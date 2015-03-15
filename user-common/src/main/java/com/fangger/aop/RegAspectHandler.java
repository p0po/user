package com.fangger.aop;

import com.fangger.annotation.Reg;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * Created by popo on 2014/10/6.
 */
@Aspect
@Component
public class RegAspectHandler {
    static final Logger logger = Logger.getLogger(RegAspectHandler.class);
    public RegAspectHandler(){
        if(logger.isDebugEnabled()){
            logger.debug("init RegAspectHandler");
        }
    }

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{

        String methodName = joinPoint.getSignature().getName(); //获取方法名称

        Class<? extends Object> controller = joinPoint.getTarget().getClass(); //获取Controller对象
        String[] regArray= null;
        for(Method method:controller.getDeclaredMethods()){
            if(method.getName().equals(methodName)){
                Annotation[][] ans = method.getParameterAnnotations();
                regArray = new String[ans.length];
                for(int i=0;i<ans.length;i++){
                    regArray[i] = "";
                    for(Annotation an:ans[i]){
                        if("Reg".equals(an.annotationType().getSimpleName())){
                            Reg reg = (Reg)an;
                            regArray[i] = reg.value();
                        }
                    }
                }
            }
        }

        for(int i = 0;i<joinPoint.getArgs().length;i++){
            if(i>=regArray.length){break;}

            String regStr = regArray[i];

            Object arg =joinPoint.getArgs()[i];

            if(!"".equals(regStr)){
                if(!(arg instanceof String)){
                    throw new IllegalArgumentException("@Reg can only with String args in method "+controller.getName()+"."+methodName);
                }

                boolean matched = Pattern.compile(regStr).matcher(joinPoint.getArgs()[i].toString()).matches();

                if(logger.isDebugEnabled()){
                    logger.debug("result: "+matched+" (detect value :"+joinPoint.getArgs()[i].toString()+" with Reg:"+regStr+")");
                }

                if(!matched){
                    throw new IllegalArgumentException("args "+joinPoint.getArgs()[i].toString()+" of method "+controller.getName()+"."+methodName+" is not matched with Red "+regStr);
                }
            }

        }

        return joinPoint.proceed();
    }
}
