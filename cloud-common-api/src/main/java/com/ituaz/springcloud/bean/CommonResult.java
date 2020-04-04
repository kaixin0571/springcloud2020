package com.ituaz.springcloud.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
	private Integer code;
	private String message;
	private T data;

	public CommonResult(Integer code, String message) {
		this(code, message, null);
	}

	public CommonResult<T> success(T data) {
		return new CommonResult<T>(200, "Success", data);
	}
	
	public CommonResult<T> success(T data,String msg) {
		return new CommonResult<T>(200, msg, data);
	}

}
