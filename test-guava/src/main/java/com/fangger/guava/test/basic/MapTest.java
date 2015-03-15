package com.fangger.guava.test.basic;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;

/**
 * Created by p0po on 15-3-15.
 */
public class MapTest {
    static class User{
        Integer id;
        String name;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void main(String[] args) {
        ClassToInstanceMap<User> classToInstanceMap = MutableClassToInstanceMap.create();
        User user = new User();
        user.setId(1);
        user.setName("p0po");
        classToInstanceMap.put(User.class, user);
        System.out.println("args = [" + args + "]");
    }
}
