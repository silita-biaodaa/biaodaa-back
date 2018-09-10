package com.silita.utils.stringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.NodeTraversor;
import org.jsoup.select.NodeVisitor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-09-05 14:39
 * 文字处理util
 */
public class WordProcessingUtil {

    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

    /**
     * @param htmlStr
     * @return
     *  删除Html标签
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }

    public static String getTextFromHtml(String htmlStr){
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        return htmlStr;
    }

    public String getPlainText(String str) {
        Document element = Jsoup.parse(str);
        FormattingVisitor formatter = new FormattingVisitor();
        NodeTraversor traversor = new NodeTraversor(formatter);
        traversor.traverse(element); // walk the DOM, and call .head() and
        element.select("span,a,u,b,font,img,br,pre").unwrap();
        String text = element.select("body").html().replaceAll("[　]*", "");
        text = text.replaceAll("[ ]*", "");
        text = text.replaceAll("\\n\\s*", "");
        text = text.replaceAll("&nbsp;", "");
        text = text.replaceAll("<[a-zA-Z0-9/]+:[\\w\\s='\"-\\:;@\\,~]+>", "");
        text = text.replaceAll("<!---->","");
        text = text.replaceAll("<p></p>", "");
        text = text.replace("<table>", "<table class='table table-bordered'>");
        return text;
    }

    // the formatting rules, implemented in a breadth-first DOM traverse
    private class FormattingVisitor implements NodeVisitor {
        // hit when the node is first seen
        public void head(Node node, int depth) {

        }
        // hit when all of the node's children (if any) have been visited
        public void tail(Node node, int depth) {
            if (node instanceof TextNode) {

            } else {
                List<Attribute> attrs = node.attributes().asList();
                for (int k = attrs.size()-1;k>=0; k--) {
                    String attributeKey = attrs.get(k).getKey();
                    node.removeAttr(attributeKey);
                }
            }
        }
    }
}
