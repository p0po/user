package com.fangger.utils.httpclient;

import org.apache.http.conn.HttpClientConnectionManager;

import java.util.concurrent.TimeUnit;

/**
 * Created by benben on 2015/7/11.
 */
public class IdleConnectionMonitorThread implements Runnable {
    private final HttpClientConnectionManager connMgr;
    private volatile boolean shutdown;

    public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
    }

    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }

    @Override
    public void run() {
        System.out.println("启动监控线程");
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(5000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
    }
}
