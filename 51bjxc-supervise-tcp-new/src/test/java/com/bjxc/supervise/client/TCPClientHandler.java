/*package com.bjxc.supervise.client;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Case;
import org.aspectj.apache.bcel.generic.NEW;
import org.aspectj.bridge.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.SimpleTriggerContext;

import com.bjxc.supervise.model.DownTransportMessageBody;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.IBytes;
import com.bjxc.supervise.model.LocationInfo;
import com.bjxc.supervise.model.LocationTemporary;
import com.bjxc.supervise.model.LoginCoach;
import com.bjxc.supervise.model.LoginStudent;
import com.bjxc.supervise.model.LogoutCoach;
import com.bjxc.supervise.model.PhotoUploadInit;
import com.bjxc.supervise.model.ResendPackage;
import com.bjxc.supervise.model.PhotoFileUpload;
import com.bjxc.supervise.model.LogoutStudent;
import com.bjxc.supervise.model.ParamBS;
import com.bjxc.supervise.model.ParamDS;
import com.bjxc.supervise.model.ResponseCoachLogin;
import com.bjxc.supervise.model.ResponsePhotoImmediately;
import com.bjxc.supervise.model.ResponsePhotoQuery;
import com.bjxc.supervise.model.ResponsePhotoUploadCertain;
import com.bjxc.supervise.model.ResponsePhotoUploadInit;
import com.bjxc.supervise.model.ResponseStudentLogin;
import com.bjxc.supervise.model.StudyingTimeOrder;
import com.bjxc.supervise.model.StudyingTime;
import com.bjxc.supervise.model.ResponseStudyingTimeOrder;
import com.bjxc.supervise.model.ResponseTerminalQuery;
import com.bjxc.supervise.model.ResponseTimeTerminalSetParam;
import com.bjxc.supervise.model.ResponseUnableTraining;
import com.bjxc.supervise.model.Terminal;
import com.bjxc.supervise.model.TerminalOrder;
import com.bjxc.supervise.model.TerminalQuery;
import com.bjxc.supervise.model.TransportObject;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.Header;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.sun.mail.handlers.message_rfc822;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import sun.security.util.Length;

import com.bjxc.supervise.Action.*;

public class TCPClientHandler extends ChannelHandlerAdapter {
	private static final Logger logger = LoggerFactory.getLogger(TCPClientHandler.class);
	private static List<Message> list = new ArrayList<Message>();
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		平台登录 1f0
//		String msg =  "7e80810021400000015938947284007b000005000572504e39363538644b2f4e7530785256714e6b47583359657771504933464b585a3330592b6c682f2f52396c56326a6a427434527173542f53626c667a7672357544495a784f78322b72774f687a6133726c47747746474b674937443059656b46774561326e4d6a54593748444668666a4779754f2f4a706a73355671432b324e752b776c6f7550534f534c4b514139696373326a6e6e396d6f34547163374c70584145476a51754d5541775067594a4b6f5a496876634e41516b564d5445454c325577494751334947593349446c6b4944493549475668494759794944493349474e6b49444a6c49444a69494467784944526a4944493249444533494759784d4330774954414a4267557244674d4347675541424254394f78336a2f4a73654777504a61566c354f62643949696d6b504151495468703075674143346d453d657e";
		String msg =  "7E8001020104000001578947893200823C589D536A89322C8348DE266439ACA6A60F4F530E727261CF46440AAF68F86FAD749E706604372309D4E38F56AEA7ACC984A2424744F6372DE5BB72710B1A203B6C933F3CB9CCC033AEF699241847717D0137EF79AF837FEC3C039B5221DD549D21F1956B865756274423925938281C8343C1FF5461F3997C858F98CCE4C6E1CE486486037C0B563043AC5E90B4466FA342235A3CDCEE19D9F722A2AD7A59782359247111BF0D78379B9D05759DE26A91E3D86DA317F619126405860B81B14C2568E0A1D998170F1C7F0F50FBCC34F8A671985341CB5987157D02B71967B9A614E9AA4C171D5ADD61673228A2CB95DCEC5CBE3AB1F8E5109FF49154695A8447AC275286B14A6F437E";
		msg =  "7E800900018D000001578947893208833C130203002201EF3939393731393531343334313431393900000072393939373139353134333431343139393137303231303030303101393338313232333436313336363238363433393431343131373531303233373600000064094612121211000000015E001400000001000000010260841506EA60D6006F006600381702101346110104000000780502006440DD9F9E49968F812FF6EDBA5D980097AE1235257A6E6BD00062586DB392740823C6F9A7EAF2AA84010C8903EC795E0DC578E2B2D0B539C6FEC34B9C0D3D130B278D654D4476D9915F1F393F9BD97181F7AF2A3468633025795161A2E8A1AB09EAEE9BAB9AB8AB02D0717527B0996D6786F4FAE6D842752406B9174143280EECBC75FCE1EEABE21E036A2C359ABDECCA64D45A41A9F17FD1D877A345B0ADC45E85AE08D8CD9A48BB0E2540519F895C25331DA32F0D1B6B57C6DB9854A6ADE9EC0C9489D1EA58B02B513D9F08054293C23F1098C6490FE9B2476C6D04121D5759B81649E0D498E99BFFBB4FC1965737893237FB87075D4A846148501526771256787E";
		byte[] req = HexUtils.HexStringToBinary(msg);
		ByteBuf message = Unpooled.buffer(req.length);
		message.writeBytes(req);
		
		ctx.writeAndFlush(message);
		
//		byte[] req2 = new byte[message.readableBytes()];
//		message.readBytes(req2);
//		Message decodeMessage = decodeMessage(req2);
//		ctx.writeAndFlush(decodeMessage);
		
		
//		upPhoto(ctx);
//		upTransportStudyingTime(ctx);


		//模拟网页发送封装消息
		LogoutStudent logoutStudent = new LogoutStudent();
		logoutStudent.setStudentNum("9381223461366286");
		logoutStudent.setLogoutTime(HexUtils.str2Bcd("161204112233"));
		logoutStudent.setThisLoginAliveTime((short)1);
		logoutStudent.setThisLoginAliveMileage((short)1);
		logoutStudent.setCourseId(1);
		logoutStudent.setLocationInfo(Utils.getDefaultLocationInfo());
		Message defaultUpTransportMessage = getDefaultUpTransportMessage(logoutStudent, (short)0x0202, "学员登出checkString");

		Message test = Utils.packageMessage(defaultUpTransportMessage,(short)-1,"88899990000");
		ctx.writeAndFlush(test);

	}
	
	private Message decodeMessage(byte[] msg){
		ByteBuf buf = Unpooled.buffer();
		buf.writeBytes(msg);
		Message  message = new Message();
		Header	header = new Header();
		message.setHeader(header);
		
		header.setVersion(buf.readByte());
		
		header.setId(buf.readShort());
		//璁剧疆娑堟伅浣撳睘鎬�
		short property = buf.readShort();
		this.setProerty(header,property);
		
		byte[] mobileBytes = new byte[8];
		buf.readBytes(mobileBytes);
//		System.out.println(HexUtils.bcd2Str(mobileBytes).length());
//		System.out.println(HexUtils.bcd2Str(mobileBytes));
		header.setMobile(HexUtils.bcd2Str(mobileBytes));
		//header.setMobile(buf.readLong());
		header.setNumber(buf.readShort());
		
		header.setBackup(buf.readByte());
		if(header.isSubPackage()){
			//鏈夊垎鍖呬俊鎭紝鎵嶈鍙栨秷鎭殑鍒嗗寘椤�
			header.setPackageSize(buf.readShort());
			header.setPackageIndex(buf.readShort());
		}
		
		byte[] body = new byte[buf.readableBytes()];
		buf.readBytes(body);
		message.setBody(body);
		
		System.out.println("getNumber:"+message.getHeader().getNumber());
		try {
			System.out.println("getBodyToString"+message.getBodyToString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message;
	}
	
	private void setProerty(Header header,short property){
		header.setProperty(property);
		short a = 1 << 13;
		//short a = 1 << 12;
		if((property & a) == a){
			//娑堟伅鏈夊垎鍖�
			//瑙ｉ噴娑堟伅鍖呭皝鐘堕」
			header.setSubPackage(true);
		}
		//鍔犲瘑鏂瑰紡
		//0x400 : 10000000000
		//0x1c00 : 1110000000000
		if((property & 0x400) == 0x400){
			//閲囩敤浜哛SA鍔犲瘑
			header.setEncrypt("RSA");
		}else if((property & 0x1c00) == 0){
			//娌℃湁缂栫爜
			header.setEncrypt(null);
		}
		//1023 111111111
		short length = (short)(property & 1023);
		//short length = (short)(property - a);
		header.setLength(length);
		
		
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		ByteBuf result = (ByteBuf) msg;
        byte[] result1 = new byte[result.readableBytes()];
        result.readBytes(result1);
        System.out.println("Server said:" + new String(result1));
        result.release();
		 
		Message message = (Message)msg;
		
		list.add(message);
		
		if (list.size() == message.getHeader().getPackageSize()) {
			
			Message finalMessage = MessageUtils.unionPackage(list);
			
			ByteBuf hbuf = Unpooled.copiedBuffer(finalMessage.getBody());
			
			logger.info(String.valueOf(hbuf.readShort()));
			logger.info(String.valueOf(hbuf.readByte()));
			
			byte[] platformNo = new byte[5];
			hbuf.readBytes(platformNo);
			
			byte[] insNo = new byte[16];
			hbuf.readBytes(insNo);
			
			byte[] deviceNo = new byte[16];
			hbuf.readBytes(deviceNo);
			
			byte[] password = new byte[12];
			hbuf.readBytes(password);
			
			byte[] certi = new byte[hbuf.readableBytes()];
			hbuf.readBytes(certi);
			
			try
			{
				logger.info(new String(platformNo,"GBK"));
				logger.info(new String(insNo,"GBK"));
				logger.info(new String(deviceNo,"GBK"));
				logger.info(new String(password,"GBK"));
				logger.info(new String(certi,"GBK"));
				
				if ((new String(certi,"GBK")).equals("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")) {
					logger.info("OK");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			logger.info("body:" + message.getBodyToString());
		}
		
		logger.info("message id:" + Integer.toHexString(message.getHeader().getId()));
		logger.info("version:" + message.getHeader().getVersion());
		logger.info("property:" + Integer.toBinaryString(message.getHeader().getProperty()));
		logger.info("encrypt:" + message.getHeader().getEncrypt());
		logger.info("length:" + message.getHeader().getLength());
		logger.info("subpackage:" + message.getHeader().isSubPackage());
		logger.info("packageSize:" + message.getHeader().getPackageSize());
		logger.info("packageIndex:" + message.getHeader().getPackageIndex());
		logger.info("packageIndex:" + message.getHeader().getMobile());
		logger.info("body:" + message.getBodyToString());
		
		System.out.println("返回消息id: "+message.getHeader().getId());
		System.out.println("(short)0x8900 : "+(short)0x8900);
		System.out.println("终端接收到消息: "+message.getBodyToString());
		
		try{
		if(message.getHeader().getId() == (short)0x8900){//上报应答 message id
//			if(false){
			byte[] body = message.getBody();
			DownTransportMessageBody downTransportMessageBody = new DownTransportMessageBody(body);
			ExtendMessageBody extendMessageBody = downTransportMessageBody.getExtendMessageBody();
			
			System.out.println("透传id: "+extendMessageBody.getTransportId());
			switch (extendMessageBody.getTransportId()) {
				case (short)0x8101://教练员登录应答
					System.out.println("教练员登录应答");
					ResponseCoachLogin responseCoachLogin = (ResponseCoachLogin)extendMessageBody.getData();
					System.out.println(responseCoachLogin.getCoachNum());
					System.out.println(responseCoachLogin.getResultCode());
					System.out.println(responseCoachLogin.getHasExtraMsg());
					System.out.println(responseCoachLogin.getExtraMsgLen());
					System.out.println(responseCoachLogin.getExtraMsg());
					upTransportStudentLogin(ctx);
					break;
				case (short)0x8201:	//学员登录应答
					System.out.println("学员登录应答");
					upTransportStudentLogout(ctx);
					break;
				case (short)0x8202:	//学员登出应答
					System.out.println("学员登出应答");
					takePicture(ctx);
				break;
				case (short)0x8306:	//照片上传应答
					System.out.println("照片上传应答");
					upTransportStudyingTime(ctx);
				break;
				case (short)0x8301:	//
					System.out.println("");
					ResponsePhotoImmediately photo=(ResponsePhotoImmediately)extendMessageBody.getData();
					photo.setCameraNum((byte)2);
					Message message2=getDefaultUpTransportMessage(photo, (short)0x0301, "拍照应答");
					ctx.writeAndFlush(message2);
					takePicture(ctx);
					break;
				case (short)0x8305:	//照片初始化应答
					System.out.println("照片初始化应答");
					upPhoto(ctx);
//					System.out.println("dasdhkjsahdkjashdkjhasdjkhjbvmxnbmbsmdnbfmdsbfmn");
//					ResponsePhotoUploadInit response=(ResponsePhotoUploadInit)extendMessageBody.getData();
//					if(response.getResultCode()==0){
//						File file=new File("/Users/dev/Desktop/9160da2ejw1f3c330kt68j20l90ltwfc.jpg");
//						PhotoFileUpload pfile=new PhotoFileUpload();
//						pfile.setPhotoNum("1234567890");
//						pfile.setFile(file);
//						Message message3=getDefaultUpTransportMessage(pfile, (short)0x0306, "照片上传");
//						ctx.writeAndFlush(message3);
//					}
					break;
				default:
					break;
			}
		}
		}catch (Exception e) {
			// TODO: handle exception
		e.printStackTrace();
		}
		
		
		if (message.getHeader().getId() == (short)0x8100) {
			
			//System.out.println("0x8100");
			decodeRegisterAction(message);
			
		}
		
		
	}

-----------------服务器发送数据到终端TriggerAction.java  begin-----------------
	private void sendResendPackage(ChannelHandlerContext ctx){
		ResendPackage resendPackage = new ResendPackage();
		resendPackage.setOriginalSerialNum((short)1);
		resendPackage.setResendPackageNumber((byte)1);
		ArrayList<Short> arrayList = new ArrayList<Short>();
		arrayList.add((short)1);
		arrayList.add((short)3);
		arrayList.add((short)5);
		resendPackage.setParamList(arrayList);
	}
	 
	private void sendTimeTerminalQueryParam(ChannelHandlerContext ctx){
		sendWebUpMessage(ctx, null, (short)0x8503, "查询计时终端应用参数");
	}
	
	private void sendUnableTraining(ChannelHandlerContext ctx){
		ResponseUnableTraining responseUnableTraining = new ResponseUnableTraining();
		responseUnableTraining.setMessage("禁用消息");
		responseUnableTraining.setUnableTrainingStatus((byte)1);
		sendWebUpMessage(ctx, responseUnableTraining, (short)0x8502, "设置禁用状态");
	}
	
	private void sendTimeTerminalSetParam(ChannelHandlerContext ctx){
		ResponseTimeTerminalSetParam responseTimeTerminalSetParam = new ResponseTimeTerminalSetParam();
		responseTimeTerminalSetParam.setAnswerInterval((byte)10);
		responseTimeTerminalSetParam.setAutoLogoutDelay((short)30);
		responseTimeTerminalSetParam.setEnableCrossSchoolStuding((byte)1);
		responseTimeTerminalSetParam.setEnableCrossSchoolTeaching((byte)1);
		responseTimeTerminalSetParam.setGetPhotoInterval((byte)1);
		responseTimeTerminalSetParam.setGNSSInterval((short)1000);
		responseTimeTerminalSetParam.setHasExtalMsg((byte)1);
		responseTimeTerminalSetParam.setParamId((byte)0);
		responseTimeTerminalSetParam.setReValidate((byte)30);
		responseTimeTerminalSetParam.setStopStudingTimeDelay((byte)30);
		responseTimeTerminalSetParam.setUploadSetting((byte)1);
		sendWebUpMessage(ctx, responseTimeTerminalSetParam, (short)0x8501, "设置计时终端应用参数");
	}
	
	private void sendLocationTemporary(ChannelHandlerContext ctx){
		LocationTemporary locationTemporary = new LocationTemporary();
		locationTemporary.setTimeInterval((short)222);
		locationTemporary.setTimeValidity(333);
		sendWebDefaultMessage(ctx, locationTemporary, (short)0x882022, "临时位置跟踪");
	}
	
	private void sendLocationInfo(ChannelHandlerContext ctx){
//		sendWebDefaultMessage(ctx, null, (short)0x8200, "位置汇报");
	}
	
	private void sendLocationQuery(ChannelHandlerContext ctx){
		sendWebDefaultMessage(ctx, null, (short)0x88201, "位置信息查询");
	}
	
	private void sendTerminalOrder(ChannelHandlerContext ctx){
		TerminalOrder terminalOrder = new TerminalOrder();
		terminalOrder.setOrderCode((byte)4);
		terminalOrder.setOrder("");
		sendWebDefaultMessage(ctx, terminalOrder, (short)0x88105, "终端控制");
	}
	
	private void sendTerminalQuery2(ChannelHandlerContext ctx){
		TerminalQuery terminalQuery = new TerminalQuery();
		terminalQuery.setParamNumber((byte)3);
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(3);
		arrayList.add(6);
		arrayList.add(22);
		terminalQuery.setParamList(arrayList);
		sendWebDefaultMessage(ctx, terminalQuery, (short)0x88106, "查询指定终端参数");
	}
	
	private void sendTerminalQuery(ChannelHandlerContext ctx){
		sendWebDefaultMessage(ctx, null, (short)0x88104, "查询终端参数");
	}
	
	private void sendTerminal(ChannelHandlerContext ctx){
		Terminal terminal = new Terminal();
		terminal.setParameterCount((byte)3);
		terminal.setParameterNumber((byte)3);
		ArrayList<ParamDS> arrayList = new ArrayList<ParamDS>();
		arrayList.add(new ParamDS((int)0x0004,(byte)4, Utils.getInt2byte(1001)));
		arrayList.add(new ParamDS((int)0x0018,(byte)4, Utils.getInt2byte(80)));
		arrayList.add(new ParamDS((int)0x0022,(byte)4, Utils.getInt2byte(21)));
		terminal.setParamList(arrayList);
		sendWebDefaultMessage(ctx, terminal, (short)0x88103, "设置终端参数");
	}
	
	private void sendResponsePhotoUploadCertain(ChannelHandlerContext ctx){
		ResponsePhotoUploadCertain responsePhotoUploadCertain = new ResponsePhotoUploadCertain();
		responsePhotoUploadCertain.setPhotoNum("1234567890");
		sendWebUpMessage(ctx, responsePhotoUploadCertain, (short)0x8304, "上传指定照片");
	}
	
	private void sendResponsePhotoQuery(ChannelHandlerContext ctx){
		ResponsePhotoQuery responsePhotoQuery = new ResponsePhotoQuery();
		responsePhotoQuery.setQueryMode((byte)1);
		responsePhotoQuery.setStartTime(HexUtils.str2Bcd("120304112233"));
		responsePhotoQuery.setEndTime(HexUtils.str2Bcd("120306112233"));
		sendWebUpMessage(ctx, responsePhotoQuery, (short)0x8302, "查询照片");;
	}
	
	private void sendResponsePhotoImmediately(ChannelHandlerContext ctx){
		ResponsePhotoImmediately responsePhotoImmediately = new ResponsePhotoImmediately();
		responsePhotoImmediately.setUpMode((byte)255);
		responsePhotoImmediately.setCameraNum((byte)21);
		responsePhotoImmediately.setPhotoSize((byte)1);
		sendWebUpMessage(ctx, responsePhotoImmediately, (short)0x8301, "立即拍照");;
	}
	
	private void sendResponseStudyingTimeOrder(ChannelHandlerContext ctx){
		ResponseStudyingTimeOrder responseStudyingTimeOrder = new ResponseStudyingTimeOrder();
		responseStudyingTimeOrder.setQueryMode((byte)1);
		responseStudyingTimeOrder.setQueryNumber((short)10);
		responseStudyingTimeOrder.setQueryStartTime(HexUtils.str2Bcd("120304112233"));
		responseStudyingTimeOrder.setQueryEndTime(HexUtils.str2Bcd("130304112233"));
		sendWebUpMessage(ctx, responseStudyingTimeOrder, (short)0x8205, "命令上报学时");
	}
-----------------服务器发送数据到终端TriggerAction.java  end-----------------
	
	private void locationQuery(ChannelHandlerContext ctx){
		sendDefaultMessage(ctx, null, (short)0x8201);
	}
	
	private void locationTemporaryQuery(ChannelHandlerContext ctx){
		LocationTemporary locationTemporary = new LocationTemporary();
		locationTemporary.setTimeInterval((short)1);
		locationTemporary.setTimeValidity(2);
		sendDefaultMessage(ctx, locationTemporary, (short)0x8202);
	}
	
	private void terminalOrder(ChannelHandlerContext ctx){
		TerminalOrder terminalOrder = new TerminalOrder();
		terminalOrder.setOrderCode((byte)1);
		terminalOrder.setOrder("第一个参数");
		sendDefaultMessage(ctx, terminalOrder, (short)0x8105);
	}
	
	private void locationInfo(ChannelHandlerContext ctx){
		LocationInfo defaultLocationInfo = Utils.getDefaultLocationInfo();

		ArrayList<ParamBS> arrayList = new ArrayList<ParamBS>();
		arrayList.add(new ParamBS((byte)1,(byte)4,Utils.getInt2byte(1000)));
		arrayList.add(new ParamBS((byte)2,(byte)2,Utils.getShort2byte((short)20)));
		arrayList.add(new ParamBS((byte)3,(byte)2,Utils.getShort2byte((short)30)));
		arrayList.add(new ParamBS((byte)5,(byte)2,Utils.getShort2byte((short)40)));

		defaultLocationInfo.setParamList(arrayList);
		sendDefaultMessage(ctx, defaultLocationInfo, (short)0x0200);
	}
	
	消息ID：0x8104
	private void terminalQuery(ChannelHandlerContext ctx) {
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(6);
		arrayList.add(8);
		arrayList.add(23);
		
		TerminalQuery terminalQuery = new TerminalQuery();
		terminalQuery.setParamNumber((byte)3);
		terminalQuery.setParamList(arrayList);
		
		sendDefaultMessage(ctx, terminalQuery, (short)0x8104);
	}
	
	消息ID：0x8103
	private void terminal(ChannelHandlerContext ctx){
		ArrayList<ParamDS> arrayList = new ArrayList<ParamDS>();
		arrayList.add(new ParamDS((int)0x0001,(byte)8, Utils.getString2byte("aaa第一个参数值")));
		arrayList.add(new ParamDS((int)0x0002,(byte)8, Utils.getString2byte("bbb第二个参数值")));
		Terminal terminal = new Terminal();
		terminal.setParameterCount((byte)1);
		terminal.setParameterNumber((byte)1);
		terminal.setParamList(arrayList);
		
		sendDefaultMessage(ctx,terminal, (short)0x8103);
	}
	
	private void upPhoto(ChannelHandlerContext ctx){
		try {
			File file=new File("/Users/dev/Desktop/9160da2ejw1f3c330kt68j20l90ltwfc.jpg");
			PhotoFileUpload pfile=new PhotoFileUpload();
			pfile.setPhotoNum("1234567890");
			pfile.setFile(file);
			
			ExtendMessageBody extendMessageBody = new ExtendMessageBody();
			extendMessageBody.setTransportId((short)0x0306);
			extendMessageBody.setExtendMessageProperty((short)0);
			extendMessageBody.setSeq((short)1);
			extendMessageBody.setDeviceNo("1234560123456789");
			extendMessageBody.setData(pfile);
			
			UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
			upTransportMessageBody.setExtendMessageBody(extendMessageBody);
			
			Message defaultUpTransportMessage = getDefaultUpTransportMessage(pfile, (short)0x0306, "照片上传");
			
			Header header = new Header();
			header.setId((short)0x0888);
			header.setMobile("15099998888");
			header.setNumber((short)1);
			
			Message message = new Message();
			message.setHeader(header);
//			message.setBody(pfile.getBytes());
			message.setBody(upTransportMessageBody.getBytes());
			System.out.println(message.getBody().length);
			for(Message msg: MessageUtils.dividePackage(defaultUpTransportMessage)){
				ctx.writeAndFlush(msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void takePicture(ChannelHandlerContext ctx){
		PhotoUploadInit photo=new PhotoUploadInit();
		photo.setPhotoNum("1234567890");
		photo.setStuNum("6593441306021328");
		photo.setUpMode((byte)7);
		photo.setCameraNum((byte)2);
		photo.setPhotoSize((byte)70);
		photo.setPhotoType((byte)18);
		photo.setClassId(1);
		photo.setCount((short)10);
		photo.setCountSize(1000);
		photo.setFace((byte)70);
		LocationInfo defaultLocationInfo = Utils.getDefaultLocationInfo();
		List<ParamBS> paramList = new ArrayList<ParamBS>();
		paramList.add(new ParamBS((byte)1,(byte)4,Utils.getInt2byte(12)));
		paramList.add(new ParamBS((byte)2,(byte)2,Utils.getShort2byte((short)80)));
		paramList.add(new ParamBS((byte)3,(byte)2,Utils.getShort2byte((short)70)));
		paramList.add(new ParamBS((byte)5,(byte)2,Utils.getShort2byte((short)90)));
		defaultLocationInfo.setParamList(paramList);
		photo.setLocationInfo(defaultLocationInfo);
		Message message=getDefaultUpTransportMessage(photo, (short)0x0305, "照片上传");
		
		ctx.writeAndFlush(message);
	}
	
	private void decodeRegisterAction(Message message) {
		// TODO Auto-generated method stub
		
		byte[] body = message.getBody();
		ByteBuf hbuf = Unpooled.copiedBuffer(body);
		
		short number = hbuf.readShort();
		byte result =  hbuf.readByte();
		
		
		byte[] platformNo = new byte[5];
		hbuf.readBytes(platformNo);
		
		byte[] insNo = new byte[16];
		hbuf.readBytes(insNo);
		
		byte[] deviceNo = new byte[16];
		hbuf.readBytes(deviceNo);
		
		byte[] password = new byte[12];
		
		hbuf.readBytes(password);
		
		byte[] certi = new byte[message.getHeader().getLength() - 52];
		hbuf.readBytes(certi);
		
		try
		{
			System.out.println(new String(platformNo,"GBK"));
			System.out.println(new String(insNo,"GBK"));
			System.out.println(new String(deviceNo,"GBK"));
			System.out.println(new String(password,"GBK"));
			System.out.println(new String(certi,"GBK"));

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		byte[] cbyt = new byte[5];
		hbuf.readBytes(cbyt);
		try {
			String creater = new String(cbyt,"GBK");
			System.out.println(creater);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
	
	private void responseTimeOrder(ChannelHandlerContext ctx){
		StudyingTimeOrder responseStudyingTimeOrder = new StudyingTimeOrder();
		responseStudyingTimeOrder.setResultCode((byte)1);
		
		Message defaultUpTransportMessage = getDefaultUpTransportMessage(responseStudyingTimeOrder, (short)0x0205, "客户端回复学时查询情况");
		ctx.writeAndFlush(defaultUpTransportMessage);
	}
	
	*//**
	 * 服务器下发指令请求客户端上报学时记录
	 * @param ctx
	 *//*
	private void timeOrder(ChannelHandlerContext ctx){
		ResponseStudyingTimeOrder studyingTimeOrder = new ResponseStudyingTimeOrder();
		studyingTimeOrder.setQueryMode((byte)1);
		studyingTimeOrder.setQueryStartTime(HexUtils.str2Bcd("151122112211"));
		studyingTimeOrder.setQueryEndTime(HexUtils.str2Bcd("161122112211"));
		studyingTimeOrder.setQueryNumber((short)100);
		Message defaultUpTransportMessage = getDefaultUpTransportMessage(studyingTimeOrder, (short)0x8205, "服务器下发指令请求客户端上报学时记录");
		
		ctx.writeAndFlush(defaultUpTransportMessage);
	}
	
	private void upTransportStudyingTime(ChannelHandlerContext ctx){
		StudyingTime studyingTime = new StudyingTime();
		studyingTime.setStudyingTimeNum("61061475449505151612020001");
		studyingTime.setUploadType((byte)0x01);
		studyingTime.setCoachNum("6593441306021328");
		studyingTime.setStudentNum("9381223461366286");
		studyingTime.setCourseId(100);
		studyingTime.setRecordTime("094733");
		studyingTime.setCourse("1212110000");
		studyingTime.setRecordStatus((byte)0);
		studyingTime.setMaxSpeed((short)35);		
		studyingTime.setMileage((short)2);
		
		LocationInfo defaultLocationInfo = Utils.getDefaultLocationInfo();
		ArrayList<ParamBS> arrayList = new ArrayList<ParamBS>();
		arrayList.add(new ParamBS((byte)1,(byte)4,Utils.getInt2byte(12)));
		arrayList.add(new ParamBS((byte)5,(byte)2,Utils.getShort2byte((short)80)));
		defaultLocationInfo.setParamList(arrayList);

		studyingTime.setLocationInfo(defaultLocationInfo);
		
		sendDefaultUpTransportMessage(ctx,studyingTime,(short)0x0203,"上报学时checkString");
	}

	private void upTransportStudentLogout(ChannelHandlerContext ctx){
		LogoutStudent logoutStudent = new LogoutStudent();
		logoutStudent.setStudentNum("9381223461366286");
		logoutStudent.setLogoutTime("161204112233");
		logoutStudent.setThisLoginAliveTime((short)1);
		logoutStudent.setThisLoginAliveMileage((short)1);
		logoutStudent.setCourseId(1);
		logoutStudent.setLocationInfo(Utils.getDefaultLocationInfo());
		Message defaultUpTransportMessage = getDefaultUpTransportMessage(logoutStudent, (short)0x0202, "学员登出checkString");

		ctx.writeAndFlush(defaultUpTransportMessage);
	}
	
	private void upTransportStudentLogin(ChannelHandlerContext ctx){
		LoginStudent loginStudent = new LoginStudent();
		loginStudent.setStudentNum("9381223461366286");
		loginStudent.setCoachNum("6593441306021328");
		loginStudent.setCourse("1122334455");
		loginStudent.setCourseId(1111);
		loginStudent.setLocationInfo(Utils.getDefaultLocationInfo());
		Message defaultUpTransportMessage = getDefaultUpTransportMessage(loginStudent,(short)0x0201, "上报学员登录的checkString");
		
		ctx.writeAndFlush(defaultUpTransportMessage);
	}
	
	private void upTransportCoachLogout(ChannelHandlerContext ctx){
		LogoutCoach logoutCoach = new LogoutCoach();
		logoutCoach.setCoachNum("6593441306021328");
		logoutCoach.setLocationInfo(Utils.getDefaultLocationInfo());
		Message defaultUpTransportMessage = getDefaultUpTransportMessage(logoutCoach, (short)0x0102, "教练员登出");
		
		String binaryToHexString = HexUtils.BinaryToHexString(defaultUpTransportMessage.toByteBuf().array());
		
		System.out.println("消息: "+binaryToHexString.replaceAll(" ", ""));
		ctx.writeAndFlush(defaultUpTransportMessage);
	}
	
	
	private void askPhoto(ChannelHandlerContext ctx){
		Header header = new Header();
		header.setId((short)0X81F0);
		Message message = new Message();
		message.setHeader(header);
		ctx.writeAndFlush(message);
	}
	
	private void downTransportCoachLogin(ChannelHandlerContext ctx) {
		ResponseCoachLogin responseCoachLogin = new ResponseCoachLogin();
		responseCoachLogin.setResultCode((byte) 1);
		responseCoachLogin.setCoachNum("6593441306021328");
		responseCoachLogin.setHasExtraMsg((byte) 1);
		responseCoachLogin.setExtraMsgLen((byte)"额外数据测试".getBytes().length);
		responseCoachLogin.setExtraMsg("额外数据测试");
		
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId((short)0x8101);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq((short)1);
		extendMessageBody.setDeviceNo("1234560123456789");
		extendMessageBody.setData(responseCoachLogin);
	  
		DownTransportMessageBody downTransportMessageBody = new DownTransportMessageBody();
		downTransportMessageBody.setExtendMessageBody(extendMessageBody);
		downTransportMessageBody.setMessageType((byte) 1);
		
		Header header = new Header();
		header.setId((short)0x8900);
		header.setMobile("13012345343");
		
		Message message = new Message();
		message.setHeader(header);
		message.setBody(downTransportMessageBody.getBytes());
		
		logger.info(String.valueOf(downTransportMessageBody.getBytes().length));
		System.out.println("messageLength------------"+message.getBody().length);
		ctx.writeAndFlush(message);
	}
	
	private void upTransportCoachLogin(ChannelHandlerContext ctx) {
		  LoginCoach coach = new LoginCoach();
		  coach.setCoachNum("6593441306021328");
		  coach.setCoachIdentifierNo("898989198705050020");
		  coach.setTeachCarType("C1");
		  coach.setLocationInfo(Utils.getDefaultLocationInfo());
		  Message defaultUpTransportMessage = getDefaultUpTransportMessage(coach, (short)0x0101, "教练员登录");
		  
		  ctx.writeAndFlush(defaultUpTransportMessage);
	}

	private void sendDefaultMessage(ChannelHandlerContext ctx,IBytes data,short messageId){
		Header header = new Header();
		header.setId(messageId);
		header.setMobile("13012345343");
		header.setNumber((short)1);
		
		Message message = new Message();
		message.setHeader(header);
		if(data!=null){
			message.setBody(data.getBytes());
		}
		
		ctx.writeAndFlush(message);
	}
	
	private Message sendWebDefaultMessage(ChannelHandlerContext ctx,TransportObject data,short transportId,String checkString ){
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq((short)1);
		extendMessageBody.setDeviceNo("1234560123456789");
		extendMessageBody.setData(data);
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
		upTransportMessageBody.setExtendMessageBody(extendMessageBody);
		  
		Header header = new Header();
		header.setId((short)-1);
		header.setMobile("13012345343");
		header.setNumber((short)1);
		
		Message message = new Message();
		message.setHeader(header);
		message.setBody(upTransportMessageBody.getBytes());
		
		ctx.writeAndFlush(message);
		return message;
	}
	
	private Message sendWebUpMessage(ChannelHandlerContext ctx,TransportObject data,short transportId,String checkString ){
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq((short)1);
		extendMessageBody.setDeviceNo("1234560123456789");
		extendMessageBody.setData(data);
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
		upTransportMessageBody.setExtendMessageBody(extendMessageBody);
		  
		Header header = new Header();
		header.setId((short)-1);
		header.setMobile("13012345343");
		header.setNumber((short)1);
		
		Message message = new Message();
		message.setHeader(header);
		message.setBody(upTransportMessageBody.getBytes());
		
		ctx.writeAndFlush(message);
		return message;
	}
	
	private Message sendDefaultUpTransportMessage(ChannelHandlerContext ctx,TransportObject data,short transportId,String checkString ){
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq((short)1);
		extendMessageBody.setDeviceNo("1234560123456789");
		extendMessageBody.setData(data);
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
		upTransportMessageBody.setExtendMessageBody(extendMessageBody);
		
		Header header = new Header();
		header.setId((short)0x0900);
		header.setMobile("15789478932");
		header.setNumber((short)1);
		
		Message message = new Message();
		message.setHeader(header);
		message.setBody(upTransportMessageBody.getBytes());
		
		ctx.writeAndFlush(message);
		return message;
	}
	
	private Message getDefaultUpTransportMessage(TransportObject data,short transportId,String checkString ){
		ExtendMessageBody extendMessageBody = new ExtendMessageBody();
		extendMessageBody.setTransportId(transportId);
		extendMessageBody.setExtendMessageProperty((short)0);
		extendMessageBody.setSeq((short)1);
		extendMessageBody.setDeviceNo("1234560123456789");
		extendMessageBody.setData(data);
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody();
		upTransportMessageBody.setExtendMessageBody(extendMessageBody);
		  
		Header header = new Header();
		header.setId((short)0x0900);
		header.setMobile("13012345343");
		header.setNumber((short)1);
		
		Message message = new Message();
		message.setHeader(header);
		message.setBody(upTransportMessageBody.getBytes());
		System.out.println("upTransportMessageBody.getBytes().length:"+upTransportMessageBody.getBytes().length);
		System.out.println("message.getBytes().length:"+message.getBytes().length);
		
		return message;
	}
	
}
*/