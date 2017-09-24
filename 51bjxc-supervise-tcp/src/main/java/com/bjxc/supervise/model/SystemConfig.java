package com.bjxc.supervise.model;

/**
 * 系统配置
 * @author levin
 *
 */
public class SystemConfig {
	private Integer id;
	private String Inscode;
	private String flagName;
	private String flagValue;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInscode() {
		return Inscode;
	}
	public void setInscode(String inscode) {
		Inscode = inscode;
	}
	public String getFlagName() {
		return flagName;
	}
	public void setFlagName(String flagName) {
		this.flagName = flagName;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFlagValue() {
		return flagValue;
	}
	public void setFlagValue(String flagValue) {
		this.flagValue = flagValue;
	}

	
	
	
}
