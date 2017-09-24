package com.bjxc.model;

/**
 * Created by izzld on 2017/1/4.
 */
public class MessageModel {
    private String messageIdHexString;
    private Object body;
    

    public String getMessageIdHexString() {
		return messageIdHexString;
	}
	public void setMessageIdHexString(String messageIdHexString) {
		this.messageIdHexString = messageIdHexString;
	}

	public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }
}
