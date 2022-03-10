package com.jiac.comment.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.jiac.comment.repository.CommentRepository;
import com.jiac.comment.request.AddCommentRequest;
import com.jiac.comment.service.CommentService;
import com.jiac.common.dto.CommentDto;
import com.jiac.common.entity.Article;
import com.jiac.common.entity.Comment;
import com.jiac.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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
}
