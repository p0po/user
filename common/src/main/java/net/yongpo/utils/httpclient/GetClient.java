package net.yongpo.utils.httpclient;

import com.google.common.base.Preconditions;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.fengjr.trade.util.httpclient.DefaultConst.*;

/**
 * Created by p0po on 2015/9/8 0008.
 */
public class GetClient {
    public static HttpResult send(HttpClientBuilder httpClientBuilder) throws IOException {
        Preconditions.checkArgument(httpClientBuilder.getBody() == null && httpClientBuilder.getToPostBinaryData() == null && httpClientBuilder.getToPostBinaryData() == null, "Get方法需要将参数拼接到url中");
        int connectionTimeOut = httpClientBuilder.getConnectionTimeOut() <= 0 ? DEFAULT_TIME_OUT : httpClientBuilder.getConnectionTimeOut();
        HttpHost proxy = httpClientBuilder.getProxy();
        Map<String, String> header = httpClientBuilder.getHeader();
        String url = httpClientBuilder.getUrl();
        Charset charset = httpClientBuilder.getCharset() == null ? Consts.UTF_8 : httpClientBuilder.getCharset();
        Preconditions.checkNotNull(url, "url can not be null");

        HttpResult httpResult = new HttpResult();
        //connectionTimeOut = connectionTimeOut < 0 ? DEFAULT_TIME_OUT : connectionTimeOut;

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(connectionTimeOut)
                .setSocketTimeout(connectionTimeOut)
                .setConnectTimeout(connectionTimeOut)
                .setProxy(proxy)
                .build();

        HttpGet httpget = new HttpGet(url);

        httpget.setConfig(requestConfig);

        if (header != null) {
            for (String key : header.keySet()) {
                httpget.setHeader(key, header.get(key));
            }
        }

        CloseableHttpResponse response = null;
        try {
            if (url.startsWith("https://")) {
                response = sslHttpClient.execute(httpget, HttpClientContext.create());
            }else{
                response = httpClient.execute(httpget, HttpClientContext.create());
            }

            Preconditions.checkNotNull(response, "Get % result null", httpget.getURI().toString());

            httpResult.setStatusCode(response.getStatusLine().getStatusCode());

            Header[] headers = response.getAllHeaders();
            int size = headers == null ? 1 : (headers.length + 1);
            Map<String, String> map = new HashMap<>(size);
            for (Header resHeader : headers) {
                map.put(resHeader.getName(), resHeader.getValue());
            }
            httpResult.setHeader(map);
            httpResult.setHeaders(headers);

            String result = EntityUtils.toString(response.getEntity(), charset);
            httpResult.setBody(result);
        } finally {
            if (response != null) {
                try {
                    response.close();
                    httpget.releaseConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpResult;
    }

    /**
     * 并发发起GET请求
     *
     * @param httpClientBuilder
     * @param exec
     * @return
     */
    public static Future<HttpResult> sendWithPool(HttpClientBuilder httpClientBuilder, ExecutorService exec) {
        return exec.submit(new GetThread(httpClientBuilder));
    }
}
