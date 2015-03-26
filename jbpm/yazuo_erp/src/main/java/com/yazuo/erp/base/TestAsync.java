package com.yazuo.erp.base;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
@Repository  
public class TestAsync {

	@Async
	public void testAsyncMethod() {
		try {
			System.out.println("进入异步方法！");
			// 让程序暂停100秒，相当于执行一个很耗时的任务
			Thread.sleep(3000);
			System.out.println("执行完成！");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
