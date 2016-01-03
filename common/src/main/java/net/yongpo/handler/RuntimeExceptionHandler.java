package net.yongpo.handler;

import com.alibaba.fastjson.JSON;
import net.yongpo.utils.json.JsonResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by p0po on 2015/11/6 0006.
 */
@Component
public class RuntimeExceptionHandler implements HandlerExceptionResolver {
    final static Logger logger = LoggerFactory.getLogger(RuntimeExceptionHandler.class);
    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        logger.error("{}", httpServletRequest.getRequestURL().toString()+ (StringUtils.isNotEmpty(httpServletRequest.getQueryString())?"?"+httpServletRequest.getQueryString():""), e);
        httpServletResponse.setStatus(500);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        try {
            httpServletResponse.getWriter().write(JSON.toJSONString(JsonResponse.bad(e.getMessage())));
            httpServletResponse.flushBuffer();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return new ModelAndView();
    }
}
