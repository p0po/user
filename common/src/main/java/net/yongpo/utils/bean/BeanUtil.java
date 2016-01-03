package net.yongpo.utils.bean;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by p0po on 15/2/27.
 */
public class BeanUtil {

    public static Map<String, Object> objectFildToMap(Object o) throws IllegalAccessException {
        Map<String, Object> result = new HashMap<String, Object>();
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            result.put(field.getName(), field.get(o));
        }
        return result;
    }

    public static Map<String, Object> objectGetToMap(Object obj) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Map<String, Object> result = new HashMap<String, Object>();

        if (obj instanceof Collection) {
            result.put("collection", obj);
            return result;
        }

        if (obj instanceof Number) {
            result.put("number", obj);
            return result;
        }

        BeanInfo info = Introspector.getBeanInfo(obj.getClass());

        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            Method reader = pd.getReadMethod();
            if (reader != null && !"class".equals(pd.getName()))
                result.put(pd.getName(), reader.invoke(obj));
        }
        return result;
    }


}
