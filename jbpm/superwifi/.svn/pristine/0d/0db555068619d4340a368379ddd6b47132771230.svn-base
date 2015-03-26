package com.yazuo.api.service.account.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResultInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6131417978607837471L;
	protected boolean success = false;
	protected String message = null;
	protected Map<String, Object> data = new HashMap<String, Object>();

	public ResultInfo() {
		this.success = false;
	}

	public ResultInfo(boolean success) {
		this.success = success;
	}

	public ResultInfo(String key, Object object) {
		this.success = true;
		this.data.put(key, object);
	}

	public ResultInfo(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public ResultInfo(boolean success, String message, Map<String, Object> data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, Object> getAllData() {
		addData("success", success);
		addData("message", message);
		return data;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	public void setData(String key, Object data) {
		this.data.put(key, data);
	}

	public void addData(String key, Object data) {
		this.data.put(key, data);
	}
}
