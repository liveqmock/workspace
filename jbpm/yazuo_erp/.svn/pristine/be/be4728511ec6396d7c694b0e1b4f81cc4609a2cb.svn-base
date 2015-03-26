package com.yazuo.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ObjectToArryList {
	

  
	public static void main(String[] args){
	/*	JdbcTemplateImp jdbcTemplateI = new JdbcTemplateImp();
		CardBatch batch = new CardBatch();
		batch.setBatchId(1); 
		batch.setCardBatch(2);
		batch.setCreateDate(new java.util.Date());
		jdbcTemplateI.save(new JdbcTemplate(), batch,"batch");*/
	}
    /*
     * (non-Javadoc)
     * @see com.yazuo.tools.JdbcTemplateI#save(org.springframework.jdbc.core.JdbcTemplate, java.lang.Object, java.lang.String)
     */
    
	@SuppressWarnings("unchecked")
	public static Object[]  getListByObject(Object obj) {
		ArrayList list = new ArrayList(); 
		for (Field field : obj.getClass().getDeclaredFields()) { 
		Object result = getObjectValue(field,obj); 
		list.add(field.getName() +"  值：   "+ result); 
		} 
		//System.out.println("参数个数："+ list.toArray().length);
	/*	for(Object string :list.toArray() ){
			System.out.print(string + " ");
		}*/
		return list.toArray(); 
	}
	
	/**
	 * 获取对象内各属性值
	 * @param field
	 * @param target
	 * @return
	 */
	public static Object getObjectValue(Field field,Object target) { 
		Object result = null; 
		try { 
		Method method = target.getClass().getMethod( "get" + 
				new StringBuffer(field.getName()).replace(0, 1, field.getName().substring(0, 1) .toUpperCase()), null); 
		result = method.invoke(target,null); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} 
		return result; 
	} 
	/**
	 * 深度复制,需要实体类继承Serializable
	 * @author yaoxiongjian
	 * @param src 源对象
	 * @return 复制对象
	 */
	 public static Object deepClone(Object src) {
		Object dst = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream oo = new ObjectOutputStream(out);
			oo.writeObject(src);
			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			ObjectInputStream oi = new ObjectInputStream(in);
			dst = oi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return dst;
	}
	
   
}
