package com.bjxc.model;

public class AmapConversionResult {
	private Integer status;		//结果状态
	private String info;		//结果信息
	private Integer infocode;	//结果代码
	private String locations;	//转换坐标列表
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public Integer getInfocode() {
		return infocode;
	}
	public void setInfocode(Integer infocode) {
		this.infocode = infocode;
	}
	public String getLocations() {
		return locations;
	}
	public void setLocations(String locations) {
		this.locations = locations;
	}
	@Override
	public String toString() {
		return "AmapConversionResult [status=" + status + ", info=" + info + ", infocode=" + infocode + ", locations="
				+ locations + "]";
	}
	
	
}	
