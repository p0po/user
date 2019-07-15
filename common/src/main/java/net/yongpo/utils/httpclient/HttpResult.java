package net.yongpo.utils.httpclient;

import lombok.ToString;
import org.apache.http.Header;

import java.util.Map;

/**
 * Created by p0po on 2015/9/8 0008.
 */
@ToString
public class HttpResult {
    int statusCode;
    String body;
    @Deprecated
    /**
     * 因为header可有重复值，所以使用map不恰当
     * 替换为 headers。
     */
    Map<String, String> header;

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    Header[] headers;
    public static final int OK = 200;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
    @Deprecated
    public Map<String, String> getHeader() {
        return header;
    }
    @Deprecated
    public void setHeader(Map<String, String> header) {
        this.header = header;
    }
}
