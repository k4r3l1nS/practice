package com.practice.demo.uri_handler;

import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;

public class UriHandler {

    public static List<Pair<String, String>> parse(String uri) {

        List<Pair<String, String>> list = new ArrayList<>();

        int index = 0;
        String currentUri = "";
        String key = "";

        list.add(new Pair("home", "/"));

        while (index < uri.length()) {

            char charAtIndex = uri.charAt(index);

            if (charAtIndex == '/' && index > 0) {

                list.add(new Pair(key, currentUri));
                key = "";
            }

            currentUri += charAtIndex;

            if (charAtIndex != '/')
                key += charAtIndex;

            ++index;
        }

        list.add(new Pair(key, currentUri));

        return list;
    }
}
