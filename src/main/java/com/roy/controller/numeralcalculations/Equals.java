package com.roy.controller.numeralcalculations;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Equals {

    public static void main(String[] args) {
        wrong();
        right();
        set();
    }

    /**
     * equals 比较的是 BigDecimal 的 value 和 scale，1.0 的 scale 是 1，1 的 scale 是 0，所以结果一定是 false：
     * 如果我们希望只比较 BigDecimal 的 value，可以使用 compareTo 方法
     *
     */
    private static void wrong() {
        System.out.println(new BigDecimal("1.0").equals(new BigDecimal("1")));
    }

    private static void right() {
        System.out.println(new BigDecimal("1.0").compareTo(new BigDecimal("1")) == 0);
    }

    private static void set() {
        Set<BigDecimal> hashSet1 = new HashSet<>();
        hashSet1.add(new BigDecimal("1.0"));
        System.out.println(hashSet1.contains(new BigDecimal("1")));//返回false

        Set<BigDecimal> hashSet2 = new HashSet<>();
        hashSet2.add(new BigDecimal("1.0").stripTrailingZeros());
        System.out.println(hashSet2.contains(new BigDecimal("1.000").stripTrailingZeros()));//返回true

        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(new BigDecimal("1.0"));
        System.out.println(treeSet.contains(new BigDecimal("1")));//返回true
    }

}

