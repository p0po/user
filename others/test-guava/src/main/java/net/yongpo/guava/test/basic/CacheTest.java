package net.yongpo.guava.test.basic;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by benben on 2016/1/15.
 */
public class CacheTest {
    public static void main(String[] args) {
        LoadingCache<String,String> cacheBuilder= CacheBuilder
                .newBuilder()
                .maximumSize(5)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .build(new CacheLoader<String, String>(){
                    @Override
                    public String load(String key) throws Exception {
                        return null;
                    }
                });

        try {
            cacheBuilder.put("p0po","dfasfsa");

            String a = cacheBuilder.get("p0po");
            System.out.println("args = [" + a + "]");
            while (cacheBuilder.get("p0po") != null){
                Thread.sleep(1000);
                //cacheBuilder.put("p0po","abc");
                a = cacheBuilder.get("p0po");
                System.out.println("args = [" + a + "]");
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
