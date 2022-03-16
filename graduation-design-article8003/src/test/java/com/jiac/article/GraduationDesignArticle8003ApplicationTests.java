package com.jiac.article;

import com.jiac.common.utils.Html2Text;
import com.jiac.common.utils.Markdown2Html;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GraduationDesignArticle8003ApplicationTests {

    @Test
    void contextLoads() {
    }


    @Test
    void testMarkdown2Html() {
        String markdownStr = "## Redis持久化\n" +
                "\n" +
                "Redis是内存数据库，如果不将内存中的数据库状态保存到磁盘，那么一旦服务器进程退出，服务器中的数据库状态就会消失。所以Redis提供了持久化功能！\n" +
                "\n" +
                "### RDB(Redis DataBase)\n" +
                "\n" +
                "> 什么是RDB\n" +
                "\n" +
                "![RDB工作方式](./1.png)\n" +
                "\n" +
                "在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接读到内存里";
        String html = Markdown2Html.convert(markdownStr);
        System.out.println(html);
    }

    @Test
    void testHtml2Text() {
        String htmlStr = "<h2>Redis持久化</h2>\n" +
                "<p>Redis是内存数据库，如果不将内存中的数据库状态保存到磁盘，那么一旦服务器进程退出，服务器中的数据库状态就会消失。所以Redis提供了持久化功能！</p>\n" +
                "<h3>RDB(Redis DataBase)</h3>\n" +
                "<blockquote>\n" +
                "<p>什么是RDB</p>\n" +
                "</blockquote>\n" +
                "<p><img src=\"./1.png\" alt=\"RDB工作方式\" /></p>\n" +
                "<p>在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是行话讲的Snapshot快照，它恢复时是将快照文件直接读到内存里</p>";

        String textStr = Html2Text.convert(htmlStr);
        System.out.println(textStr);
    }
}
