package com.bjxc.jtt.model;

import java.io.UnsupportedEncodingException;

public class JttCar extends IBytes {
	
	private String VIN;	//车牌号, 公安车管部门核发的机动车牌照号码。车牌照号码中不设分隔符号，所有字母数字连续保存，如：京CJ6789，不应保存为“京C—J6789”或“京C.J6789”
	private String VEHICLE_COLOR;	//车牌颜色, 按照JT/T 415 – 2006中5.4.12的规定
	private String VEHICLE_TYPE;	//车牌类型, 按照JT/T 415 – 2006中5.4.9的规定
	private String TRANS_TYPE;	//运输行业编码, 按照JT/T 415 – 2006中5.2.1的规定
	private String VEHICLE_NATIONALITY;	//车籍地, 行政区划代码，按照GB/T 2260的规定
	private String OWERS_ID;	//注意：OWERS_Id我们用做MDTID（终端号）, 业户ID, 该业户ID为下级平台存储业户信息所采用的ID编号
	private String OWERS_NAME;	//OWERS_NAME我们用做“车队名称”, 业户名称, 运输企业名称
	private String OWERS_TEL;	//业户联系电话
	
	public JttCar() {
		super();
	}

	public JttCar(byte[] bytes) {
		try {
			String wholeString = new String(bytes,"GBK");
			String[] split = wholeString.split(";");
			for (int i = 0; i < split.length; i++) {
				String[] value = split[i].split(":=");
				switch (i) {
				case 0:
					VIN = value[1];
					break;
				case 1:
					VEHICLE_COLOR = value[1];
					break;
				case 2:
					VEHICLE_TYPE = value[1];
					break;
				case 3:
					TRANS_TYPE = value[1];
					break;
				case 4:
					VEHICLE_NATIONALITY = value[1];
					break;
				case 5:
					OWERS_ID = value[1];
					break;
				case 6:
					OWERS_NAME = value[1];
					break;
				case 7:
					OWERS_TEL = value[1];
					break;
				default:
					break;
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte[] getBytes() {
		String wholeString = "VIN:=" + VIN + ";VEHICLE_COLOR:=" + VEHICLE_COLOR + ";VEHICLE_TYPE:=" + VEHICLE_TYPE
				+ ";TRANS_TYPE:=" + TRANS_TYPE + ";VEHICLE_NATIONALITY:=" + VEHICLE_NATIONALITY + ";OWERS_ID:="
				+ OWERS_ID + ";OWERS_NAME:=" + OWERS_NAME + ";OWERS_TEL:=" + OWERS_TEL;
		byte[] bytes = null;
		try {
			bytes = wholeString.getBytes("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	public String getVIN() {
		return VIN;
	}
	public void setVIN(String vIN) {
		VIN = vIN;
	}
	public String getVEHICLE_COLOR() {
		return VEHICLE_COLOR;
	}
	public void setVEHICLE_COLOR(String vEHICLE_COLOR) {
		VEHICLE_COLOR = vEHICLE_COLOR;
	}
	public String getVEHICLE_TYPE() {
		return VEHICLE_TYPE;
	}
	public void setVEHICLE_TYPE(String vEHICLE_TYPE) {
		VEHICLE_TYPE = vEHICLE_TYPE;
	}
	public String getTRANS_TYPE() {
		return TRANS_TYPE;
	}
	public void setTRANS_TYPE(String tRANS_TYPE) {
		TRANS_TYPE = tRANS_TYPE;
	}
	public String getVEHICLE_NATIONALITY() {
		return VEHICLE_NATIONALITY;
	}
	public void setVEHICLE_NATIONALITY(String vEHICLE_NATIONALITY) {
		VEHICLE_NATIONALITY = vEHICLE_NATIONALITY;
	}
	public String getOWERS_ID() {
		return OWERS_ID;
	}
	public void setOWERS_ID(String oWERS_ID) {
		OWERS_ID = oWERS_ID;
	}
	public String getOWERS_NAME() {
		return OWERS_NAME;
	}
	public void setOWERS_NAME(String oWERS_NAME) {
		OWERS_NAME = oWERS_NAME;
	}
	public String getOWERS_TEL() {
		return OWERS_TEL;
	}
	public void setOWERS_TEL(String oWERS_TEL) {
		OWERS_TEL = oWERS_TEL;
	}

	@Override
	public String toString() {
		return "JttCar [VIN=" + VIN + ", VEHICLE_COLOR=" + VEHICLE_COLOR + ", VEHICLE_TYPE=" + VEHICLE_TYPE
				+ ", TRANS_TYPE=" + TRANS_TYPE + ", VEHICLE_NATIONALITY=" + VEHICLE_NATIONALITY + ", OWERS_ID="
				+ OWERS_ID + ", OWERS_NAME=" + OWERS_NAME + ", OWERS_TEL=" + OWERS_TEL + "]";
	}

}
