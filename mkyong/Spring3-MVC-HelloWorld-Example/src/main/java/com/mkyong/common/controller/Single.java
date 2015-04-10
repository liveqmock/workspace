package com.mkyong.common.controller;

public class Single {
	// 私有的默认构造子
	private Single() {
	}

	// 注意，这里没有final
	private static ElasticSearchHandler esHandler = null;

	// 静态工厂方法
	public static ElasticSearchHandler getInstance() {
		if (esHandler == null) {
			esHandler = new ElasticSearchHandler();
		}
		return esHandler;
	}
}