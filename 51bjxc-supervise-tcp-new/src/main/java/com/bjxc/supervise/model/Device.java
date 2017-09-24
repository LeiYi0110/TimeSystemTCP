package com.bjxc.supervise.model;

public class Device {
	private Integer id;
	/**
	 * 计时终端类型, 1:车载计程计时终端2:课堂教学计时终端3:模拟训练计时终端
	 */
	private Integer termtype;
	/**
	 * 生产厂家
	 */
	private String vender;
	/**
	 * 终端型号
	 */
	private String model;
	/**
	 * 终端IMEI号或设备MAC地址
	 */
	private String imei;
	/**
	 * 终端出厂序列号
	 */
	private String sn;
	
	private String devNum;
	
	private String key;
	
	private String passwd;
	
	private Integer insId;
	
	private Integer mapType;
	
	private String Inscode;
	
	public String getInscode() {
		return Inscode;
	}
	public void setInscode(String inscode) {
		this.Inscode = inscode;
	}
	public String getDevnum() {
		return devNum;
	}
	public void setDevnum(String devnum) {
		this.devNum = devnum;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTermtype() {
		return termtype;
	}
	public void setTermtype(Integer termtype) {
		this.termtype = termtype;
	}
	public String getVender() {
		return vender;
	}
	public void setVender(String vender) {
		this.vender = vender;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public Integer getInsId() {
		return insId;
	}
	public void setInsId(Integer insId) {
		this.insId = insId;
	}
	public Integer getMapType() {
		return mapType;
	}
	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}
	@Override
	public String toString() {
		return "Device [id=" + id + ", termtype=" + termtype + ", vender=" + vender + ", model=" + model + ", imei="
				+ imei + ", sn=" + sn + ", devnum=" + devNum + ", key=" + key + ", passwd=" + passwd + ", insId="
				+ insId + ", mapType=" + mapType + ", Inscode=" + Inscode + "]";
	}
	
}
