package com.bjxc.supervise.netty;

/**
 *	消息头 
 */
public class Header  {
	
	private boolean security;
	private byte version=(byte)128; //协议版本
	private short	 id;//消息ID
	private short property;// 消息体属性
	private String mobile;// 终端手机号，16位
	private short number;//消息流水号
	private byte backup;//预留
	private int length; //消息体长度
	private String encrypt; //数据加密方式
	private boolean subPackage = false; //是否有子包
	private short packageSize;//包个数
	private short packageIndex;//包编号
	
	
	
	public static short maxLength = 20;
	public boolean isSecurity() {
		return security;
	}
	public void setSecurity(boolean security) {
		this.security = security;
	}
	public byte getVersion() {
		return version;
	}
	public void setVersion(byte version) {
		this.version = version;
	}
	public short getId() {
		return id;
	}
	public void setId(short id) {
		this.id = id;
	}
	public short getProperty() {
		return property;
	}
	public void setProperty(short property) {
		this.property = property;
		
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		
		while (mobile.length() < 16) {
			mobile = "0" + mobile;
			
		}
		this.mobile = mobile;
	}
	public short getNumber() {
		return number;
	}
	public void setNumber(short number) {
		this.number = number;
	}
	public byte getBackup() {
		return backup;
	}
	public void setBackup(byte backup) {
		this.backup = backup;
	}
	public int getLength() {
		return length;
	}
	
	
	public void setLength(int length) {
		this.length = length;
	}
	public String getEncrypt() {
		return encrypt;
	}
	
	public void setPackageIndex(short packageIndex) {
		this.packageIndex = packageIndex;
	}
	public void setPackageSize(short packageSize) {
		this.packageSize = packageSize;
	}
	public short getPackageSize() {
		return packageSize;
	}
	public short getPackageIndex() {
		return packageIndex;
	}
	public boolean isSubPackage() {
		return subPackage;
	}
	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}
	public void setSubPackage(boolean subPackage) {
		this.subPackage = subPackage;
	}
	
	
	

	
	
}
