package com.fangger.init;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by p0po on 15-3-30.
 */
public class Init implements ServletContextListener {

    private static final Set<RequestMethod> ALL= new LinkedHashSet<>();
    static {
        ALL.add(RequestMethod.GET);
        ALL.add(RequestMethod.POST);
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);

        Map<String, HandlerMapping> allRequestMappings = BeanFactoryUtils.beansOfTypeIncludingAncestors(appContext,
                HandlerMapping.class, true, false);

        RequestMappingHandlerMapping requestMappingHandlerMapping = null;
        for (HandlerMapping handlerMapping : allRequestMappings.values()){
            if (handlerMapping instanceof RequestMappingHandlerMapping){
                requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                break;
            }
        }
        List<Map<String,Object>> list = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet())
        {

            Map<String,Object> map = new HashMap<>();

            //解析传入参数
            RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
            Set<String> htttpPatterns = requestMappingInfo.getPatternsCondition().getPatterns();
            Set<RequestMethod> httpMethods = requestMappingInfo.getMethodsCondition().getMethods();
            Map<String,Object> httpMap = new HashMap<>();
            httpMethods = CollectionUtils.isEmpty(httpMethods)?ALL:httpMethods;
            httpMap.put("method", httpMethods);
            httpMap.put("url",htttpPatterns);

            /*if(httpMethods.size() == 0){
                httpMethods.add(RequestMethod.GET);
                httpMethods.add(RequestMethod.POST);
            }*/
            System.out.println(httpMethods+" = " + htttpPatterns);

            HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
            String controllerName = mappingInfoValue.getBeanType().getName();
            Method method = mappingInfoValue.getMethod();
            Annotation[][] annotations = method.getParameterAnnotations();

            Class<?>[] parameterTypes = method.getParameterTypes();
            LocalVariableTableParameterNameDiscoverer u =
                    new LocalVariableTableParameterNameDiscoverer();

            String[] parameterNames = u.getParameterNames(method);
            String param = "";
            for (int i=0;i<parameterTypes.length;i++){
                String name = parameterTypes[i].getSimpleName();
                Annotation[] annotation1 = annotations[i];
                if(annotation1 != null){
                    for (Annotation annotation:annotation1){
                        param += annotation.toString();
                    }
                }

                param += name+" "+ parameterNames[i]+",";
            }


           /* for(Annotation[] annotations1:annotations){
                param += ",";
                for (Annotation annotation:annotations1){
                    param += annotation.toString()+" ";
                }
            }*/

            if(!StringUtils.isEmpty(param)){
                param =  " ("+param.substring(0,param.length()-1)+")";
            }else {
                param =  " ()";
            }
            httpMap.put("mapping",controllerName+" "+method.getName() +param);

            if(method.getGenericParameterTypes().length>0){
                httpMap.put("genparam",method.getGenericParameterTypes());
            }

            //map.put("http",httpMap);

            //map.put("method",method);

            list.add(httpMap);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
