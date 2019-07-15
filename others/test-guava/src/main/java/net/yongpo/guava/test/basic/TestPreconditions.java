package net.yongpo.guava.test.basic;

import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;

/**
 * Created by p0po on 15-4-3.
 */
public class TestPreconditions {
    public static void main(String[] args) {
        //Preconditions.checkArgument(1==2,"传入的是假");
        Preconditions.checkArgument(1==2,"%s 不等于 %s",1,2);


    }
}
