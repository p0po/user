package com.fangger.filter;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by popo on 2014/10/6.
 */
public class XssFilter implements Filter {
    private static Logger logger = Logger.getLogger(XssFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("init XssFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest == null) {
            filterChain.doFilter(servletRequest, servletResponse);
        }

        HttpServletRequest hsr = (HttpServletRequest) servletRequest;
        String ip = hsr.getRemoteAddr();
        String userAgent = hsr.getHeader("User-Agent");
        String from = hsr.getHeader("From");
        String requestUri = hsr.getRequestURI();
        if (!requestUri.startsWith("/img")
                && !requestUri.startsWith("/css")
                && !requestUri.startsWith("/js")) {
            logger.debug(ip + " " + Calendar.getInstance().getTimeInMillis() + " " + hsr.getMethod() + " " + requestUri + " " + from + " " + userAgent);
        }
        /**
         * 白名单
         */
        if (requestUri == null
                || requestUri.endsWith("")
                ) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(new XssHttpServletRequestWrapper((HttpServletRequest) servletRequest), servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    private class XssHttpServletRequestWrapper extends
            HttpServletRequestWrapper {
        private Map<String, String[]> params = new HashMap<String, String[]>();

        public XssHttpServletRequestWrapper(HttpServletRequest request) {
            super(request);
            this.params.putAll(request.getParameterMap());
        }

        @Override
        public String getParameter(String name) {
            String[] values = params.get(name);
            if (values == null || values.length == 0) {
                return null;
            }
            return xssEncode(values[0]);
        }

        @Override
        public String[] getParameterValues(String parameter) {
            String[] results = params.get(parameter);
            if (results == null) {
                return null;
            } else {
                String[] resultsTemp = new String[results.length];
                for (int i = 0, n = results.length; i < n; i++) {
                    resultsTemp[i] = xssEncode(results[i]);
                }
                results = resultsTemp;
            }
            return results;
        }

        @Override
        public String getHeader(String name) {
            String value = super.getHeader(xssEncode(name));
            if (value != null) {
                value = xssEncode(value);
            }
            return value;
        }

        /**
         * The default behavior of this method is to call getAttribute(String
         * name) on the wrapped request object.
         */
  /*
   * @Override public Object getAttribute(String name) { Object obj =
   * super.getAttribute(name); String temp = null; if (obj instanceof
   * String) { temp = xssEncode((String) obj); } return temp == null ? obj
   * : temp; }
   */
        private String xssEncode(String s) {
            if (s == null || "".equals(s)) {
                return s;
            }
            StringBuilder sb = new StringBuilder(s.length() + 16);
            boolean hasThreaten = false;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                switch (c) {
                    case '>':
                        sb.append('＞');// 全角大于号
                        if (logger.isDebugEnabled()) {
                            logger.debug("replace > with ＞");
                        }
                        hasThreaten = true;
                        break;
                    case '<':
                        sb.append('＜');// 全角小于号
                        if (logger.isDebugEnabled()) {
                            logger.debug("replace < with ＜");
                        }
                        hasThreaten = true;
                        break;
    /*
    case '\'':
     sb.append('‘');// 全角单引号
     if (logger.isDebugEnabled()) {
      logger.debug("replace \' with ‘");
     }
     hasThreaten = true;
     break;
    case '\"':
     sb.append('“');// 全角双引号
     if (logger.isDebugEnabled()) {
      logger.debug("replace \" with “");
     }
     hasThreaten = true;
     break;
     */
                    case '&':
                        sb.append('＆');// 全角
                        if (logger.isDebugEnabled()) {
                            logger.debug("replace & with ＆");
                        }
                        hasThreaten = true;
                        break;
                    case '\\':
                        sb.append('＼');// 全角斜线
                        if (logger.isDebugEnabled()) {
                            logger.debug("replace \\ with ＼");
                        }
                        hasThreaten = true;
                        break;
    /*
     * 掌厅协议中有#和|，所以暂时放过 case '#': sb.append('＃');// 全角井号 if
     * (logger.isDebugEnabled()) { logger.debug("replace # with ＃");
     * } hasThreaten = true; break; case '|': // 删除| if
     * (logger.isDebugEnabled()) { logger.debug("remove |"); }
     * hasThreaten = true; break;
     */
                    default:
                        sb.append(c);
                        // if(logger.isDebugEnabled()){logger.debug("safe..");}
                        break;
                }
            }
            if (logger.isDebugEnabled() && hasThreaten) {
                logger.debug("replace 【" + s + "】 with 【" + sb.toString() + "】");
            }
            return sb.toString();
        }
    }
}
