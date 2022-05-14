package com.customer.cookingrecipes.service;

import com.customer.cookingrecipes.model.Expression;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Temp {

    public static void main(String[] args) throws IOException {
        int count = 0;
        List<String> lists = new ArrayList<>();
        List<Expression> expressions = new ArrayList<>();

        String url = "https://citaty.info/category/citaty-so-smyslom";
        lists.add(url);
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 Chrome/4.0.249.0 Safari/532.5")
                .referrer("http://www.google.com")
                .get();

        /*Pagination*/ // создаем список страниц для парсинга
        var rsl2 = doc.select("div.pagination");
        var rsl3 = rsl2.select("li.pager-item");
        for (Element element : rsl3.select("a")) {
            var str = element.toString().split("href=\"");
            var str1 = str[1].split("\">");
            lists.add(str1[0]);
        }
        /**/
        //парсим
        for (int i = 0; i < lists.size() - 1; i++) { // заменить после отладки

            Document doc1 = Jsoup.connect(lists.get(2))
                    .userAgent("Mozilla/5.0 Chrome/4.0.249.0 Safari/532.5")
                    .referrer("http://www.google.com")
                    .get();

            for (Element rts : doc1.select("div.view-content")) {
                for (Element rsll : rts.select("div.node__content")) {
                    Expression result = new Expression();
                    var elm = rsll.select("div.field-item.even.last");
                    result.setCitation(elm.text());

                    var element = rsll.select("div.field.field-name-taxonomy-vocabulary-3.field-type-taxonomy-term-reference.field-label-hidden");  //автор
                    if (element.hasText()) {
                        System.out.println("____--- " + element.text());
                        result.setAuthor(element.text());
                    }
                    var select = rsll.select("div.field.field-name-field-book-author.field-type-taxonomy-term-reference.field-label-hidden");
                    if (select.hasText()) {
                        System.out.println("____=== " + select.text());
                        result.setAuthor(select.text());
                    }
                    var element1 = rsll.select("div.field.field-name-taxonomy-vocabulary-6.field-type-taxonomy-term-reference.field-label-hidden"); //автор кино
                    if (element1.hasText()) {
                        System.out.println("____+++ " + element1.text());
                        result.setAuthor(element1.text());
                    }

                    var elements = rsll.select("div.field.field-name-taxonomy-vocabulary-29.field-type-taxonomy-term-reference.field-label-hidden"); //автор кино
                    if (elements.hasText()) {
                        System.out.println("____000 " + elements.text());
                        result.setAuthor(elements.text());
                    }

                    result.setId(++count);
                    expressions.add(result);
                }
            }
        }

        System.out.println("expressions = " + expressions);
        for (Expression expression : expressions) {
            System.out.println();
        }

    }
}
