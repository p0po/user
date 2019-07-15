package net.yongpo.guava.test.basic;

import com.google.common.collect.Ordering;
import org.springframework.beans.BeanUtils;

/**
 * Created by p0po on 15-4-3.
 */
public class TestOrding {
    public static void main(String[] args) {
        Ordering<O1> o1Ordering = new Ordering<O1>() {
            @Override
            public int compare(O1 o1, O1 t1) {
                return 0;
            }
        };

        Ordering<Comparable> natural = Ordering.natural();
        Ordering<Comparable> comparableOrdering = natural.nullsFirst();
    }
}
