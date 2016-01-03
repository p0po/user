package net.yongpo.guava.test.basic;

import com.google.common.base.*;
import com.google.common.collect.Collections2;

/**
 * Created by p0po on 15-3-8.
 */
public class BasicTest {
    public static void testString(){
        System.out.println("isNullOrEmpty: " + Strings.isNullOrEmpty(null));
        System.out.println("emptyToNull: " + (Strings.emptyToNull("")==null));
        System.out.println("nullToEmpty: "+Strings.nullToEmpty(null));
        System.out.println(Strings.repeat("aaa-",3));
        System.out.println(Strings.commonPrefix("aaabaab","aaabbb"));
        System.out.println(Strings.commonSuffix("aaabaab", "aaabbb"));
        System.out.println(Strings.padStart("234",10,'0'));
        System.out.println(Strings.padEnd("234.", 10, '0'));
    }

    public static void testAscii(){
        System.out.println("toLowerCase: "+Ascii.toLowerCase("dfasWWWewe"));
    }

    public static void testCaseFormate(){//interface
        //CaseFormat.LOWER_CAMEL;
    }

    public static void tetsCharMatcher(){
        System.out.println("matchesAllOf: "+CharMatcher.is('a').matchesAllOf("aaaa"));
        System.out.println("matchesAnyOf: "+CharMatcher.DIGIT.matchesAnyOf("dfa3sd"));
    }

    public static void tetsCharStes(){
        System.out.println("UTF_8: "+ Charsets.UTF_8);
        System.out.println("US_ASCII: "+ Charsets.US_ASCII);
        System.out.println(Charsets.ISO_8859_1);
        System.out.println(Charsets.UTF_16BE);
    }

    public static void testDefault() {
        Defaults.defaultValue(Integer.class);
    }

    public static void testEnums() {
        System.out.println("a: "+Enums.getField(MyEnum.A));
        System.out.println("a: "+Enums.getIfPresent(MyEnum.class,"C"));
        System.out.println("a: "+Enums.getIfPresent(MyEnum.class,"B"));
        System.out.println("a: "+Enums.stringConverter(MyEnum.class));
    }

    public static void main(String[] args) {
        testString();
        testAscii();
        tetsCharMatcher();
        tetsCharStes();
        testEnums();
        
    }


}
