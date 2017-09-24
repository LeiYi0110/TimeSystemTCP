package com.bjxc.jtt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.bjxc.jtt.model.JttBase;
import com.bjxc.jtt.model.JttCar;
import com.bjxc.jtt.model.JttCoach;
import com.bjxc.jtt.model.JttGnss;
import com.bjxc.jtt.model.JttLogin;
import com.bjxc.jtt.model.JttPhoto;
import com.bjxc.jtt.model.JttWarn;
import com.bjxc.model.Coach;
import com.bjxc.model.DeviceImage;
import com.bjxc.model.Ins;
import com.bjxc.model.LocationInfo;
import com.bjxc.model.TrainingCar;
import com.bjxc.supervise.model.Device;

public class JttUtils {
	
	public static final String JTT_CHANNEL = "jtt_channel"; 
	
	public static int serialNumber = 0;
	public static synchronized int getCurrentMessageSerialNumber(){
		return serialNumber++;
	}
	
	/**
	 * 1, 车辆定位数据
	 * @param locationInfo
	 * @return
	 */
	public static JttMessage locationTransform(LocationInfo locationInfo,TrainingCar trainingCar){
		
		JttGnss jttGnss = getJttGnss(locationInfo);
		
		JttBase jttBase = getJttBase(trainingCar);
		jttBase.setDATA_TYPE((short)0x1202);
		jttBase.setDATA(jttGnss);
		
		JttMessage jttMessage = new JttMessage();
		jttMessage.setData(jttBase.getBytes());
		jttMessage.setType((short)0x1200);
		return jttMessage;
	}
	
	/**
	 * 2, 车辆静态数据
	 * @param device
	 * @param trainingCar
	 * @return
	 */
	public static JttMessage carTransform(Device device,TrainingCar trainingCar,Ins ins){
		JttCar jttCar = new JttCar();
		jttCar.setVIN(trainingCar.getLicnum());
		switch (trainingCar.getPlatecolor()) {
		case 1:
		case 2:
		case 3:
		case 4:
			jttCar.setVEHICLE_COLOR(trainingCar.getPlatecolor()+"");
			break;
		default:
			jttCar.setVEHICLE_COLOR("9");
			break;
		}
		jttCar.setVEHICLE_TYPE("14");
		jttCar.setTRANS_TYPE("051");
		jttCar.setVEHICLE_NATIONALITY(ins.getDistrict());
		jttCar.setOWERS_NAME(ins.getName());
		
		JttBase jttBase = getJttBase(trainingCar);
		jttBase.setDATA_TYPE((short)0x1601);
		jttBase.setDATA(jttCar);
		
		JttMessage jttMessage = new JttMessage();
		jttMessage.setData(jttBase.getBytes());
		jttMessage.setType((short)0x1600);
		return jttMessage;
	}
	
	/**
	 * 3, 监控照片
	 * @param deviceImage
	 * @param locationInfo
	 * @return
	 */
	public static JttMessage photoTransform(DeviceImage deviceImage,LocationInfo locationInfo,TrainingCar trainingCar,byte[] fileBytes){
		JttPhoto jttPhoto = new JttPhoto();
		jttPhoto.setPHOTO_RSP_FLAG((byte)0x01);
		jttPhoto.setGBSS_DATA(getJttGnss(locationInfo));
		jttPhoto.setLENS_ID((byte) 1);
		jttPhoto.setPHOTO_LEN(deviceImage.getDataSize());
		jttPhoto.setSIZE_TYPE((byte)deviceImage.getImageSize());
		jttPhoto.setTYPE((byte) 1);
		jttPhoto.setPHOTO(fileBytes);
		
		JttBase jttBase = getJttBase(trainingCar);
		jttBase.setDATA_TYPE((short)0x1502);
		jttBase.setDATA(jttPhoto);
		
		JttMessage jttMessage = new JttMessage();
		jttMessage.setData(jttBase.getBytes());
		jttMessage.setType((short)0x1500);
		
		return jttMessage;
	}
	
	/**
	 * 4, 报警
	 * @param locationInfo
	 * @param trainingCar
	 * @return
	 */
	public static JttMessage alertTransform(LocationInfo locationInfo,TrainingCar trainingCar){
		JttWarn jttWarn = new JttWarn();
		jttWarn.setWARN_SRC((byte) 1);
		Integer alertInfo = locationInfo.getAlertInfo();
		if(alertInfo != null){
			jttWarn.setWARN_TYPE(Short.parseShort(alertInfo+""));
		}else{
			jttWarn.setWARN_TYPE((short) 0);
		}
		String time = locationInfo.getTime();
		Date parse = null;
		try {
			parse = new SimpleDateFormat("yyMMddHHmmss").parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		jttWarn.setWARN_TIME(parse.getTime()/1000-800);
		jttWarn.setINFO_ID(locationInfo.getId());
		jttWarn.setINFO_LENGTH(0);
		jttWarn.setINFO_CONTENT("");
		
		JttBase jttBase = getJttBase(trainingCar);
		jttBase.setDATA_TYPE((short)0x1402);
		jttBase.setDATA(jttWarn);
		
		JttMessage jttMessage = new JttMessage();
		jttMessage.setData(jttBase.getBytes());
		jttMessage.setType((short)0x1400);
		
		return jttMessage;
	}
	
	/**
	 * 5, 从业资格证和驾驶证
	 * @param coach
	 * @return
	 */
	public static JttMessage coachTransform(String idcard,String name,TrainingCar trainingCar){
		JttCoach jttCoach = new JttCoach();
		if(idcard == null){
			idcard = "";
		}
		for (int i = idcard.length(); i < 20; i++) {
			idcard += "\000";	//身份证不足20位补0x00
		}
		jttCoach.setDRIVER_ID(idcard);
		if(name == null){
			name = "";
		}
		for (int i = getChineseLength(name,"GBK"); i < 16; i++) {
			name += "\000";	//姓名不足16位补0x00
		}
		jttCoach.setDRIVER_NAME(name);
		JttBase jttBase = getJttBase(trainingCar);
		jttBase.setDATA_TYPE((short)0x120A);
		jttBase.setDATA(jttCoach);
		
		JttMessage jttMessage = new JttMessage();
		jttMessage.setData(jttBase.getBytes());
		jttMessage.setType((short)0x1200);
		return jttMessage;
	}
	
	/**
	 * 登录消息
	 * @param link_ip
	 * @param port
	 * @return
	 */
	public static JttMessage login(String link_ip,short port){
		JttLogin jttLogin = new JttLogin();
		jttLogin.setUserid(1300013);
		jttLogin.setPassword("1300014"+"\000");
		if(link_ip == null){
			link_ip = "";
		}
		for (int i = link_ip.length(); i < 32; i++) {
			link_ip += "\000";	//姓名不足32位补0x00
		}
		jttLogin.setDown_link_ip(link_ip);	//TODO:修改ip
		jttLogin.setDown_link_port(port);
		
		JttMessage jttMessage = new JttMessage();
		jttMessage.setData(jttLogin.getBytes());
		jttMessage.setType((short) 0x1001);
		return jttMessage;
	}
	
	public static JttMessage heartMessage() {
		JttMessage jttMessage = new JttMessage();
		jttMessage.setType((short)0x1005);
		return jttMessage;
	}
	
	private static JttGnss getJttGnss(LocationInfo locationInfo) {
		String time = locationInfo.getTime();
		JttGnss jttGnss = new JttGnss();
		jttGnss.setENCRTYPT((byte) 0);
		byte[] date = {Byte.parseByte(time.substring(4,6)),Byte.parseByte(time.substring(2,4)),(byte)0x07,Byte.parseByte(time.substring(0,2))};
		jttGnss.setDATE(date);
		byte[] date1 = {Byte.parseByte(time.substring(6,8)),Byte.parseByte(time.substring(8,10)),Byte.parseByte(time.substring(10,12))};
		jttGnss.setTIME(date1);
		jttGnss.setLON(locationInfo.getLongtitude());
		jttGnss.setLAT(locationInfo.getLatitude());
		jttGnss.setVEC1((short) (locationInfo.getGpsSpeed()/10));
		jttGnss.setVEC2((short) (locationInfo.getCarSpeed()/10));
		jttGnss.setVEC3(locationInfo.getSum_distance());
		Integer orientation = locationInfo.getOrientation();
		if(orientation !=null){
			jttGnss.setDIRECTION(Short.parseShort(orientation+""));
		}else{
			jttGnss.setDIRECTION((short) 0);
		}
		Integer elevation = locationInfo.getElevation();
		if(elevation != null){
			jttGnss.setALTTTUDE(Short.parseShort(elevation+""));
		}else{
			jttGnss.setALTTTUDE((short) 0);
		}
		jttGnss.setSTATE(locationInfo.getStatus());
		jttGnss.setALARM(locationInfo.getAlertInfo());
		return jttGnss;
	}
	
	private static JttBase getJttBase(TrainingCar trainingCar){
		JttBase jttBase = new JttBase();
		int platecolor = trainingCar.getPlatecolor();
		switch (platecolor) {
		case 1:
		case 2:
		case 3:
		case 4:
			jttBase.setVEHICLE_COLOR((byte) platecolor);
			break;
		default:
			jttBase.setVEHICLE_COLOR((byte) 9);
			break;
		}
		String licnum = trainingCar.getLicnum();
		if(licnum == null){
			licnum = "";
		}
		for (int i = getChineseLength(licnum,"GBK"); i < 21; i++) {
			licnum += "\000";
		}
		jttBase.setVEHICLE_NO(licnum);
		return jttBase;
	}

	/**
	 * 计算中英文结合的字符串的真实长度
	 * @param name
	 * @param endcoding
	 * @return
	 */
	public static int getChineseLength( String name , String endcoding ){
		try {
        int len = 0 ; //定义返回的字符串长度
        int j = 0 ;
        //按照指定编码得到byte[]
        byte[] b_name = null;
			b_name = name.getBytes( endcoding );
		
        while ( true ){
            short tmpst = (short) ( b_name[ j ] & 0xF0 ) ;
            if ( tmpst >= 0xB0 ){
                if ( tmpst < 0xC0 ){
                    j += 2 ;
                    len += 2 ;
                }
                else if ( ( tmpst == 0xC0 ) || ( tmpst == 0xD0 ) ){
                    j += 2 ;
                    len += 2 ;
                }
                else if ( tmpst == 0xE0 ){
                    j += 3 ;
                    len += 2 ;
                }
                else if ( tmpst == 0xF0 ){
                    short tmpst0 = (short) ( ( (short) b_name[ j ] ) & 0x0F ) ;
                    if ( tmpst0 == 0 ){
                        j += 4 ;
                        len += 2 ;
                    }
                    else if ( ( tmpst0 > 0 ) && ( tmpst0 < 12 ) ){
                        j += 5 ;
                        len += 2 ;
                    }
                    else if ( tmpst0 > 11 ){
                        j += 6 ;
                        len += 2 ;
                    }
                }
            }
            else{
                j += 1 ;
                len += 1 ;
            }
            if ( j > b_name.length - 1 ){
                break ;
            }
        }
        return len ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}return 0;
    }
	
	public static byte[] getContent(File file) throws IOException {  
        long fileSize = file.length();  
        if (fileSize > Integer.MAX_VALUE) {  
            System.out.println("file too big...");  
            return null;  
        }  
        FileInputStream fi = new FileInputStream(file);  
        byte[] buffer = new byte[(int) fileSize];  
        int offset = 0;  
        int numRead = 0;  
        while (offset < buffer.length  
        && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  
            offset += numRead;  
        }  
        // ȷ���������ݾ�����ȡ  
        if (offset != buffer.length) {  
        	throw new IOException("Could not completely read file " + file.getName());  
        }  
        fi.close();  
        return buffer;  
    } 
}
