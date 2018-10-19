package com.silita.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Create by IntelliJ Idea 2018.1
 * Company: silita
 * Author: gemingyi
 * Date: 2018-10-16 18:05
 */
public class StreamTest {
    public static void main(String[] args) {
        Set<String> sets = new HashSet<>(20);
        for (int i = 0; i < 20; i++) {
            sets.add("String" + i);
        }
//        Optional<String> optional = sets.stream().filter(set -> set.contains("String0")).findFirst();
//        System.out.println(optional.get());

        List list = sets.stream().filter(s -> "String0".equals(s)).collect(Collectors.toList());
        System.out.println(list.toArray().toString());
    }
}
