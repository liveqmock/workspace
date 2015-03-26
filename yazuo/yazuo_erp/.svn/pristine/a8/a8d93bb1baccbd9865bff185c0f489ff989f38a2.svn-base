package com.yazuo.erp.base;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 */
public class ViewUtils {
    /**
     * SelectedAttributeUtils.getAttributes()
     * @param list
     * @param attributes
     * @param <E>
     * @return
     */
    public static <E> List<Map<String, Object>> getAttributes(List<E> list, String... attributes) {
        List<Map<String, Object>> result = new LinkedList<Map<String, Object>>();
        for (Object element : list) {
            Map<String, Object> object = new HashMap<String, Object>();
            if (element instanceof Map) {//Map
                for (String attribute : attributes) {
                    object.put(attribute, ((Map) element).get(attribute));
                }
            } else {//Bean
                for (String attribute : attributes) {
                    try {
                        Object a = BeanUtilsBean.getInstance().getPropertyUtils().getNestedProperty(element, attribute);
                        object.put(attribute, a);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
            result.add(object);
        }
        return result;
    }

    /**
     * 将map转换成List
     * @param map
     * @return
     */
    public static List<Map<String, String>> mapToList(Map<String, String> map) {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        for (Map.Entry<String, String> e : map.entrySet()) {
            Map<String, String> m = new HashMap<String, String>();
            m.put("value", e.getKey());
            m.put("text", e.getValue());
            result.add(m);
        }
        return result;

    }
}
