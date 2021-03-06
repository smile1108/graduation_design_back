package com.jiac.article.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.jiac.article.feign.CommentFeign;
import com.jiac.article.feign.UserFeign;
import com.jiac.article.repository.ArticleLikeRepository;
import com.jiac.article.repository.ArticleRepository;
import com.jiac.article.request.*;
import com.jiac.article.service.ArticleService;
import com.jiac.common.utils.Html2Text;
import com.jiac.common.utils.Markdown2Html;
import com.jiac.common.dto.ArticleDto;
import com.jiac.common.entity.Article;
import com.jiac.common.entity.ArticleLike;
import com.jiac.common.entity.User;
import com.jiac.common.utils.CommonType;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.*;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * FileName: ArticleServiceImpl
 * Author: Jiac
 * Date: 2022/2/12 7:22
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    // 服务器上的图片的固定前缀
    @Value("${static-profile-url}")
    private String IMAGE_PREFIX;

    // nginx 存储图片的路径
    @Value("${nginx-static-dir}")
    private String NGINX_STATIC_DIR;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleLikeRepository articleLikeRepository;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private CommentFeign commentFeign;

    @Override
    public String uploadImage(MultipartFile file) throws IOException {
        // 先放入静态目录中
        RandomUtil randomUtil = new RandomUtil();
        // 获取原来文件的类型 比如 jpg  png
        String[] split = file.getOriginalFilename().split("\\.");
        String suffix = split.length == 1 ? "jpg" : split[1];
        String randomFileName = randomUtil.randomString(20) + "." + suffix;
        // 根据随机生成的10位字符串 拼接上Nginx的静态目录 组成文件存储的路径
        String filePath = NGINX_STATIC_DIR + randomFileName;
        // 将文件写入对应路径中
        FileUtil.writeBytes(file.getBytes(), filePath);
        // 然后返回给前端 访问图片的路径
        return IMAGE_PREFIX + randomFileName;
    }

    @Override
    public Boolean deleteImage(String filename) {
        // 给回来的filename是前端访问图片的路径 如 http://localhost/images/xxxxxx.jpg
        // 首先把文件名字分离出来
        String[] split = filename.split("/");
        // 经过测试 前端传递过来的路径中多了,[object File] 所以我们还需要过滤一层
        // 数组的最后一个就是文件的名字
        String randomName = split[split.length - 1].split(",")[0];
        // 然后根据目录 拼接成在文件系统中的路径
        String path = NGINX_STATIC_DIR + randomName;
        // 先判断文件是否存在 如果存在再删除
        boolean exist = FileUtil.exist(path);
        if (!exist) {
            throw new MyException(ErrorEnum.FILE_NOT_EXIST);
        }
        // 然后使用hutool 中的FileUtil删除文件
        FileUtil.del(path);
        return true;
    }

    @Override
    public ArticleDto addArticle(AddArticleRequest request) {
        Article article = new Article();
        RandomUtil randomUtil = new RandomUtil();
        String randomId = randomUtil.randomString(10);
        article.setId(randomId);
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        article.setClassify(request.getClassify());
        article.setPublishDate(new Date());
        User user = new User();
        user.setUsername(request.getUsername());
        article.setUser(user);
        Article save = articleRepository.save(article);
        return ArticleDto.of(save);
    }

    @Override
    public ArticleDto deleteArticle(DeleteArticleRequest request) {
        Optional<Article> articleOptional = articleRepository.findById(request.getId());
        try {
            Article article = articleOptional.get();
            // 如果没有报错 就说明存在
            // 然后判断操作的用户是不是该文章所属的用户 否则没有权限
            if (!article.getUser().getUsername().equals(request.getUsername())) {
                throw new MyException(ErrorEnum.NO_PERMISSION);
            }
            // 如果有权限 就进行删除
            // 因为文章的评论以及问题的回答 都设置了外键 所以删除文章之前应该先删除对应的评论
            if(commentFeign.deleteCommentByArticleId(request.getId()).getData()) {
                articleRepository.delete(article);
                // 删除一个文章之后 要将对应的喜欢的记录 删除掉 否则之后调用获取喜欢列表的接口会报错
                articleLikeRepository.deleteByArticleId(article.getId());
            }
            return ArticleDto.of(article);
        } catch (NoSuchElementException e) {
            // 如果捕捉到异常 就重新抛出一个我们处理的异常
            throw new MyException(ErrorEnum.ARTICLE_NOT_EXIST);
        }
    }

    @Override
    public PageVo<ArticleDto> getUserArticle(GetUserArticleRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        int page = request.getPage();
        int pageSize = request.getPageSize();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        String username = request.getUsername();
        Specification<Article> specification = (Specification<Article>) (root, query, cb) -> {
            Join<Article, User> userJoin = root.join("user", JoinType.LEFT);
            return cb.equal(userJoin.get("username").as(String.class), username);
        };
        Page<Article> articlePage = articleRepository.findAll(specification, pageRequest);
        return transferPageArticle(articlePage);
    }

    @Override
    public PageVo<ArticleDto> searchArticle(SearchArticleRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        int page = request.getPage() == null ? 0 : request.getPage();
        int pageSize = request.getPageSize() == null ? 5 : request.getPageSize();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        String keyword = request.getKeyword();
        String classify = request.getClassify();
        List<String> classifyList = transferClassifyToList(classify);
        Specification<Article> specification = (Specification<Article>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (keyword != null && !"".equals(keyword)) {
                Join<Article, User> userJoin = root.join("user", JoinType.LEFT);
                Predicate keywordPredicate = cb.or(cb.like(root.get("title"), "%" + keyword + "%"), cb.like(root.get("content"), "%" + keyword + "%"), cb.like(userJoin.get("nickname"), "%" + keyword + "%"));
                predicateList.add(keywordPredicate);
            }
            if (classifyList != null) {
                Predicate classifyPredicate = root.get("classify").in(classifyList);
                predicateList.add(classifyPredicate);
            }
            Predicate[] predicates = new Predicate[predicateList.size()];
            return query.where(predicateList.toArray(predicates)).getRestriction();
        };
        Page<Article> articlePage = articleRepository.findAll(specification, pageRequest);
        return judgeUserLikeAndFollow(transferPageArticle(articlePage), request.getUsername());
    }

    @Override
    public ArticleDto getArticleMessageById(String articleId, String username) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        try {
            Article article = articleOptional.get();
            // 如果没有异常 表示查询到了对应的文章
            ArticleDto articleDto = ArticleDto.of(article);
            if (username != null && !"".equals(username)) {
                ArticleLike articleLike = articleLikeRepository.findByIdAndUsername(articleId, username);
                if (articleLike != null) {
                    articleDto.setLike(true);
                }
                CommonType<Boolean> userFollow = userFeign.getUserFollow(username, articleDto.getUserDto().getUsername());
                articleDto.setFollow(userFollow.getData());
            }
            articleDto.setLikeCount(articleLikeRepository.getLikeCountByArticleId(articleId));
            articleDto.setHtmlContent(Markdown2Html.convert(articleDto.getContent()));
            articleDto.setTextContent(Html2Text.convert(articleDto.getHtmlContent()));
            return articleDto;
        } catch (NoSuchElementException e) {
            // 如果捕获到异常  表示没有对应的文章 这时候给前端一个错误信息
            throw new MyException(ErrorEnum.ARTICLE_NOT_EXIST);
        }
    }

    @Override
    public Boolean like(String username, String articleId) {
        // 先检查文章存不存在
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        try {
            Article article = articleOptional.get();
            // 如果存在 然后进行like操作
            ArticleLike articleLike = articleLikeRepository.findByIdAndUsername(articleId, username);
            if (articleLike != null) {
                // 代表当前用户已经喜欢该文章 不能重复操作
                throw new MyException(ErrorEnum.DO_NOT_DONE_AGAIN);
            }
            articleLike = new ArticleLike();
            articleLike.setUsername(username);
            articleLike.setArticleId(articleId);
            articleLike.setLikeDate(new Date());
            articleLikeRepository.save(articleLike);
        } catch (NoSuchElementException e) {
            // 如果捕捉到异常 就重新抛出一个我们处理的异常
            throw new MyException(ErrorEnum.ARTICLE_NOT_EXIST);
        }
        return true;
    }

    @Override
    public Boolean unlike(String username, String articleId) {
        // 先检查文章存不存在
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        try {
            Article article = articleOptional.get();
            // 如果存在 然后进行like操作
            ArticleLike articleLike = articleLikeRepository.findByIdAndUsername(articleId, username);
            if (articleLike == null) {
                // 代表当前用户已经喜欢该文章 不能重复操作
                throw new MyException(ErrorEnum.ILLEGAL_OPERATION);
            }
            articleLikeRepository.delete(articleLike);
        } catch (NoSuchElementException e) {
            // 如果捕捉到异常 就重新抛出一个我们处理的异常
            throw new MyException(ErrorEnum.ARTICLE_NOT_EXIST);
        }
        return true;
    }

    @Override
    public PageVo<ArticleDto> getLikeListByUser(GetLikeListRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "likeDate");
        int page = request.getPage();
        int pageSize = request.getPageSize();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        Specification<ArticleLike> specification = (Specification<ArticleLike>) (root, query, cb) -> cb.equal(root.get("username"), request.getUsername());
        Page<ArticleLike> articleLikePage = articleLikeRepository.findAll(specification, pageRequest);
        return transferPageArticleLike2Article(articleLikePage);
    }

    @Override
    public Integer countArticleByUser(String username) {
        return articleRepository.countArticle(username);
    }

    @Override
    public Integer countLikeByUser(String username) {
        return articleLikeRepository.getLikeCountByUsername(username);
    }

    @Override
    public Boolean articleExist(String articleId) {
        Optional<Article> articleOptional = articleRepository.findById(articleId);
        try {
            Article article = articleOptional.get();
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    private PageVo<ArticleDto> transferPageArticle(Page<Article> page) {
        List<ArticleDto> articleDtoList = page.stream().map(ArticleDto::of).collect(Collectors.toList());
        for (ArticleDto articleDto : articleDtoList) {
            articleDto.setLikeCount(articleLikeRepository.getLikeCountByArticleId(articleDto.getId()));
            articleDto.setCommentCount(commentFeign.countCommentByArticle(articleDto.getId()).getData());
        }
        PageVo<ArticleDto> articleDtoPageVo = new PageVo<>();
        articleDtoPageVo.setLists(articleDtoList);
        articleDtoPageVo.setCount(page.getTotalElements());
        articleDtoPageVo.setSumPage(page.getTotalPages());
        return articleDtoPageVo;
    }

    private PageVo<ArticleDto> transferPageArticleLike2Article(Page<ArticleLike> page) {
        List<ArticleDto> articleDtoList = page.stream().map(articleLike -> {
            Optional<Article> articleOptional = articleRepository.findById(articleLike.getArticleId());
            try {
                Article article = articleOptional.get();
                ArticleDto articleDto = ArticleDto.of(article);
                articleDto.setLike(true);
                articleDto.setLikeCount(articleLikeRepository.getLikeCountByArticleId(articleDto.getId()));
                return articleDto;
            } catch (NoSuchElementException e) {
                // 如果捕捉到异常 就重新抛出一个我们处理的异常
                throw new MyException(ErrorEnum.ARTICLE_NOT_EXIST);
            }
        }).collect(Collectors.toList());
        PageVo<ArticleDto> articleDtoPageVo = new PageVo<>();
        articleDtoPageVo.setLists(articleDtoList);
        articleDtoPageVo.setCount(page.getTotalElements());
        articleDtoPageVo.setSumPage(page.getTotalPages());
        return articleDtoPageVo;
    }

    private PageVo<ArticleDto> judgeUserLikeAndFollow(PageVo<ArticleDto> pageVo, String username) {
        if (username == null || "".equals(username)) {
            return pageVo;
        }
        List<ArticleDto> articleDtos = pageVo.getLists();
        for (ArticleDto articleDto : articleDtos) {
            ArticleLike articleLike = articleLikeRepository.findByIdAndUsername(articleDto.getId(), username);
            if (articleLike != null) {
                articleDto.setLike(true);
            }
            // 获取关注信息
            Boolean follow = userFeign.getUserFollow(username, articleDto.getUserDto().getUsername()).getData();
            articleDto.setFollow(follow);
        }
        return pageVo;
    }

    private List<String> transferClassifyToList(String classify) {
        // 规定好 前端传递来的分类筛选 为 所有的筛选用,连接 比如 COMPUTER,MATH
        if (classify == null || "".equals(classify)) {
            return null;
        }
        return Arrays.asList(classify.split(","));
    }
}
