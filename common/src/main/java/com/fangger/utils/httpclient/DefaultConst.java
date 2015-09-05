package com.fangger.utils.httpclient;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by p0po on 15-2-24.
 */
public class DefaultConst {
    protected static final int DEFAULT_TIME_OUT = 5 * 1000;

    protected static final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    static {
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
        IdleConnectionMonitorThread idleConnectionMonitorThread = new IdleConnectionMonitorThread(cm);
        Thread thread = new Thread(idleConnectionMonitorThread);
        thread.start();
    }

    static ConnectionKeepAliveStrategy defaultStrategy = new ConnectionKeepAliveStrategy() {
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            // Honor 'keep-alive' header
            HeaderElementIterator it = new BasicHeaderElementIterator(
                    response.headerIterator(HTTP.CONN_KEEP_ALIVE));
            while (it.hasNext()) {
                HeaderElement he = it.nextElement();
                String param = he.getName();
                String value = he.getValue();
                if (value != null && param.equalsIgnoreCase("timeout")) {
                    try {
                        return Long.parseLong(value) * 1000;
                    } catch(NumberFormatException ignore) {
                    }
                }
            }
            HttpHost target = (HttpHost) context.getAttribute(
                    HttpClientContext.HTTP_TARGET_HOST);
            if ("www.example.com".equalsIgnoreCase(target.getHostName())) {
                // Keep alive for 5 seconds only
                return 5 * 1000;
            } else {
                // otherwise keep alive for 30 seconds
                return 30 * 1000;
            }
        }
    };

    protected static final CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .setKeepAliveStrategy(defaultStrategy)
            .build();


    protected static CloseableHttpClient createSSLClientDefault() {
        SSLContextBuilder builder = new SSLContextBuilder();
        try {
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        SSLConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(
                    builder.build());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(
                sslsf).build();
        return httpclient;
    }

}
