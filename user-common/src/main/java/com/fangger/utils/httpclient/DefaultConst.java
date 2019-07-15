package com.fangger.utils.httpclient;

import org.apache.http.*;
import org.apache.http.client.CookieStore;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultRedirectStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Created by p0po on 2015/9/8 0008.
 */
public class DefaultConst {
    protected static final int DEFAULT_TIME_OUT = 5 * 1000;
    protected static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    protected static SSLContext sslContext;

    static {
        /*
        暂时关闭，因为使用中很少起作用
        IdleConnectionMonitorThread idleConnectionMonitorThread = new IdleConnectionMonitorThread(cm);
        Thread thread = new Thread(idleConnectionMonitorThread);
        thread.start();
        */

        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(
                    KeyStore.getInstance(KeyStore.getDefaultType()),
                    new TrustStrategy() {
                        @Override
                        public boolean isTrusted(X509Certificate[] xcs, String string) {
                            return true;
                        }
                    }
            ).useSSL().build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        RegistryBuilder<ConnectionSocketFactory> registryBuilder = RegistryBuilder.create();
        LayeredConnectionSocketFactory sslSF = new SSLConnectionSocketFactory(sslContext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        registryBuilder.register("https", sslSF);

        ConnectionSocketFactory plainSF = new PlainConnectionSocketFactory();
        registryBuilder.register("http", plainSF);
        Registry<ConnectionSocketFactory> registry = registryBuilder.build();

        cm = new PoolingHttpClientConnectionManager(registry);
        cm.setMaxTotal(128);
        cm.setDefaultMaxPerRoute(128);
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
                    } catch (NumberFormatException ignore) {
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

    static CookieStore cookieStore = new BasicCookieStore();

    protected static final CloseableHttpClient httpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .setKeepAliveStrategy(defaultStrategy)
            .setDefaultCookieStore(cookieStore)
            .setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
                    boolean isRedirect = false;
                    try {
                        isRedirect = super.isRedirected(request, response, context);
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                    if (!isRedirect) {
                        int responseCode = response.getStatusLine().getStatusCode();
                        if (responseCode == 301 || responseCode == 302) {
                            return true;
                        }
                    }
                    return isRedirect;
                }
            })
            .build();

    protected static final CloseableHttpClient sslHttpClient = HttpClients.custom()
            .setConnectionManager(cm)
            .setKeepAliveStrategy(defaultStrategy)
            .setDefaultCookieStore(cookieStore)
            .setRedirectStrategy(new DefaultRedirectStrategy() {
                @Override
                public boolean isRedirected(HttpRequest request, HttpResponse response, HttpContext context) throws ProtocolException {
                    boolean isRedirect = false;
                    try {
                        isRedirect = super.isRedirected(request, response, context);
                    } catch (ProtocolException e) {
                        e.printStackTrace();
                    }
                    if (!isRedirect) {
                        int responseCode = response.getStatusLine().getStatusCode();
                        if (responseCode == 301 || responseCode == 302) {
                            return true;
                        }
                    }
                    return isRedirect;
                }
            })
            .build();
}
