package com.app.api.utils;

import org.springframework.stereotype.Service;

@Service
public class Message {

	public ResponseBody ok(Object body) {
		ResponseBody responseBody = new ResponseBody();
		responseBody.setStatus(ResponseBody.status_ok);
		responseBody.setBody(body);
		return responseBody;
	}
	
	public ResponseBody error(Object body) {
		ResponseBody responseBody = new ResponseBody();
		responseBody.setStatus(ResponseBody.status_error);
		responseBody.setBody(body);
		return responseBody;
	}

    public ResponseBody error(String message, String codigo) {
        ResponseBody  responseBody = new ResponseBody();
        responseBody.setStatus(ResponseBody.status_error);
        MessageBodyError errors = new MessageBodyError(codigo, message);
        responseBody.setBody(errors);
        return responseBody;
    }

	public ResponseBody error(String codigo, Object message) {
		ResponseBody responseBody = new ResponseBody();
		MessageBodyError errors = new MessageBodyError(codigo, message);
		responseBody.setStatus(ResponseBody.status_error);
		responseBody.setBody(errors);
		return responseBody;
	}
}
