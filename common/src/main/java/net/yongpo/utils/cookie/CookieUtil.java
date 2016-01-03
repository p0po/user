package net.yongpo.utils.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by popo on 2014/10/7.
 */
public class CookieUtil {
    static HttpServletResponse response;

    public static CookieUtil getIntence(HttpServletResponse httpServletResponse) {
        response = httpServletResponse;
        return new CookieUtil();
    }

    public CookieUtil add(String key, String value, String domain, int maxAge) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        response.addCookie(cookie);
        return this;
    }

    public CookieUtil add(String key, String value) {
        add(key, value, "/", 1000 * 60 * 60 * 24 * 30);
        return this;
    }

    public CookieUtil delete(String key) {
        add(key, null, "/", -1);
        return this;
    }

}
