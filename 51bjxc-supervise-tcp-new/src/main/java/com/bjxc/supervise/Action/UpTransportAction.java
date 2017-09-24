package com.bjxc.supervise.Action;

import java.io.File;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.datetime.DateFormatter;

import com.bjxc.Result;
import com.bjxc.json.JacksonBinder;
import com.bjxc.jtt.JttMessage;
import com.bjxc.jtt.JttUtils;
import com.bjxc.model.Coach;
import com.bjxc.model.DeviceImage;
import com.bjxc.model.LocationInfo;
import com.bjxc.model.MessageModel;
import com.bjxc.model.PhotoInfo;
import com.bjxc.model.Student;
import com.bjxc.model.StudentTrainSituation;
import com.bjxc.model.TrainingLog;
import com.bjxc.model.TrainingRecord;
import com.bjxc.model.UploadPhoto;
import com.bjxc.supervise.http.crypto.DecodeUtil;
import com.bjxc.supervise.http.crypto.HttpClientService;
import com.bjxc.supervise.http.crypto.JSPTConstants;
import com.bjxc.supervise.model.Device;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.LoginCoach;
import com.bjxc.supervise.model.LoginInfo;
import com.bjxc.supervise.model.LoginStudent;
import com.bjxc.supervise.model.LogoutCoach;
import com.bjxc.supervise.model.LogoutStudent;
import com.bjxc.supervise.model.OperationLog;
import com.bjxc.supervise.model.ParamBS;
import com.bjxc.supervise.model.PhotoFileUpload;
import com.bjxc.supervise.model.PhotoImmediately;
import com.bjxc.supervise.model.PhotoQuery;
import com.bjxc.supervise.model.PhotoUpQuery;
import com.bjxc.supervise.model.PhotoUploadCertain;
import com.bjxc.supervise.model.PhotoUploadInit;
import com.bjxc.supervise.model.ResponseCoachLogin;
import com.bjxc.supervise.model.ResponseCoachLogout;
import com.bjxc.supervise.model.ResponsePhotoUpQuery;
import com.bjxc.supervise.model.ResponsePhotoUploadInit;
import com.bjxc.supervise.model.ResponseStudentLogin;
import com.bjxc.supervise.model.ResponseStudentLogout;
import com.bjxc.supervise.model.StudyingTime;
import com.bjxc.supervise.model.StudyingTimeOrder;
import com.bjxc.supervise.model.TimeTerminalQueryParam;
import com.bjxc.supervise.model.TimeTerminalSetParam;
import com.bjxc.supervise.model.TrainingCar;
import com.bjxc.supervise.model.TransportMessageBody;
import com.bjxc.supervise.model.UnableTraining;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.model.WebPhoto;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.server.TCPMap;
import com.bjxc.supervise.service.AutoRecordCheckService;
import com.bjxc.supervise.service.CoachService;
import com.bjxc.supervise.service.DeviceImageService;
import com.bjxc.supervise.service.DeviceService;
import com.bjxc.supervise.service.LocationInfoService;
import com.bjxc.supervise.service.OperationLogService;
import com.bjxc.supervise.service.StudentService;
import com.bjxc.supervise.service.TrainingLogService;
import com.bjxc.supervise.service.TrainingRecordService;
import com.mysql.jdbc.Util;

import io.netty.channel.ChannelHandlerContext;

public class UpTransportAction implements Action{
	
	private static final Logger logger = LoggerFactory.getLogger(UpTransportAction.class);
	private static JacksonBinder binder = JacksonBinder.buildNormalBinder();
	@Resource
	private CoachService coachService;
	@Resource
	private StudentService studentService;
	@Resource
	private TrainingRecordService trainingRecordService;
	@Resource
	private LocationInfoService locationInfoService;
	@Resource
	private HttpClientService httpClientService;
	@Resource
	private DeviceImageService deviceImageService;
	@Resource
	private TrainingLogService trainingLogService;
	@Resource
	private OperationLogService operationLogService;
	@Resource
	private DeviceService deviceService;
	@Value("${bjxc.message.server}")
	private String messageServer;

	@Resource
	private AutoRecordCheckService autoRecordCheckService;
	
	public void action(ActionContext context) {
		
		logger.info("0x0900 UpTransportAction");
		Message msg = context.getMessag();
		byte[] body = msg.getBody();
		
		UpTransportMessageBody upTransportMessageBody = new UpTransportMessageBody(body);
		ExtendMessageBody extendMessageBody = upTransportMessageBody.getExtendMessageBody();
		
		LoginInfo loginInfo = 	TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
		//检查校验串
		byte[] check = extendMessageBody.getCheck();
		byte[] extendBody = extendMessageBody.getCheckBytes();
		//获取校验串
		String encodedEncryptedStr = HexUtils.BinaryToHexString(check);
		boolean checkresult = DecodeUtil.checkTerminalAuth(extendBody, encodedEncryptedStr,0, loginInfo.getCadata(), loginInfo.getCapwd());
		logger.info("校验密文. checkresult = " + checkresult);
//		boolean checkresult = true;
		
		short seq = extendMessageBody.getSeq();
		short number = msg.getHeader().getNumber();

		switch(extendMessageBody.getTransportId()){
			case (short)0x0101:	//上报教练员登录
				LoginCoach loginCoach = (LoginCoach)extendMessageBody.getData();
				logger.info("0x0101 上报教练员登录");
				logger.info("0x0101: "+loginCoach.toString());
				
				loginCoach(context, loginCoach,msg,seq,number,checkresult);
				break;
			case (short)0x0102:	//上报教练员登出
				LogoutCoach logoutCoach = (LogoutCoach)extendMessageBody.getData();
				logger.info("0x0102 上报教练员登出");
				logger.info("0x0102: "+logoutCoach.toString());
				
				logoutCoach(context, logoutCoach,msg,seq,number,checkresult);
				break;
			case (short)0x0201:	//上报学员登录
				LoginStudent loginStudent = (LoginStudent)extendMessageBody.getData();
				logger.info("0x0201 上报学员登录");
				logger.info("0x0201 : "+loginStudent.toString());
				
				loginStudent(context, loginStudent,msg,seq,number,checkresult);
				break;
			case (short)0x0202:	//上报学员登出
				LogoutStudent logoutStudent = (LogoutStudent)extendMessageBody.getData();
				logger.info("0x0202 上报学员登出");
				logger.info("0x0202 : "+logoutStudent.toString());
				
				logoutStudent(context, logoutStudent,msg,seq,number,checkresult);
				break;
			case (short)0x0203:	//上报学时
				StudyingTime studyingTime = (StudyingTime) extendMessageBody.getData();
				logger.info("0x0203 上报学时");
				logger.info("0x0203 : "+studyingTime.toString());
				
				studyingTime(context,studyingTime,msg,seq,number,checkresult);
				break;
			case (short)0x0205:	//命令上报学时
				StudyingTimeOrder studyingTimeOrder = (StudyingTimeOrder) extendMessageBody.getData();
				logger.info("0x0205 命令上报学时");
				String[] strings = {"","查询的记录正在上传","SD卡没有找到","执行成功，但无指定记录","执行成功，稍候上报查询结果"};
				logger.info("0x0205 : "+studyingTimeOrder.getResultCode()+strings[studyingTimeOrder.getResultCode()]);
				
				studyingTimeOrder(context,studyingTimeOrder,msg,seq,number,checkresult);
				break;
			case (short)0x0301:	//立即拍照
				logger.info("0x0301 立即拍照");
				PhotoImmediately photoImmediately = (PhotoImmediately) extendMessageBody.getData();
				logger.info("0x0301 : "+photoImmediately.toString());
				
				photoImmediately(context,photoImmediately,msg,seq,number,checkresult);
				break;
			case (short)0x0302:	//查询照片
				logger.info("0x0302 查询照片");
				PhotoQuery photoQuery = (PhotoQuery) extendMessageBody.getData();
				logger.info("0x0302 : "+photoQuery.toString());
				
				photoQuery(context,photoQuery,msg,seq,number,checkresult);
				break;
			case (short)0x0303:	//上报照片查询结果
				logger.info("0x0303 上报照片查询结果");
				PhotoUpQuery photoUpQuery = (PhotoUpQuery) extendMessageBody.getData();
				logger.info("0x0303 : "+photoUpQuery.toString());
				logger.info("0x0303 : "+photoUpQuery.getParamList().toString());
				
				photoUpQuery(context,photoUpQuery,msg,seq,number,checkresult);
				break;
			case (short)0x0304:	//上传指定照片
				logger.info("0x0304 上传指定照片");
				PhotoUploadCertain photoUploadCertain = (PhotoUploadCertain) extendMessageBody.getData();
				String[] strings1 = {"找到照片，稍候上传","没有该照片"};
				logger.info("0x0304 getResultCode: "+photoUploadCertain.getResultCode()+strings1[photoUploadCertain.getResultCode()]);
				
				photoUploadCertain(context,photoUploadCertain,msg,seq,number,checkresult);
				break;
			case (short)0x0305:	//照片上传初始化
				logger.info("0x0305 照片上传初始化");
				PhotoUploadInit photoUploadInit = (PhotoUploadInit) extendMessageBody.getData();
				logger.info("0x0305 : "+photoUploadInit.toString());
				
				photoUploadInit(context,photoUploadInit,msg,seq,number,checkresult);
				break;
			case (short)0x0306:	//上传照片数据包
				logger.info("0x0306 上传照片数据包");
				PhotoFileUpload photoFileUpload = (PhotoFileUpload) extendMessageBody.getData();
				logger.info("0x0306 getPhotoNum: "+photoFileUpload.getPhotoNum());
				logger.info("0x0306 getAbsolutePath: "+photoFileUpload.getFile().getAbsolutePath());
				
				photoFileUpload(context,photoFileUpload,msg,seq,number,checkresult);
				break;
			case (short)0x0501:	//设置计时终端应用参数
				logger.info("0x0501 设置计时终端应用参数");
				TimeTerminalSetParam timeTerminalSetParam = (TimeTerminalSetParam) extendMessageBody.getData();
				logger.info("0x0501 getResultCode: "+timeTerminalSetParam.getResultCode());
				
				timeTerminalSetParam(context,timeTerminalSetParam,msg,seq,number,checkresult);
				break;
			case (short)0x0502:	//设置禁训状态
				logger.info("0x0502 设置禁训状态");
				UnableTraining unableTraining = (UnableTraining) extendMessageBody.getData();
				logger.info("0x0502 : "+unableTraining.toString());
				
				unableTraining(context,unableTraining,msg,seq,number,checkresult);
				break;
			case (short)0x0503:	//查询计时终端应用参数
				logger.info("0x0503 查询计时终端应用参数");
				TimeTerminalQueryParam timeTerminalQueryParam = (TimeTerminalQueryParam) extendMessageBody.getData();
				logger.info("0x0503 : "+timeTerminalQueryParam.toString());
				
//				ChannelHandlerContext webChannelHandlerContext = TCPMap.getInstance().getWebClientMap().get(msg.getHeader().getMobile());
//				webChannelHandlerContext.writeAndFlush(msg);
				
				timeTerminalQueryParam(context,timeTerminalQueryParam,msg,seq,number,checkresult);
				break;
		}
	}

	/**
	 * 查询计时终端应用参数
	 * @param context
	 * @param timeTerminalQueryParam
	 * @param msg 
	 */
	private void timeTerminalQueryParam(ActionContext context, TimeTerminalQueryParam timeTerminalQueryParam, Message msg, short seq, short number, boolean checkresult) {
		
		try {
			httpClientService.doSimpleGet(messageServer + "/message?inscode=" + "9694949161640983" + "&content=" + URLEncoder.encode(binder.toJson(msg), "UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e+"");
		}
		
	}

	/**
	 * 设置禁训状态
	 * @param context
	 * @param unableTraining
	 * @param upTransportMessageBody 
	 */
	private void unableTraining(ActionContext context, UnableTraining unableTraining, Message msg, short seq, short number, boolean checkresult) {
		
	}

	/**
	 * 设置计时终端应用参数
	 * @param context
	 * @param timeTerminalSetParam
	 * @param upTransportMessageBody 
	 */
	private void timeTerminalSetParam(ActionContext context, TimeTerminalSetParam timeTerminalSetParam, Message msg, short seq, short number, boolean checkresult) {
		
	}

	/**
	 * 上传照片数据包
	 * @param context
	 * @param photoFileUpload
	 * @param upTransportMessageBody 
	 */
	private void photoFileUpload(ActionContext context, PhotoFileUpload photoFileUpload, Message msg, short seq, short number, boolean checkresult) {
		try {
			//上传图片到服务器保存
			File targetFile = photoFileUpload.getFile(); //-- 指定上传文件
			String targetURL = "http://upload.91ygxc.com/FileUpload"; // 指定URL
			String photoUrl = null;
			try {
				Result upload = httpClientService.upload(targetURL, targetFile);
				if(upload.getCode()==200){
					String strs[]=upload.getData().toString().split(",");
					String params[]=strs[1].split("=");
					photoUrl = params[1].replaceAll("}", "");
					targetFile.delete();
					logger.info("photoUrl: "+photoUrl);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e+"");
			}
			LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
			
			//把照片链接发到WEB
			WebPhoto webPhoto = new WebPhoto();
			webPhoto.setLic(loginInfo.getTrainingCar().getLicnum());
			webPhoto.setPhotoUrl(photoUrl);
			MessageModel messageModel = new MessageModel();
			messageModel.setBody(webPhoto);
			messageModel.setMessageIdHexString("-3");
			try {
				String insCode = loginInfo.getIns().getInscode();
				httpClientService.doSimpleGet(messageServer +"/message?inscode="+insCode+"&content="+URLEncoder.encode(binder.toJson(messageModel), "UTF-8"));
				logger.info("透传终端消息到WEB客户端-立即拍照照片链接");
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e+"");
			}
			
			//更新数据库图片信息
			deviceImageService.updateUrl(Integer.parseInt(photoFileUpload.getPhotoNum()),loginInfo.getDevice().getDevnum(), photoUrl);
			List<DeviceImage> list = deviceImageService.findByNum(photoFileUpload.getPhotoNum(),loginInfo.getDevice().getDevnum());
			
			//图片信息转发809协议
			ChannelHandlerContext jttChannel = TCPMap.getInstance().getChannelMap().get(JttUtils.JTT_CHANNEL);
			if(jttChannel != null && list != null && list.size() != 0){
				com.bjxc.model.TrainingCar jttTrainingCar = loginInfo.getTrainingCar();
				Integer locationId = list.get(0).getLocationId();
				if(locationId != null ){
					LocationInfo locationInfoById = locationInfoService.getLocationInfoById(locationId);
					if(locationInfoById != null){
						JttMessage jttMessage = JttUtils.photoTransform(list.get(0), locationInfoById, jttTrainingCar, photoFileUpload.getFileBytes());
						jttChannel.writeAndFlush(jttMessage);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e+"");
		}
		
	}

	/**
	 * 照片上传初始化
	 * @param context
	 * @param photoUploadInit
	 * @param upTransportMessageBody 
	 */
	private void photoUploadInit(ActionContext context, PhotoUploadInit photoUploadInit, Message msg, short seq, short number, boolean checkresult) {
		ResponsePhotoUploadInit data=new ResponsePhotoUploadInit();
		if(!checkresult){
			data.setResultCode((byte)9);//校验串检查失败
		}else{
			try {
				LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
				//照片编号重复检查
				List<DeviceImage> list = deviceImageService.findByNum(photoUploadInit.getPhotoNum(),loginInfo.getDevice().getDevnum());
				if(list == null || list.size() == 0){
					data.setResultCode((byte)0);//0允许，255拒绝

					LocationInfo insertLocationInfo = Utils.insertLocationInfo(photoUploadInit.getLocationInfo(),msg.getHeader().getMobile());
					
					//保存照片信息到数据库
					DeviceImage deviceImage = new DeviceImage();
					deviceImage.setDeviceNum(loginInfo.getDevice().getDevnum());
					deviceImage.setObjectNum(photoUploadInit.getStuNum());
					deviceImage.setImageNum(photoUploadInit.getPhotoNum());
					deviceImage.setUploadMode((int) photoUploadInit.getUpMode());
					deviceImage.setChannelNo((int) photoUploadInit.getCameraNum());
					deviceImage.setImageSize(photoUploadInit.getPhotoSize());
					deviceImage.setEvent((int) photoUploadInit.getPhotoType());
					deviceImage.setCourseId(photoUploadInit.getClassId());
					deviceImage.setLocationId(insertLocationInfo.getId());
					deviceImage.setFaceProbability((int) photoUploadInit.getFace());
					deviceImage.setPackageNum((int) photoUploadInit.getCount());
					deviceImage.setDataSize(photoUploadInit.getCountSize());
					SimpleDateFormat dateFormatter = new SimpleDateFormat("yyMMddHHmmss");
					deviceImage.setRecordTime(dateFormatter.parse(photoUploadInit.getLocationInfo().getTime()));
					deviceImage.setPhotoId(loginInfo.getDevice().getDevnum()+photoUploadInit.getPhotoNum());
					
					if(photoUploadInit.getPhotoType() != 20 && photoUploadInit.getPhotoType() != 21){
						deviceImage.setTrainingRecordId(loginInfo.getTrainingRecordId());
					}
					
					if(!photoUploadInit.getStuNum().equals("0000000000000000")){
						//照片与学员最新一个学时记录关联起来
						if(photoUploadInit.getPhotoType() != 17 && photoUploadInit.getPhotoType() != 18
								&& photoUploadInit.getPhotoType() != 20 && photoUploadInit.getPhotoType() != 21){	//学员过程中的照片
							TrainingLog studentLastLog = trainingLogService.getStudentLastLog(photoUploadInit.getStuNum());
							deviceImage.setTrainingLogId(studentLastLog.getId());
						}
						DeviceImage add = deviceImageService.add(deviceImage);
						
						//如果是学员登录登出的照片，写入电子教学日志里面
						if(photoUploadInit.getPhotoType() == (byte)17){	//学员登录拍照
							logger.info("学员登录拍照");
							trainingRecordService.updateLoginPhoto(loginInfo.getTrainingRecordId(),add.getId());
						}else if(photoUploadInit.getPhotoType() == (byte)18){	//学员登出拍照
							logger.info("学员登出拍照");
							trainingRecordService.updateLogoutPhoto(loginInfo.getTrainingRecordId(),add.getId());
						}
					}
					
				}else{
//					data.setResultCode((byte)1);	//照片编号重复或错误
					data.setResultCode((byte)0);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.info(e+"");
				data.setResultCode((byte)255);//0允许，255拒绝
			}finally {
				logger.info("照片初始化:"+data.getResultCode());
				Message downTransportMessage = Utils.getDownTransportMessage(data,(short)0x8305,JSPTConstants.JSPT_PLATFORMNO_BCD,new TransportMessageBody(msg.getBody()).getExtendMessageBody().getDeviceNo(),seq,number);
				context.writeAndFlush(downTransportMessage);
			}
		}
	}

	/**
	 * 上传指定照片
	 * @param context
	 * @param photoUploadCertain
	 * @param upTransportMessageBody 
	 */
	private void photoUploadCertain(ActionContext context, PhotoUploadCertain photoUploadCertain, Message msg, short seq, short number, boolean checkresult) {
		
	}

	/**
	 * 上报照片查询结果
	 * @param context
	 * @param photoUpQuery
	 * @param msg
	 */
	private void photoUpQuery(ActionContext context, PhotoUpQuery photoUpQuery, Message msg, short seq, short number, boolean checkresult) {
		
		ResponsePhotoUpQuery responsePhotoUpQuery = new ResponsePhotoUpQuery();
		responsePhotoUpQuery.setResultCode((byte)0);
		try {
			if(!checkresult){
				responsePhotoUpQuery.setResultCode((byte)9);//校验串检查失败
			}else{
				//插入图片deviceId及photoNum
				LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
				
				if(photoUpQuery!=null && photoUpQuery.getParamList()!=null){
					for (String imageNum : photoUpQuery.getParamList()) {
						DeviceImage deviceImage = new DeviceImage();
						deviceImage.setDeviceNum(loginInfo.getDevice().getDevnum());
						deviceImage.setImageNum(imageNum);
						deviceImageService.init(deviceImage);
					}
				}
			}
		}catch(Exception e){
			logger.info(e+"");
		}finally {
			Message downTransportMessage = Utils.getDownTransportMessage(responsePhotoUpQuery, (short)0x8303, JSPTConstants.JSPT_PLATFORMNO_BCD,new TransportMessageBody(msg.getBody()).getExtendMessageBody().getDeviceNo(),seq,number);
			context.writeAndFlush(downTransportMessage);
		}
	}

	/**
	 * 查询照片
	 * @param context
	 * @param photoQuery
	 * @param upTransportMessageBody 
	 */
	private void photoQuery(ActionContext context, PhotoQuery photoQuery, Message msg, short seq, short number, boolean checkresult) {
		
	}

	/**
	 * 立即拍照
	 * @param context
	 * @param photoImmediately
	 * @param upTransportMessageBody 
	 */
	private void photoImmediately(ActionContext context, PhotoImmediately photoImmediately, Message msg, short seq, short number, boolean checkresult) {
		
	}

	/**
	 * 命令上报学时
	 * @param context
	 * @param studyingTimeOrder
	 * @param upTransportMessageBody 
	 */
	private void studyingTimeOrder(ActionContext context, StudyingTimeOrder studyingTimeOrder, Message msg, short seq, short number, boolean checkresult) {
		//更新最后要求上报时间
		LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
		com.bjxc.supervise.model.Student student = loginInfo.getStudent();
		studentService.updateLastestUploadTime(student.getStudentNum(),new Date());
	}

	/**
	 * 上报学时记录
	 * @param context
	 * @param studyingTime
	 * @param upTransportMessageBody
	 */
	private void studyingTime(ActionContext context, StudyingTime studyingTime, Message msg, short seq, short number, boolean checkresult) {
		Message createDefaultMessage = MessageUtils.successMessage((short)0x0900, msg.getHeader().getNumber());
		TrainingLog trainingLog = null;
		try {
			//写入位置记录
			LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
			LocationInfo locationInfoId = Utils.insertLocationInfo(studyingTime.getLocationInfo(),msg.getHeader().getMobile());
			
			//添加一条学时记录数据
			trainingLog = new TrainingLog();
			trainingLog.setRecordnum(studyingTime.getStudyingTimeNum());
			trainingLog.setUploadtype((int) studyingTime.getUploadType());
			trainingLog.setStudentnum(studyingTime.getStudentNum());
			trainingLog.setCoachnum(studyingTime.getCoachNum());
			trainingLog.setCourseid(studyingTime.getCourseId());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
			Date recordTime = new Date();
			String format = simpleDateFormat.format(recordTime);
			simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			Date date= simpleDateFormat.parse(format+studyingTime.getRecordTime());	//由于发过来的只有HHmmss，用当前的yyyyMMdd拼上组成Date对象
			trainingLog.setRecordtime(date);
			logger.info("getRecordTime: "+studyingTime.getRecordTime());
			logger.info("format: "+format+studyingTime.getRecordTime());
			logger.info("RecordTime: "+date.toGMTString());
			trainingLog.setCourse(studyingTime.getCourse());
			trainingLog.setStatus((int) studyingTime.getRecordStatus());
			trainingLog.setMaxspeed((int) studyingTime.getMaxSpeed());
			trainingLog.setMileage((int) studyingTime.getMileage());
			trainingLog.setLocationid(locationInfoId.getId());
			trainingLog.setTrainingrecordid(loginInfo.getTrainingRecordId());
			trainingLogService.add(trainingLog);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e+"");
			createDefaultMessage = MessageUtils.errorMessage((short)0x0900, msg.getHeader().getNumber());
		}finally {
			context.writeAndFlush(createDefaultMessage);
		}
		if(trainingLog != null){
			//自动检查学时
			autoRecordCheckService.checkMinRecord(trainingLog);
		}
	}

	/**
	 * 学员登出
	 * @param context
	 * @param logoutStudent
	 * @param upTransportMessageBody 
	 */
	private void logoutStudent(ActionContext context, LogoutStudent logoutStudent, Message msg, short seq, short number, boolean checkresult) {
		logger.info("Mobile: "+msg.getHeader().getMobile());
		ResponseStudentLogout responseStudentLogout = new ResponseStudentLogout();
		responseStudentLogout.setStudentNum(logoutStudent.getStudentNum());
		responseStudentLogout.setResultCode((byte)2);
		try {
			if(!checkresult){
				responseStudentLogout.setResultCode((byte)9);
			}else{
				Student studentByNum = studentService.getStudentByNum(logoutStudent.getStudentNum());
				if(studentByNum==null){	//登出失败
					responseStudentLogout.setResultCode((byte)2);
				}else{	//登出成功
					responseStudentLogout.setResultCode((byte)1);
					studentService.updateTCPLogout(studentByNum.getStunum(), new Date());
					
					//添加日志记录
					OperationLog operationLog = new OperationLog();
					operationLog.setInsId(2);
					operationLog.setLogEvent("TCP学员登出："+studentByNum.getStunum());
					operationLog.setLogTime(new Date());
					operationLog.setLogUser(studentByNum.getName());
					operationLog.setRemark("");
					operationLogService.add(operationLog);
					
					//记录本次登出时间
					LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
					com.bjxc.supervise.model.Student student = loginInfo.getStudent();
					student.setEndTime(new Date());
					loginInfo.setStudent(student);
					TCPMap.getInstance().getLoginInfoMap().put(msg.getHeader().getMobile(), loginInfo);
					
					//更新电子教学日志结束时间、计算本次培训的总里程
					Integer mileage = (int)logoutStudent.getThisLoginAliveMileage();	//发过来的数据多了个0
					Integer speed = 0;
					if(logoutStudent.getThisLoginAliveTime() != 0){
						speed = (int)(mileage/logoutStudent.getThisLoginAliveTime())*60;	//发过来的数据是min,speed的单位是km/h
					}
					trainingRecordService.updateEndTime(loginInfo.getTrainingRecordId(),new Date(),mileage,speed);
					
					//教学日志自动检查
					TrainingRecord trainingRecord = trainingRecordService.getTrainingRecordById(loginInfo.getTrainingRecordId());
					autoRecordCheckService.checkTrainingRecord(trainingRecord);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e+"");
			responseStudentLogout.setResultCode((byte)2);
		}finally {
			logger.info("学员登出: "+responseStudentLogout.getResultCode());
			Message downTransportMessage = Utils.getDownTransportMessage(responseStudentLogout,(short)0x8202,JSPTConstants.JSPT_PLATFORMNO_BCD,new TransportMessageBody(msg.getBody()).getExtendMessageBody().getDeviceNo(),seq,number);
			context.writeAndFlush(downTransportMessage);
		}
	}

	/**
	 * 学员登录
	 * @param context
	 * @param loginStudent
	 * @param upTransportMessageBody 
	 */
	private void loginStudent(ActionContext context, LoginStudent loginStudent, Message msg, short seq, short number, boolean checkresult) {
		logger.info("Mobile: "+msg.getHeader().getMobile());
		logger.info("loginStudent: "+loginStudent.toString());
 		ResponseStudentLogin responseStudentLogin = new ResponseStudentLogin();
		responseStudentLogin.setStudentNum(loginStudent.getStudentNum());
		responseStudentLogin.setWholeClassHour((short)0);
		responseStudentLogin.setCurrentClassHour((short)0);
		responseStudentLogin.setWholeMileage((short)0);
		responseStudentLogin.setCurrentMileage((short)0);
		responseStudentLogin.setHasExtalMsg((byte)0);
		responseStudentLogin.setResultCode((byte)9);
		try {
			if(!checkresult){
				responseStudentLogin.setResultCode((byte)9);
			}else{
				Student studentByNum = studentService.getStudentByNum(loginStudent.getStudentNum());
				logger.info(loginStudent.getCoachNum());
				Coach coachByNum = coachService.getCoachByNum(loginStudent.getCoachNum());
				
				if(studentByNum==null){	//无效的学员编号
					logger.info("无效的学员编号");
					responseStudentLogin.setResultCode((byte)2);
				}else if(coachByNum==null){	//无效的教练编号
					logger.info("无效的教练编号");
					responseStudentLogin.setHasExtalMsg((byte)1);
					responseStudentLogin.setExtalMsg("无效的教练编号");
				}else if(studentByNum.getSubjectId()==null){	//学员无科目信息
					logger.info("学员无科目信息");
					responseStudentLogin.setHasExtalMsg((byte)1);
					responseStudentLogin.setExtalMsg("学员无科目信息");
				}else if(!Utils.checkCarTrain(coachByNum.getTeachpermitted().toUpperCase(),studentByNum.getTraintype().toUpperCase())){	//准教车型与培训车型不符
					logger.info("准教车型与培训车型不符");
					responseStudentLogin.setResultCode((byte)5);
				}else{	//TODO:登录成功
					responseStudentLogin.setResultCode((byte)1);
					studentService.updateTCPLogin(studentByNum.getStunum(), new Date());
					StudentTrainSituation studentTrainSituation = studentService.getStudentTrainSituation(studentByNum.getStunum());
					
					if(studentTrainSituation==null){
						responseStudentLogin.setWholeClassHour((short)0);
						responseStudentLogin.setCurrentClassHour((short)0);
						responseStudentLogin.setWholeMileage((short)0);
						responseStudentLogin.setCurrentMileage((short)0);
						responseStudentLogin.setHasExtalMsg((byte)0);
					}else{
						responseStudentLogin.setWholeClassHour((short) studentTrainSituation.getTotalhour());
						responseStudentLogin.setCurrentClassHour((short) studentTrainSituation.getCurrenthour());
						responseStudentLogin.setWholeMileage((short) studentTrainSituation.getTotalmileage());
						responseStudentLogin.setCurrentMileage((short) studentTrainSituation.getCurrentmileage());
						responseStudentLogin.setHasExtalMsg((byte)0);
					}
					LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
					
					//添加日志记录
					OperationLog operationLog = new OperationLog();
					operationLog.setInsId(2);
					operationLog.setLogEvent("TCP学员登录："+studentByNum.getStunum());
					operationLog.setLogTime(new Date());
					operationLog.setLogUser(studentByNum.getName());
					operationLog.setRemark("");
					operationLogService.add(operationLog);
					
					//生成本次电子教学日志记录
					TrainingRecord trainingRecord = new TrainingRecord();
					trainingRecord.setStudentId(studentByNum.getId());
					trainingRecord.setCoachId(coachByNum.getId());
					trainingRecord.setTrainingCarId(loginInfo.getTrainingCar().getId());
					trainingRecord.setDeviceId(loginInfo.getDevice().getId());
					trainingRecord.setStudentNum(studentByNum.getStunum());
					trainingRecord.setCoachNum(coachByNum.getCoachnum());
					trainingRecord.setTrainingcarNum(loginInfo.getTrainingCar().getCarnum());
					trainingRecord.setDeviceNum(loginInfo.getDevice().getDevnum());
					trainingRecord.setStartTime(new Date());
					trainingRecord.setCourseCode(loginStudent.getCourse());
					int courseType = Integer.parseInt(loginStudent.getCourse().substring(0, 1));
					trainingRecord.setCourseType(courseType);
					trainingRecord.setSubjectId(Integer.parseInt(loginStudent.getCourse().substring(3, 4)));
					Integer lastTrainingLogCode = trainingRecordService.getLastTrainingLogCode(studentByNum.getStunum());
					String trainingLogCode = new DecimalFormat("00000").format(lastTrainingLogCode+1);
					trainingRecord.setEtrainingNum(trainingLogCode);
					TrainingRecord add = trainingRecordService.add(trainingRecord);
					
					//记录本次登录学员数据
					com.bjxc.supervise.model.Student student = new com.bjxc.supervise.model.Student();
					student.setStudentId(studentByNum.getId());
					student.setStudentNum(studentByNum.getStunum());
					student.setSubjectId(studentByNum.getSubjectId());
					student.setStartTime(new Date());
					loginInfo.setStudent(student);
					logger.info("setTrainingRecordId:"+add.getId());
					loginInfo.setTrainingRecordId(add.getId());
					TCPMap.getInstance().getLoginInfoMap().put(msg.getHeader().getMobile(), loginInfo);
					
					//从业资格证和驾驶证转发809协议
					ChannelHandlerContext jttChannel = TCPMap.getInstance().getChannelMap().get(JttUtils.JTT_CHANNEL);
					if(jttChannel != null){
						JttMessage jttMessage = JttUtils.coachTransform(studentByNum.getCardnum(),studentByNum.getName(), loginInfo.getTrainingCar());
						jttChannel.writeAndFlush(jttMessage);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e+"");
			responseStudentLogin.setResultCode((byte)9);
		}finally {
			logger.info("学员登录: "+responseStudentLogin.getResultCode());
			Message downTransportMessage = Utils.getDownTransportMessage(responseStudentLogin, (short)0x8201,JSPTConstants.JSPT_PLATFORMNO_BCD,new TransportMessageBody(msg.getBody()).getExtendMessageBody().getDeviceNo(),seq,number);
			context.writeAndFlush(downTransportMessage);
		}
	}

	/**
	 * 教练登出
	 * @param context
	 * @param logoutCoach
	 * @param upTransportMessageBody 
	 */
	private void logoutCoach(ActionContext context, LogoutCoach logoutCoach, Message msg, short seq, short number, boolean checkresult) {
		ResponseCoachLogout responseCoachLogout = new ResponseCoachLogout();
		responseCoachLogout.setCoachNum(logoutCoach.getCoachNum());
		try {
			if(!checkresult){
				responseCoachLogout.setResultCode((byte)9);
			}else{
				Coach coachByNum = coachService.getCoachByNum(logoutCoach.getCoachNum());
				if(coachByNum==null){	//登出失败
					responseCoachLogout.setResultCode((byte)2);
				}else{	//登出成功
					responseCoachLogout.setResultCode((byte)1);
					coachService.updateTCPLogin(coachByNum.getCoachnum(), new Date());
					Device device = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile()).getDevice();
					deviceService.updateTCPLogout(device.getId());
					
					//添加日志记录
					OperationLog operationLog = new OperationLog();
					operationLog.setInsId(2);
					operationLog.setLogEvent("TCP教练登出："+coachByNum.getCoachnum());
					operationLog.setLogTime(new Date());
					operationLog.setLogUser(coachByNum.getName());
					operationLog.setRemark("");
					operationLogService.add(operationLog);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e+"");
			responseCoachLogout.setResultCode((byte)1);
		}finally {
			logger.info("教练登出: "+responseCoachLogout.getResultCode());
			Message downTransportMessage = Utils.getDownTransportMessage(responseCoachLogout, (short)0x8102,JSPTConstants.JSPT_PLATFORMNO_BCD,new TransportMessageBody(msg.getBody()).getExtendMessageBody().getDeviceNo(),seq,number);
			context.writeAndFlush(downTransportMessage);
		}
	}

	/**
	 * 教练登录
	 * @param context
	 * @param loginCoach
	 * @param upTransportMessageBody 
	 */
	private void loginCoach(ActionContext context, LoginCoach loginCoach, Message msg, short seq, short number, boolean checkresult) {
		ResponseCoachLogin responseCoachLogin = new ResponseCoachLogin();
		responseCoachLogin.setCoachNum(loginCoach.getCoachNum());
		responseCoachLogin.setHasExtraMsg((byte)2);
		try {
			if(!checkresult){
				responseCoachLogin.setResultCode((byte)9);
			}else{
				Coach coachByNum = coachService.getCoachByNum(loginCoach.getCoachNum());
				if(coachByNum==null){	//教练编号不存在
					responseCoachLogin.setResultCode((byte)2);
				}else if(!Utils.checkCarTrain(coachByNum.getTeachpermitted().toUpperCase(),loginCoach.getTeachCarType().toUpperCase())){	//车型不正确
					 logger.info("车型不正确");
					responseCoachLogin.setResultCode((byte)3);
				}else if(!coachByNum.getIdcard().equals(loginCoach.getCoachIdentifierNo())){	//身份证不正确
					logger.info("身份证号不正确");
					responseCoachLogin.setResultCode((byte)9);
					responseCoachLogin.setHasExtraMsg((byte)1);
					responseCoachLogin.setExtraMsg("身份证号不正确");
				}
				else{	//登录成功
					responseCoachLogin.setResultCode((byte)1);
					coachService.updateTCPLogin(coachByNum.getCoachnum(), new Date());
					Device device = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile()).getDevice();
					deviceService.updateTCPLogin(device.getId());
					
					//添加日志记录
					OperationLog operationLog = new OperationLog();
					operationLog.setInsId(2);
					operationLog.setLogEvent("TCP教练登录："+coachByNum.getCoachnum());
					operationLog.setLogTime(new Date());
					operationLog.setLogUser(coachByNum.getName());
					operationLog.setRemark("");
					operationLogService.add(operationLog);
					
					//记录本次登录教练数据
					LoginInfo loginInfo = TCPMap.getInstance().getLoginInfoMap().get(msg.getHeader().getMobile());
					com.bjxc.supervise.model.Coach coach = new com.bjxc.supervise.model.Coach();
					coach.setCoachId(coachByNum.getId());
					coach.setCoachNum(coachByNum.getCoachnum());
					loginInfo.setCoach(coach);
					TCPMap.getInstance().getLoginInfoMap().put(msg.getHeader().getMobile(), loginInfo);
					
					//从业资格证和驾驶证转发809协议
					ChannelHandlerContext jttChannel = TCPMap.getInstance().getChannelMap().get(JttUtils.JTT_CHANNEL);
					if(jttChannel != null){
						JttMessage jttMessage = JttUtils.coachTransform(coachByNum.getIdcard(),coachByNum.getName(), loginInfo.getTrainingCar());
						jttChannel.writeAndFlush(jttMessage);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e+"");
			responseCoachLogin.setResultCode((byte)9);
		}finally {
			logger.info("教练登录结果: "+responseCoachLogin.getResultCode());
			Message downTransportMessage = Utils.getDownTransportMessage(responseCoachLogin, (short)0x8101,JSPTConstants.JSPT_PLATFORMNO_BCD,new TransportMessageBody(msg.getBody()).getExtendMessageBody().getDeviceNo(),seq,number);
			context.writeAndFlush(downTransportMessage);
		}
	}
}
