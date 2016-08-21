package com.zane.generic.obj;

import java.io.PrintWriter;
import java.io.StringWriter;

public class GenericError {

	private int code;
	private String message; 
	private String stackTrace;


	public GenericError(int code, String message){
		this.code = code;
		this.message = message;
	}

	public GenericError(int code, String message, Throwable e) {	 
		this.code = code;
		this.message = message;
		setStackTrace(e);
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(Throwable e) {
		StringWriter s = new StringWriter();
		e.printStackTrace(new PrintWriter(s));
		this.stackTrace = s.toString();
	}


}
