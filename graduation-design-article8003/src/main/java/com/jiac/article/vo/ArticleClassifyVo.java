package com.jiac.article.vo;

import com.jiac.article.utils.ArticleClassify;
import lombok.Data;

/**
 * FileName: ArticleClassifyVo
 * Author: Jiac
 * Date: 2022/2/11 9:42
 */
@Data
public class ArticleClassifyVo {
    private ArticleClassify articleClassify;
    private String name;

    public static ArticleClassifyVo of(ArticleClassify articleClassify) {
        ArticleClassifyVo articleClassifyVo = new ArticleClassifyVo();
        articleClassifyVo.setArticleClassify(articleClassify);
        articleClassifyVo.setName(articleClassify.getName());
        return articleClassifyVo;
    }
}
