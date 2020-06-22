package com.ethan.ryds.common.utils;


import com.ethan.ryds.common.constant.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	
	public R() {
		put("code", Constants.HttpStatus.INTERNAL_SERVER_SUCCESS.getStatus());
		put("msg", Constants.HttpStatus.INTERNAL_SERVER_SUCCESS.getMsg());
	}
	
	public static R error() {
		return error(Constants.HttpStatus.INTERNAL_SERVER_ERROR.getStatus(), Constants.HttpStatus.INTERNAL_SERVER_ERROR.getMsg());
	}
	
	public static R error(String msg) {
		return error(Constants.HttpStatus.INTERNAL_SERVER_ERROR.getStatus(), msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("msg", msg);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
