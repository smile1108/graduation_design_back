package com.jiac.comment.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.comment.repository.CommentRepository;
import com.jiac.comment.request.AddCommentRequest;
import com.jiac.comment.request.DeleteCommentRequest;
import com.jiac.comment.request.GetCommentListRequest;
import com.jiac.comment.service.CommentService;
import com.jiac.common.dto.CommentDto;
import com.jiac.common.entity.Article;
import com.jiac.common.entity.Comment;
import com.jiac.common.entity.User;
import com.jiac.common.utils.ErrorEnum;
import com.jiac.common.utils.MyException;
import com.jiac.common.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FileName: CommentServiceImpl
 * Author: Jiac
 * Date: 2022/3/10 13:48
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDto addComment(AddCommentRequest request) {
        Comment comment = new Comment();
        comment.setId(RandomUtil.randomString(10));
        comment.setContent(request.getContent());
        comment.setPublishDate(new Date());
        Article article = new Article();
        article.setId(request.getArticleId());
        comment.setArticle(article);
        User user = new User();
        user.setUsername(request.getUsername());
        comment.setUser(user);
        Comment save = commentRepository.save(comment);
        return CommentDto.of(save);
    }

    @Override
    public CommentDto deleteComment(DeleteCommentRequest request) {
        Optional<Comment> commentOptional = commentRepository.findById(request.getCommentId());
        try {
            Comment comment = commentOptional.get();
            if(!comment.getUser().getUsername().equals(request.getUsername())) {
                throw new MyException(ErrorEnum.NO_PERMISSION);
            }
            commentRepository.delete(comment);
            return CommentDto.of(comment);
        } catch (NoSuchElementException e) {
            throw new MyException(ErrorEnum.COMMENT_NOT_EXIST);
        }
    }

    @Override
    public PageVo<CommentDto> getCommentList(GetCommentListRequest request) {
        Sort sort = Sort.by(Sort.Direction.DESC, "publishDate");
        int page = 0;
        int pageSize = request.getNumber();
        PageRequest pageRequest = PageRequest.of(page, pageSize, sort);
        String articleId = request.getArticleId();
        Specification<Comment> specification = (Specification<Comment>) (root, query, cb) -> {
            Join<Comment, Article> articleJoin = root.join("article", JoinType.LEFT);
            return cb.equal(articleJoin.get("id").as(String.class), articleId);
        };
        Page<Comment> commentPage = commentRepository.findAll(specification, pageRequest);
        return transferCommentPage2CommentDtoPageVo(commentPage);
    }

    private PageVo<CommentDto> transferCommentPage2CommentDtoPageVo(Page<Comment> commentPage) {
        List<CommentDto> commentDtoList = commentPage.getContent().stream().map(CommentDto::of).collect(Collectors.toList());
        PageVo<CommentDto> commentDtoPageVo = new PageVo<>();
        commentDtoPageVo.setLists(commentDtoList);
        commentDtoPageVo.setCount(commentPage.getTotalElements());
        commentDtoPageVo.setSumPage(commentPage.getTotalPages());
        return commentDtoPageVo;
    }
}
