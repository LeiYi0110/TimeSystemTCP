package com.bjxc.supervise.Action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.bjxc.json.JacksonBinder;
import com.bjxc.model.AmapConversionResult;
import com.bjxc.supervise.http.crypto.DecodeUtil;
import com.bjxc.supervise.http.crypto.HttpClientService;
import com.bjxc.supervise.model.DownTransportMessageBody;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.IBytes;
import com.bjxc.supervise.model.LocationInfo;
import com.bjxc.supervise.model.LoginInfo;
import com.bjxc.supervise.model.ParamBS;
import com.bjxc.supervise.model.TransportObject;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.GeneralResponse;
import com.bjxc.supervise.netty.Header;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.server.SpringApplicationContext;
import com.bjxc.supervise.netty.server.TCPMap;
import com.bjxc.supervise.service.DeviceService;
import com.bjxc.supervise.service.LocationInfoService;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class Utils {
	
	private static final Logger logger = LoggerFactory.getLogger(Utils.class);
	private static Utils util;
	private LocationInfoService locationInfoService;
	private static String webServiceKey; 
	
	public void init() {
		util = this;
		util.locationInfoService= this.locationInfoService;
	}

	@Value("${bjxc.amap.webServiceKey}")
	private void setWebServiceKey(String key){
		webServiceKey = key;
	}

	public void setLocationInfoService(LocationInfoService locationInfoService) {
		this.locationInfoService = locationInfoService;
	}

	/**
	 * 获取普通Message
	 * @param data
	 * @param messageId
	 * @param mobile
	 * @return
	 */
	public static Message getCommonMessage(IBytes data,short messageId,String mobile,short number) {
		Message message = new Message();
		message.setHeader(getHeader(messageId, mobile,number));
		if(data!=null){
			message.setBody(data.getBytes());
		}
		return message;
	}

	/**
	 * 获取下行透传Message
	 * @param data
	 * @param transportId
	 * @param mobile
	 * @return
	 */
	public static Message getDownTransportMessage(TransportObject data,short transportId,String mobile,String deviceNo,short seq, short number){
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq(seq);	//TODO:驾培包序号,从1开始，除协议中特别声明外，循环递增
		extendMessageBody.setDeviceNo(deviceNo);
		extendMessageBody.setData(data);
		
		DownTransportMessageBody downTransportMessageBody = new DownTransportMessageBody();
		downTransportMessageBody.setExtendMessageBody(extendMessageBody);
		
		Message message = new Message();
		message.setHeader(getHeader((short)0x8900,mobile,number));
		message.setBody(downTransportMessageBody.getBytes());
		return message;
	}
	
	/**
	 * 获取上行透传Message
	 * @param data
	 * @param transportId
	 * @param mobile
	 * @return
	 */
	public static Message getUpTransportMessage(TransportObject data,short transportId,String mobile,String deviceNo,short seq, short number){
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq(seq);	//TODO:驾培包序号,从1开始，除协议中特别声明外，循环递增
		extendMessageBody.setDeviceNo(deviceNo);
		extendMessageBody.setData(data);
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
		upTransportMessageBody.setExtendMessageBody(extendMessageBody);
		
		Message message = new Message();
		message.setHeader(getHeader((short)0x0900,mobile,number));
		message.setBody(upTransportMessageBody.getBytes());
		return message;
	}
	
	
	private static int num = 1;
	/**
	 * 获取上行透传Message
	 * @param data
	 * @param transportId
	 * @param mobile
	 * @return
	 */
	public static Message getUpTransportMessage2(TransportObject data,short transportId,String mobile,String deviceNo,String cadata,String capwd){
		
		//封装扩展消息
		num++;
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq((short)num);	//TODO:驾培包序号,从1开始，除协议中特别声明外，循环递增
		extendMessageBody.setDeviceNo(deviceNo);
		extendMessageBody.setData(data);
		
//		String encodedEncryptedStr = DecodeUtil.getEncodedEncryptedStr(extendMessageBody.getBytes(), cadata, capwd);
//		extendMessageBody.setCheck(HexUtils.HexStringToBinary(encodedEncryptedStr));
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
		upTransportMessageBody.setExtendMessageBody(extendMessageBody);

		Message message = new Message();
		message.setHeader(getHeader((short)0x0900,mobile,MessageUtils.getCurrentMessageNumber()));
		message.setBody(upTransportMessageBody.getBytes());
		
		return message;
	}	
	
	
	/**
	 * 把Message作为body再封装进消息里面，以便发送到特定Action进行直接转发处理，
	 * 其中外层消息除header中的id外没有什么意义（mobile也可用于查找通道）
	 * @param data
	 * @return
	 */
	public static Message packageMessage(Message message,Short messageId,String mobile){
		Header header = new Header();
		header.setId(messageId);
		header.setMobile(mobile);
		header.setNumber((short)1);
		
		Message newMessage = new Message();
		newMessage.setHeader(header);
		if(message!=null){
			newMessage.setBody(message.getBytes());
		}
		return newMessage;
	}
	
	
	/**
	 * 获取header
	 * @param messageId
	 * @param mobile
	 * @return
	 */
	public static Header getHeader(short messageId, String mobile,short number) {
		Header header = new Header();
		header.setId(messageId);
		header.setMobile(mobile);
		header.setNumber(number);
		return header;
	}
	
	public static byte[] getInt2byte(int data){
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeInt(data);
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	
	public static byte[] getShort2byte(short data){
		ByteBuf buffer = Unpooled.buffer();
		buffer.writeShort(data);
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	
	public static byte[] getString2byte(String data){
		ByteBuf buffer = Unpooled.buffer();
		try {
			buffer.writeBytes(data.getBytes("GBK"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] result = new byte[buffer.readableBytes()];
		buffer.readBytes(result);
		return result;
	}
	
	public static Integer getByte2Int(byte[] bytes){
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		return copiedBuffer.readInt();
	}
	
	public static Short getByte2Short(byte[] bytes){
		ByteBuf copiedBuffer = Unpooled.copiedBuffer(bytes);
		return copiedBuffer.readShort();
	}
	
	public static GeneralResponse parseGeneralResponse(byte[] bytes)
	{
		GeneralResponse generalResponse = new GeneralResponse();
		
		ByteBuf hbuf = Unpooled.copiedBuffer(bytes);
		
		short seq = hbuf.readShort();
		short requestMessageId =  hbuf.readShort();
		byte result = hbuf.readByte();
		generalResponse.setSeq(seq);
		generalResponse.setRequestMessageId(requestMessageId);
		generalResponse.setResult(result);
		
		
		return generalResponse;
	}
	
	/**
	 * 测试用
	 * 获取测试用的LocationInfo对象
	 * @return
	 */
	public static LocationInfo getDefaultLocationInfo(){
		LocationInfo locationInfo = new LocationInfo();
		locationInfo.setAlertInfo(1);
		locationInfo.setCarSpeed((short)11.1);
		locationInfo.setGpsSpeed((short)10.2);
		int latitude=(int)(22563100+(Math.random()*100));	//获取随机经度
		locationInfo.setLatitude(latitude);
		int longtitude=(int)(113815000+(Math.random()*1000));	//获取随机维度
		locationInfo.setLongtitude(longtitude);
		locationInfo.setOrientation((short)56);
		locationInfo.setStatus(1);
		locationInfo.setTime("161130112233");
		return locationInfo;
	}
	
	public static void sendToWeb(Message message)
	{
		String content = JacksonBinder.buildNonDefaultBinder().toJson(message);
		HttpClientService httpClientService = SpringApplicationContext.getInstance().getContext().getBean(HttpClientService.class);
		try
		{
			httpClientService.doGet("http://112.74.129.7:8709" + "/message?inscode=" + "5851633716061642" + "&content=" + URLEncoder.encode(content, "UTF-8"));
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	/**
	 * 把终端上传上来的位置信息存到数据库
	 * @param locationInfo
	 * @param mobile
	 * @return
	 */
	public static com.bjxc.model.LocationInfo insertLocationInfo(com.bjxc.supervise.model.LocationInfo locationInfo,String mobile) {
		LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(mobile);
		logger.info(mobile);
		switch (loginInfo.getMapMode()) {
		case 1:	//GPS类坐标
			logger.info("GPS坐标");
			locationInfo = WGS84ToGCJ02(locationInfo,"gps");
			break;
		case 2:	//百度类坐标
			logger.info("百度坐标");
			locationInfo = WGS84ToGCJ02(locationInfo,"baidu");
			break;
		default:	//谷歌类坐标
			logger.info("谷歌坐标");
			break;
		}
		Integer trainingRecordId = loginInfo.getTrainingRecordId();
		com.bjxc.model.LocationInfo insertLocationInfo = new com.bjxc.model.LocationInfo();
		try {
			insertLocationInfo.setAlertInfo(locationInfo.getAlertInfo());
			insertLocationInfo.setCarSpeed((int) locationInfo.getCarSpeed());
			insertLocationInfo.setGpsSpeed((int) locationInfo.getGpsSpeed());
			insertLocationInfo.setLatitude(locationInfo.getLatitude());
			insertLocationInfo.setLongtitude(locationInfo.getLongtitude());
			insertLocationInfo.setOrientation((int) locationInfo.getOrientation());
			insertLocationInfo.setStatus(locationInfo.getStatus());
			insertLocationInfo.setTime(locationInfo.getTime());
			insertLocationInfo.setTrainingrecordid(trainingRecordId);
			insertLocationInfo.setDeviceId(loginInfo.getDevice().getId());
			
			List<ParamBS> paramList = locationInfo.getParamList();
			if(paramList!=null){
				for (ParamBS paramBS : paramList) {
					if (paramBS!=null) {
						switch (paramBS.getParamId()) {
						case 1:
							insertLocationInfo.setSum_distance((Integer) paramBS.getParam());
							break;
						case 2:
							insertLocationInfo.setGasonline_cost(Integer.parseInt((short)paramBS.getParam()+""));
							break;
						case 3:
							insertLocationInfo.setElevation(Integer.parseInt((short)paramBS.getParam()+""));
							break;
						case 5:
							insertLocationInfo.setEngine_speed(Integer.parseInt((short)paramBS.getParam()+""));
							break;
						}
					}
				}
			}	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return util.locationInfoService.add(insertLocationInfo);
	}
	
	/**
	 * 把LocationInfo里面的坐标转换成GCJ02坐标
	 * @param type baidu/gps
	 */
	public static LocationInfo WGS84ToGCJ02(LocationInfo locationInfo ,String type) {
		double latitude = locationInfo.getLatitude();
		double longtitude = locationInfo.getLongtitude();
 		double longtitudetemp =  longtitude/1000000;
		double latitudetemp = latitude/1000000;
 		Map map = new HashMap<>();
		map.put("longtitude", longtitudetemp);
 		map.put("latitude", latitudetemp);
 		List<Map> list = new ArrayList<>();
		list.add(map);
		try {
 			List<Map> conversion = conversion(list,type);
			Map map2 = conversion.get(0);
			double longtitude2 = Double.parseDouble((String) map2.get("longtitude")) ;
			double latitude2 = Double.parseDouble((String) map2.get("latitude"));
			locationInfo.setLongtitude((int) (longtitude2*1000000));
			locationInfo.setLatitude((int) (latitude2*1000000));
		} catch (Exception e1) {
 			e1.printStackTrace();
		}
		return locationInfo;
	}
	
	/**
	 * 把GPS坐标改为高德坐标
	 */
	public static List<Map> conversion(List<Map> list,String type) throws Exception{

		if(list.size() < 1){
			return list;
		}

		String coordinateString = StringUtils.join(
				list.stream().filter(f -> f.get("longtitude") != null && f.get("latitude") != null)
						.map(m -> (m.get("longtitude") + "," + m.get("latitude"))).collect(Collectors.toList()),
				";");
		String url = String.format(
				"http://restapi.amap.com/v3/assistant/coordinate/convert?key=%s&locations=%s&coordsys="+type,
				webServiceKey, coordinateString);
		
		logger.info(url);
		
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = client.execute(httpGet);

		List<Map> resultlist = new ArrayList<>();
		try{
			HttpEntity entity = response.getEntity();
			String bodyContent = EntityUtils.toString(entity);
			
			logger.info(bodyContent);
			AmapConversionResult result = JacksonBinder.buildNonNullBinder().fromJson(bodyContent, AmapConversionResult.class);
			if(result.getInfocode().equals(10000)){
				String amapLocations = result.getLocations();
				for (String item : result.getLocations().split(";")) {
					Map map = new HashMap<>();
					map.put("longtitude", item.split(",")[0]);
					map.put("latitude", item.split(",")[1]);
					resultlist.add(map);
				}
			}
			EntityUtils.consume(entity);
		}finally{
			response.close();
		}
		if(resultlist.size() < 1){
			Map map = new HashMap<>();
			resultlist.add(map);
		}
		return resultlist;
	}
}
