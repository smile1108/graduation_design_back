package com.jiac.article.utils;

/**
 * FileName: ArticleClassify
 * Author: Jiac
 * Date: 2022/2/11 9:17
 */
public enum ArticleClassify {

    COMPUTER("计算机类"),
    PHILOSOPHY("哲学类"),
    PHYSICS("物理类"),
    CHEMISTRY("化学类"),
    LITERATURE("文学类"),
    BIOLOGY("生物类"),
    FOREIGN_LANGUAGE("外语类"),
    MATH("数学类"),
    PSYCHOLOGY("心理类"),
    ART("美术类"),
    MUSIC("音乐类"),
    SPORT("体育类"),
    OTHER("其他");


    private String name;

    ArticleClassify(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
