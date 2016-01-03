package net.yongpo.utils.httpclient;

import com.google.common.base.Preconditions;
import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import static net.yongpo.utils.httpclient.DefaultConst.*;

/**
 * Created by p0po on 2015/9/8 0008.
 */
public class PostClient {
    public static Future<HttpResult> sendWithPool(HttpClientBuilder httpClientBuilder, ExecutorService exec) {
        return exec.submit(new PostThread(httpClientBuilder));
    }

    public static HttpResult send(HttpClientBuilder httpClientBuilder) throws IOException {
        int connectionTimeOut = httpClientBuilder.getConnectionTimeOut() <= 0?DEFAULT_TIME_OUT:httpClientBuilder.getConnectionTimeOut();
        HttpHost proxy = httpClientBuilder.getProxy();
        Map<String,String> header = httpClientBuilder.getHeader();
        Map<String,String> body = httpClientBuilder.getBody();
        String url = httpClientBuilder.getUrl();
        Charset charset = httpClientBuilder.getCharset()==null? Consts.UTF_8:httpClientBuilder.getCharset();
        String jsonString = httpClientBuilder.getToPostJsonString();
        Preconditions.checkNotNull(url, "url can not be null");
        Preconditions.checkArgument((body == null && jsonString != null) || (body != null && jsonString == null), "[body] or [jsonString] must need only one");
       /*  if (ssl) {
            httpClient = createSSLClientDefault();
        }*/

        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(connectionTimeOut)
                .setConnectTimeout(connectionTimeOut)
                .setProxy(proxy)
                .build();

        HttpPost httpPost = new HttpPost(url);

        httpPost.setConfig(requestConfig);

        if (header != null) {
            for (String key : header.keySet()) {
                httpPost.setHeader(key, header.get(key));
            }
        }

        if (body != null) {
            List<NameValuePair> formParams = new ArrayList<>();
            for (String key : body.keySet()) {
                formParams.add(new BasicNameValuePair(key, body.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);
            httpPost.setEntity(entity);
        }else if(jsonString != null){
            StringEntity stringEntity = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }

        HttpResult httpResult = new HttpResult();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            Preconditions.checkNotNull(response, "Get % result null", httpPost.getURI().toString());

            //Preconditions.checkState(response.getStatusLine().getStatusCode() == 200,response.getStatusLine().getStatusCode());
            httpResult.setStatusCode(response.getStatusLine().getStatusCode());

            Header[] headers = response.getAllHeaders();
            int size = headers == null?1:(headers.length+1);
            Map<String,String> map = new HashMap<>(size);
            for (Header resHeader:headers){
                map.put(resHeader.getName(),resHeader.getValue());
            }
            httpResult.setHeader(map);

            String result = EntityUtils.toString(response.getEntity(), charset);
            httpResult.setBody(result);
        } finally {
            if (response != null) {
                try {
                    response.close();
                    httpPost.releaseConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return httpResult;
    }
}
