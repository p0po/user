package net.yongpo.utils.json;

import com.alibaba.fastjson.JSON;
import net.yongpo.utils.bean.BeanUtil;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Map;

/**
 * Created by popo on 2014/10/3.
 */
public class JsonResponse {
    int code = 0;
    Object data;
    String msg = "";

    private static final String SUCCESS = "success";
    private static final String FAILURE = "failed";

    public static JsonResponse ok() {
        return new JsonResponse(0, SUCCESS, null);
    }

    public static JsonResponse ok(Object content) {
        return new JsonResponse(0, SUCCESS, content);
    }

    public static JsonResponse ok(Object content, boolean toAddMore) {
        if (content instanceof Collection) {
            throw new IllegalArgumentException("不支持Collection类型参数，请用ok(content)方法");
        }

        if (content instanceof Number) {
            throw new IllegalArgumentException("不支持Number类型参数，请用ok(content)方法");
        }

        if (toAddMore) {
            try {
                return new JsonResponse(0, SUCCESS, BeanUtil.objectGetToMap(content));
            } catch (IntrospectionException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            }
        } else {
            return ok(content);
        }
    }

    public static JsonResponse bad() {
        return new JsonResponse(1, FAILURE, null);
    }

    public static JsonResponse bad(Object content) {
        return new JsonResponse(1, FAILURE, content);
    }

    public static JsonResponse bad(Object content, boolean toAddMore) {
        if (content instanceof Collection) {
            throw new IllegalArgumentException("不支持Collection类型参数，请用ok(content)方法");
        }

        if (content instanceof Number) {
            throw new IllegalArgumentException("不支持Number类型参数，请用ok(content)方法");
        }

        if (toAddMore) {
            try {
                return new JsonResponse(0, SUCCESS, BeanUtil.objectGetToMap(content));
            } catch (IntrospectionException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                throw new IllegalArgumentException("转化失败");
            } catch (IllegalAccessException e) {
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
    public JsonResponse merge(String key, Object obj) throws IllegalArgumentException {
        if (this.data == null || !(this.data instanceof Map)) {
            throw new IllegalArgumentException("请先用ok(content,true)实例化再调用该方法");
        }
        ((Map) this.data).put(key, obj);
        return this;
    }

    /**
     * 0和1状态不能覆盖，不信你试试
     *
     * @param code
     * @param msg
     * @param content
     * @return
     */
    public static JsonResponse other(int code, String msg, Object content) {
        if (code == 0) {
            msg = SUCCESS;
        }
        if (code == 1) {
            msg = FAILURE;
        }
        return new JsonResponse(code, msg, content);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private JsonResponse(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }


}
