package com.fangger.guava.test.basic;

import com.google.common.base.Objects;

/**
 * Created by p0po on 15-4-3.
 */
public class TestObjects {
    public static void main(String[] args) {
        boolean isEqa = Objects.equal(null,new Object());
        System.out.println("isEqa = [" + isEqa + "]");

        int hashCode = Objects.hashCode(new O1());
        System.out.println("hashCode = [" + hashCode + "]");
    }
}
