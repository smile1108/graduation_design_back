package com.jiac.common.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * FileName: ArticleLike
 * Author: Jiac
 * Date: 2022/3/2 12:26
 */
@Entity
@Data
@Table(name = "article_like")
@IdClass(ArticleLikeKey.class)
public class ArticleLike extends ArticleLikeKey{
}
