package com.otp.dao;

import net.minidev.json.JSONObject;

public class Response {

	private final String status;

	private final JSONObject data;

	private final String message;

	public Response(String status, JSONObject data, String message) {
		this.status = status;
		this.data = data;
		this.message = message;

	}

	public String getStatus() {
		return status;
	}

	public JSONObject getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Response [status=" + status + ", data=" + data + ", message=" + message + "]";
	}

}
