package com.yby.uInterface.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class HttpParseUtil {
    public static void main(String[] args) throws IOException {
        String url = "http://qianshan.co/";

        Document document = Jsoup.parse(new URL(url), 30000);

        Elements element = document.getElementsByClass("website");

        System.out.println(element.html());


    }
}
