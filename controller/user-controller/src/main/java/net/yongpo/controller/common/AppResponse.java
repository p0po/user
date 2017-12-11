package net.yongpo.controller.common;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by fengjr on 2015/6/2 0002.
 */
public class AppResponse {
    private static final Map NO_ERROR = null;
    private static final Map FAILURE = ImmutableMap.of("code", -1, "message", "failed");

    boolean success;
    Object data;
    Map error;

    public AppResponse() {

    }

    public Map getError() {
        return error;
    }

    public void setError(Map error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static AppResponse ok() {
        return new AppResponse(true, NO_ERROR, null);
    }

    public static AppResponse ok(Object content) {
        return new AppResponse(true, NO_ERROR, content);
    }

    public static AppResponse ok(Object content, boolean toAddMore) {
        if (content instanceof Collection) {
            throw new IllegalArgumentException("不支持Collection类型参数，请用ok(content)方法");
        }

        if (content instanceof Number) {
            throw new IllegalArgumentException("不支持Number类型参数，请用ok(content)方法");
        }

        if (toAddMore) {
            try {
                return new AppResponse(toAddMore, NO_ERROR, PropertyUtils.describe(content));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            }
        } else {
            return ok(content);
        }
    }

    public static AppResponse bad() {
        return new AppResponse(false, FAILURE, null);
    }

    public static AppResponse bad(String errorMsg) {
        return new AppResponse(false, ImmutableMap.of("code", -1, "message", errorMsg), null);
    }

    public static AppResponse bad(String errorMsg, Object content) {
        return new AppResponse(false, ImmutableMap.of("code", -1, "message", errorMsg), content);
    }

    public static AppResponse bad(int errorCode, String errorMsg) {
        return new AppResponse(false, ImmutableMap.of("code", errorCode, "message", errorMsg), null);
    }

    public static AppResponse bad(int errorCode, String errorMsg, Object content) {
        return new AppResponse(false, ImmutableMap.of("code", errorCode, "message", errorMsg), content);
    }

    public static AppResponse bad(Object content, boolean toAddMore) {
        if (content instanceof Collection) {
            throw new IllegalArgumentException("不支持Collection类型参数，请用ok(content)方法");
        }

        if (content instanceof Number) {
            throw new IllegalArgumentException("不支持Number类型参数，请用ok(content)方法");
        }

        if (toAddMore) {
            try {
                return new AppResponse(false, FAILURE, PropertyUtils.describe(content));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            }
        } else {
            return ok(content);
        }
    }

    /**
     * 合并属性字段到对象
     *
     * @param key
     * @param obj
     * @return
     * @throws IllegalArgumentException
     */
    public AppResponse merge(String key, Object obj) throws IllegalArgumentException {
        if (this.data == null || !(this.data instanceof Map)) {
            throw new IllegalArgumentException("请先用ok(content,true)实例化再调用该方法");
        }
        ((Map) this.data).put(key, obj);
        return this;
    }

    /**
     * 0和1状态不能覆盖，不信你试试
     *
     * @param success
     * @param error
     * @param content
     * @return
     */
    public static AppResponse other(boolean success, Map error, Object content) {
        if (success) {
            new AppResponse(success, NO_ERROR, content);
        }
        return new AppResponse(success, error, content);
    }

    private AppResponse(boolean success, Map error, Object data) {
        this.success = success;
        this.error = error;
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

