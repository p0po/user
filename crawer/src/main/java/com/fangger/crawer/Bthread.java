package com.fangger.crawer;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by p0po on 15-6-27.
 */
public class Bthread implements Runnable {
    int i = 0;
    Bthread(int i){
        this.i = i;
    }
    @Override
    public void run() {
        System.currentTimeMillis();
        //System.out.println(i);
    }
}
