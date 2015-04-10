package com.learningstorm.storm_example;

import java.util.HashMap;
import java.util.Map;

import org.elasticsearch.action.update.UpdateRequestBuilder;

import com.yazuo.redisSpout.ElasticSearchHandler;

public class TestMain {
	private static final Map<Integer, String> map = new HashMap<Integer, String>();
	static {
		map.put(0, "google");
		map.put(1, "facebook");
		map.put(2, "twitter");
		map.put(3, "youtube");
		map.put(4, "linkedin");
	}

	public static void main(String[] args) throws InterruptedException {
//		testHashMap();
//		testString();
		ElasticSearchHandler esHandler = new ElasticSearchHandler();
		esHandler.updateDocument("indexdemo", "storm", "AUxFXKyJm_koQZAKXqTM", "name", "zhangwuji");
	}

	private static void testString() {
		String s = "admin\",\"@version\":\"1\",\"@timestamp\":\"2015-04-01t06:51:42.653z";
	   String substring = s.substring(0, s.indexOf("\""));
	   System.out.println(substring);
	}

	private static void testHashMap() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			Thread.sleep(33);
			if(map.size()!=0){
				System.err.println("tttttttt "+map.get(i));
				System.out.println(map);
				System.out.println(map.size());
				map.remove(i);
			}
		}
	}
}
