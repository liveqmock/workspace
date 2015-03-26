package com.yazuo.erp.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * json 输出结构
 * {
 *   "flag": true,
 *   "message": "success or failure message",
 *   "data": {
 *   }
 * }
 * 使用方法:
 * flag: true & false<br/>
 * message: 成功或失败的消息<br/>
 * data: 本身是一个Hash<br/>
 *
 * Result result = new Result()<br/>
 * Result result = new Result(false)<br/>
 * Result result = new Result(true,"message")<br/>
 *
 *Data为Map
 * new JsonResult(true)<br/>
 *        .setMessage("success or failure message")<br/>
 *        .putData("hello", "json")<br/>
 *        .putData("table",new ArrayList());<br/>
 *
 * data为list
 * new JsonResult(true)<br/>
 *    .setMessage("success or failure message")<br/>
 *    .addData("hello")<br/>
 *    .addData("world");<br/>
 *
 * data为其他任意对象
 * List list = new ArrayList();
 * new JsonResult(true,"success message").setData(list);
 *
 * @author congshuanglong
 */
public class JsonResult {
    private boolean flag;
    private String message;
    private Object data;
    private static final ObjectMapper mapper = new ObjectMapper();

    public JsonResult() {
    }
    public JsonResult(boolean flag) {
        this.flag = flag ;
    }

    public JsonResult(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
    
    public JsonResult(Object data, boolean flag ) {
    	this.flag = flag;
    	this.data = data;
    }
    public JsonResult(Object data, boolean flag , String message) {
    	this.flag = flag;
    	this.data = data;
        this.message = message;
    }

    public int getFlag() {
        return flag ? 1 : 0;
    }

    public JsonResult setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonResult setMessage(String message) {
        this.message = message;
        return this;
    }

    public Object getData() {
        if (data == null) {
            this.data = new HashMap<String, Object>();
        }
        return data;
    }

    public JsonResult setData(Object data) {
        this.data = data;
        return this;
    }

    /**
     * 向Map类型data中插入数据
     * @param name
     * @param value
     */
    public JsonResult putData(String name, Object value) {
        Map<String, Object> map = this.initData(HashMap.class);
        map.put(name, value);
        return this;
    }

    /**
     * 向List类型data中添加数据
     * @param object
     * @return
     */
    public JsonResult addData(Object object) {
        List<Object> list =  this.initData(ArrayList.class);
        list.add(object);
        return this;
    }

    private <E> E initData(Class<?> klass){
        if (this.data == null) {
            try {
                this.data = klass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return (E) this.data;
    }

    /**
     * @Description 测试方法， 输出json格式
     * @param object
     * @throws
     */
    public static String getJsonString(Object object){
    	try {
//    		mapper.setSerializationInclusion(Inclusion.NON_NULL); //不显示为空的属性
    		mapper.disable(DeserializationConfig.Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * @Description 测试方法， 输出json格式, 如果有null输出""
     * @param object
     * @throws
     */
    public static String getJsonStringHandleNull(Object object){
    	try {
//    		mapper.setSerializationInclusion(Inclusion.NON_NULL); //不显示为空的属性
    		ObjectMapper mapper1 = new JsonNullObjectMapper();
			return mapper1.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
	/**
	 * 字符串转VO对象
	 * @param <T>
	 * @return 
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static <T> Object readValueToVO(String input, @SuppressWarnings("rawtypes") Class<T> clazz) {
		Object object =null;
		try {
			mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			mapper.configure(org.codehaus.jackson.JsonParser.Feature.ALLOW_COMMENTS, true);
			object = mapper.readValue(input.getBytes(), clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
}
