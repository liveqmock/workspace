//package com.yazuo.weixin.util.getXml;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.Timestamp;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.util.EntityUtils;
//import org.dom4j.Document;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;
//
//public class GetDishesInfo {
//	static List<Info> infoList = new ArrayList<Info>();
//	static String date = "2013-01-1";
//
//	public static void main(String args[]) {
//		String sql = null;
//		try {
//			GetDishesInfo gdi = new GetDishesInfo();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			System.out.println("第一次查询");
//			String xml = gdi.getXml(date, "");
//		
//			for (int i = 1; i < 2; i++) {
//				while (gdi.readStringXml(xml, date) != 0) {
//					System.out.println("不为空翻页");
//					xml = gdi.getXml(date,
//							infoList.get(infoList.size() - 1).Eat_CheckID);
//				}
//				
//				System.out.println("当内存中数据插入数据库");
//				insert();
//				System.out.println("清空内存");
//				infoList=new ArrayList<Info>();
//				
//				Timestamp ts = new Timestamp(sdf.parse("2013-01-1").getTime()
//						+ i * 24 * 3600 * new Long(1000));
//				date = sdf.format(ts);
//				System.out.println("查询下一天的数据，再次装入内存");
//				xml = gdi.getXml(date, "");
//
//			}
//		} catch (Exception ee) {
//			System.out.print(ee.getMessage());
//			System.out.println(sql);
//		}
//	}
//
//	static void insert(){
//		String sql = null;
//		
//		try {
//			Class.forName("org.postgresql.Driver").newInstance();
//			String url = "jdbc:postgresql://192.168.236.210/crm";
//			Connection con = DriverManager.getConnection(url, "weixin",
//					"weixin");
//			Statement st = con.createStatement();
//			for (int i = 0; i < infoList.size(); i++) {
//				sql = "insert into weixin.jinbaiwan_caipin_20130723 (eat_checkid,eat_checkindex,eat_mannum,"
//						+ "eat_xfbmid,eat_text,eat_number,door_autoid,door_name,date,cash_consume,store_consume,integral_consume,dishes_coupon,eat_payInfo,eat_etime,eat_zmoney) "
//						+ "values ('"
//						+ infoList.get(i).Eat_CheckID
//						+ "','"
//						+ infoList.get(i).Eat_CheckIndex
//						+ "','"
//						+ infoList.get(i).Eat_ManNum
//						+ "','"
//						+ infoList.get(i).Eat_XfbmID
//						+ "','"
//						+ infoList.get(i).Eat_Text
//						+ "','"
//						+ infoList.get(i).Eat_Number
//						+ "','"
//						+ infoList.get(i).Door_Autoid
//						+ "','"
//						+ infoList.get(i).Door_name
//						+ "','"
//						+ infoList.get(i).date
//						+ "','"
//						+ infoList.get(i).cash_consume
//						+ "','"
//						+ infoList.get(i).store_consume
//						+ "','"
//						+ infoList.get(i).integral_consume
//						+ "','"
//						+ infoList.get(i).dishes_coupon
//						+ "','"
//						+ infoList.get(i).Eat_PayInfo
//						+ "','"
//						+ infoList.get(i).Eat_Etime
//						+ "','"
//						+ infoList.get(i).eat_zmoney + "');";
//				// System.out.println(sql);
//				st.execute(sql);
//			}
//
//			st.close();
//			con.close();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	public int readStringXml(String xml, String date) {
//		Document doc = null;
//		int count = 0;
//		try {
//			doc = DocumentHelper.parseText(xml); // 将字符串转为XML
//
//			Element rootElt = doc.getRootElement(); // 获取根节点
////			Element NewDataSet = rootElt.element("NewDataSet");
//			Iterator iterss = rootElt.elementIterator("Table"); // /获取根节点下的子节点body
//
//			// 遍历body节点
//			while (iterss.hasNext()) {
//				Element recordEless = (Element) iterss.next();
//				Info i = new Info();
//				String Eat_CheckID = recordEless.elementTextTrim("Eat_CheckID"); // 拿到body节点下的子节点result值
//				String Eat_CheckIndex = recordEless
//						.elementTextTrim("Eat_CheckIndex");
//				String Eat_ManNum = recordEless.elementTextTrim("Eat_ManNum");
//				String Eat_XfbmID = recordEless.elementTextTrim("Eat_XfbmID");
//				String Eat_Text = recordEless.elementTextTrim("Eat_Text");
//				String Eat_Number = recordEless.elementTextTrim("Eat_Number");
//				String Door_Autoid = recordEless.elementTextTrim("Door_Autoid");
//				String Door_name = recordEless.elementTextTrim("Door_name");
//				String eat_zmoney = recordEless.elementTextTrim("eat_zmoney");
//				String consume = recordEless.elementTextTrim("Eat_PayInfo");
//				String Eat_Etime = recordEless.elementTextTrim("Eat_Etime");
//
//				String[] consumes = consume.split(",");
//				double couponConsume = 0;
//				for (int j = 0; j < consumes.length; j++) {
//					if (consumes[j].equals("现金")) {
//						i.setCash_consume(consumes[j + 1]);
//						continue;
//					}
//					if (consumes[j].equals("雅座储值消费")) {
//						i.setStore_consume(consumes[j + 1]);
//						continue;
//					}
//					if (consumes[j].equals("雅座积分消费")) {
//						i.setIntegral_consume(consumes[j + 1]);
//						continue;
//					}
//					if (consumes[j].indexOf("券") > 0) {
//						couponConsume = couponConsume
//								+ new Double(consumes[j + 1]).doubleValue();
//						continue;
//					}
//				}
//				i.setDishes_coupon(new Double(couponConsume).toString());
//				i.setEat_PayInfo(consume);
//				i.setDoor_Autoid(Door_Autoid);
//				i.setDoor_name(Door_name);
//				i.setEat_CheckID(Eat_CheckID);
//				i.setEat_CheckIndex(Eat_CheckIndex);
//				i.setEat_ManNum(Eat_ManNum);
//				i.setEat_Number(Eat_Number);
//				i.setEat_Text(Eat_Text);
//				i.setEat_XfbmID(Eat_XfbmID);
//				i.setDate(date);
//				i.setEat_zmoney(eat_zmoney);
//				i.setEat_Etime(Eat_Etime);
//				infoList.add(i);
//				count++;
//			}
//			System.out.println(infoList.size());
//			return count;
//		} catch (DocumentException e) {
//			e.printStackTrace();
//			return count;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return count;
//		}
//
//	}
//
//	public String getXml(String data, String lastId)
//			throws MalformedURLException, Exception {
//		org.codehaus.xfire.client.Client client = new org.codehaus.xfire.client.Client(new URL("http://118.144.86.102/upload/services.asmx?wsdl"));   
//        Object[] results11 = client.invoke("GetConsumeInfo", new Object[]{data,data,lastId});  
//        String result= (String)results11[0];
//		return result;
//	}
//}
