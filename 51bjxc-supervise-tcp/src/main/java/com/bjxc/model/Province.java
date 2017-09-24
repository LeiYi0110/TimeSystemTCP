package com.bjxc.model;

import java.util.Date;

public class Province {
	
	private Integer id;
	private String msgid;
	private String extendmsgid;
	private String msgcontent;
	private Date createtime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getExtendmsgid() {
		return extendmsgid;
	}
	public void setExtendmsgid(String extendmsgid) {
		this.extendmsgid = extendmsgid;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
