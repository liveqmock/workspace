package com.learningstorm.storm_example;

import java.util.HashMap;
import java.util.Map;

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