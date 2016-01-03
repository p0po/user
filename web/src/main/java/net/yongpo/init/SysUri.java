package net.yongpo.init;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by p0po on 15-6-21.
 * 系统所有的uri
 */
public class SysUri {
    private static final Set<String> uriSet = new HashSet<>();

    public static void add(String uri){
        uriSet.add(uri);
    }

    public boolean isSysUri(String uri){
        return uriSet.contains(uri);
    }
}
