package com.bjxc.supervise.Action;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;

import com.bjxc.model.DeviceImage;
import com.bjxc.model.TrainingLog;
import com.bjxc.model.TrainingRecord;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.LocationInfo;
import com.bjxc.supervise.model.LoginStudent;
import com.bjxc.supervise.model.ParamBS;
import com.bjxc.supervise.model.PhotoFileUpload;
import com.bjxc.supervise.model.StudyingTime;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.Header;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.server.SpringApplicationContext;
import com.bjxc.supervise.service.AutoRecordCheckService;
import com.bjxc.supervise.service.DevAssignService;
import com.bjxc.supervise.service.DeviceImageService;
import com.bjxc.supervise.service.DeviceService;
import com.bjxc.supervise.service.LocationInfoService;
import com.bjxc.supervise.service.TrainingLogService;
import com.bjxc.supervise.service.TrainingRecordService;

public class FakeMethod {
	
	/**
	 * 生成学时记录
	 * @return
	 */
	public static StudyingTime getStudyTimeMessage(String deviceNum,String mobile,int index,String studentNum,String coachNum,Date recordTime,int subjectId) {
		StudyingTime studyingTime = new StudyingTime();
		/*	系统中的学时记录编码应采用26位字母数字混合编码，由“16位计时设备编码+6位日期码+4位序列号”组成，并符合以下规则：
			a)	计时设备编码采用全国驾培平台生成的统一编号，未申请时暂用16个“0”；
			b)	日期码定义：6位数字，格式为YYMMDD；
			c)	序列号定义：4位数字，每日0时从“0001”开始，按顺序递增。
		 */
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
		String formatDate = simpleDateFormat.format(recordTime);
		String formatIndex = new DecimalFormat("0000").format(index);
		studyingTime.setStudyingTimeNum(deviceNum+formatDate+formatIndex);
		studyingTime.setUploadType((byte)0x01);
		studyingTime.setCoachNum(coachNum);
		studyingTime.setStudentNum(studentNum);
		studyingTime.setCourseId(100);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HHmmss");
		String formatRecordTime = simpleDateFormat2.format(recordTime);
		studyingTime.setRecordTime(formatRecordTime);
		/*	系统中的课程编码应采用10位数字码，由“1位课程方式码+2位培训车型码+1位培训部分码+2位培训项目码+4位数字码”组成，并符合以下规则：
			a)	课程方式码定义：1-实操，2-课堂教学，3-模拟器教学，4-远程教学；
			b)	培训车型码定义：00-无，01- A1，02-A2，03-A3，11-B1，12-B2，21-C1，22-C2，23-C3，24-C4，25-C5，31-D，32-E，33-F，41-M，42-N，43-P；
			c)	培训部分码定义：1-第一部分，2-第二部分，3-第三部分，4-第四部分；
			d)	培训项目码定义：01-法律、法规及道路交通信号，02-机动车基本知识，03-第一部分综合复习及考核，11-基础驾驶，12-场地驾驶，13-第二部分综合驾驶及考核，21-跟车行驶，22-变更车道，23-靠边停车，24-掉头，25-通过路口，26-通过人行横道，27-通过学校区域，28-通过公共汽车站，29-会车，30-超车，31-夜间驾驶，32-恶劣条件下的驾驶，33-山区道路驾驶，34-高速公路驾驶，35-行驶路线选择，36-第三部分综合驾驶及考核，41-安全、文明驾驶知识，42-危险源辨识知识，43-夜间和高速公路安全驾驶知识，44-恶劣气象和复杂道路条件下的安全驾驶知识，45-紧急情况应急处置知识，46-危险化学品知识，47-典型事故案例分析，48-第四部分综合复习及考核；
			e)	4位数字码预留，不使用时置“0”。
		 */
		studyingTime.setCourse("121"+subjectId+"110000");
		studyingTime.setRecordStatus((byte)0);
		studyingTime.setMaxSpeed((short)35);
		studyingTime.setMileage((short)2);
		
		LocationInfo defaultLocationInfo = Utils.getDefaultLocationInfo();
		ArrayList<ParamBS> arrayList = new ArrayList<ParamBS>();
		arrayList.add(new ParamBS((byte)1,(byte)4,Utils.getInt2byte(12)));
		arrayList.add(new ParamBS((byte)5,(byte)2,Utils.getShort2byte((short)80)));
		defaultLocationInfo.setParamList(arrayList);

		studyingTime.setLocationInfo(defaultLocationInfo);
		return studyingTime;
	}
	
	public static Message getStudentLogin(String mobile,String studentNum,String coachNum,int subjectId,String deviceNum) {
		LoginStudent loginStudent = new LoginStudent();
		loginStudent.setStudentNum(studentNum);
		loginStudent.setCoachNum(coachNum);
		/*	系统中的课程编码应采用10位数字码，由“1位课程方式码+2位培训车型码+1位培训部分码+2位培训项目码+4位数字码”组成，并符合以下规则：
			a)	课程方式码定义：1-实操，2-课堂教学，3-模拟器教学，4-远程教学；
			b)	培训车型码定义：00-无，01- A1，02-A2，03-A3，11-B1，12-B2，21-C1，22-C2，23-C3，24-C4，25-C5，31-D，32-E，33-F，41-M，42-N，43-P；
			c)	培训部分码定义：1-第一部分，2-第二部分，3-第三部分，4-第四部分；
			d)	培训项目码定义：01-法律、法规及道路交通信号，02-机动车基本知识，03-第一部分综合复习及考核，11-基础驾驶，12-场地驾驶，13-第二部分综合驾驶及考核，21-跟车行驶，22-变更车道，23-靠边停车，24-掉头，25-通过路口，26-通过人行横道，27-通过学校区域，28-通过公共汽车站，29-会车，30-超车，31-夜间驾驶，32-恶劣条件下的驾驶，33-山区道路驾驶，34-高速公路驾驶，35-行驶路线选择，36-第三部分综合驾驶及考核，41-安全、文明驾驶知识，42-危险源辨识知识，43-夜间和高速公路安全驾驶知识，44-恶劣气象和复杂道路条件下的安全驾驶知识，45-紧急情况应急处置知识，46-危险化学品知识，47-典型事故案例分析，48-第四部分综合复习及考核；
			e)	4位数字码预留，不使用时置“0”。
		 */
		loginStudent.setCourse("121"+subjectId+"110000");
		loginStudent.setCourseId(100);
		loginStudent.setLocationInfo(Utils.getDefaultLocationInfo());
		Message commonMessage = Utils.getUpTransportMessage(loginStudent,(short)0x0201, mobile,deviceNum,MessageUtils.getCurrentMessageSeq(),MessageUtils.getCurrentMessageNumber());
		return commonMessage;
	}
	
	public static Message getStudentLogout(String mobile,String studentNum,String coachNum,int subjectId,String deviceNum) {
		LoginStudent loginStudent = new LoginStudent();
		loginStudent.setStudentNum(studentNum);
		loginStudent.setCoachNum(coachNum);
		loginStudent.setCourse("121"+subjectId+"110000");
		loginStudent.setCourseId(100);
		loginStudent.setLocationInfo(Utils.getDefaultLocationInfo());
		Message commonMessage = Utils.getUpTransportMessage(loginStudent, (short)0x0202, mobile, deviceNum,MessageUtils.getCurrentMessageSeq(),MessageUtils.getCurrentMessageNumber());
		return commonMessage;
	}
	
	public static void saveStudyTime(StudyingTime studyingTime,int trainingRecordId) {
		TrainingLog trainingLog = new TrainingLog();
		trainingLog.setRecordnum(studyingTime.getStudyingTimeNum());
		trainingLog.setUploadtype((int) studyingTime.getUploadType());
		trainingLog.setStudentnum(studyingTime.getStudentNum());
		trainingLog.setCoachnum(studyingTime.getCoachNum());
		trainingLog.setCourseid(studyingTime.getCourseId());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date recordTime = new Date();
		String format = simpleDateFormat.format(recordTime);
		simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			recordTime = simpleDateFormat.parse(format+studyingTime.getRecordTime());	//由于发过来的只有HHmmss，用当前的yyyyMMdd拼上组成Date对象
		} catch (ParseException e) {
			e.printStackTrace();
		}
		trainingLog.setRecordtime(recordTime);
		trainingLog.setCourse(studyingTime.getCourse());
		trainingLog.setStatus((int) studyingTime.getRecordStatus());
		trainingLog.setMaxspeed((int) studyingTime.getMaxSpeed());
		trainingLog.setMileage((int) studyingTime.getMileage());
		com.bjxc.model.LocationInfo locationInfo = insertLocationInfo(studyingTime.getLocationInfo(),trainingRecordId);
		trainingLog.setLocationinfoid(locationInfo.getId());
		trainingLog.setTrainingrecordid(trainingRecordId);
		
		//自动检查
		ApplicationContext applicationContext = SpringApplicationContext.getInstance().getContext();
		AutoRecordCheckService autoCheckService = applicationContext.getBean(com.bjxc.supervise.service.AutoRecordCheckService.class);
		
//		Integer check = autoCheckService.checkTrainingLog(trainingLog);
//		if( check != 0 ){
//			trainingLog.setInspass(check);
//		}

		TrainingLogService trainingLogService = applicationContext.getBean(com.bjxc.supervise.service.TrainingLogService.class);
		trainingLogService.add(trainingLog);
	}
	
	public static Integer addTrainingRecord(int studentId,int coachId,int trainingCarId,int deviceId
			,int subjectId,int trainingLogCode,Date startTime,Date endTime,String deviceNum,String lastImageNum) {
		
		String imageNum1 = new DecimalFormat("0000000000").format(Integer.parseInt(lastImageNum)+1);
		String imageNum2 = new DecimalFormat("0000000000").format(Integer.parseInt(lastImageNum)+2);
		
		DeviceImage savePhoto1 = savePhoto(deviceId, deviceNum, studentId, imageNum1, 0);
		DeviceImage savePhoto2 = savePhoto(deviceId, deviceNum, studentId, imageNum2, 1);
		TrainingRecord trainingRecord = new TrainingRecord();
		trainingRecord.setStudentId(studentId);
		trainingRecord.setCoachId(coachId);
		trainingRecord.setTrainingcarId(trainingCarId);
		trainingRecord.setDeviceId(deviceId);
		trainingRecord.setStartTime(startTime);
		trainingRecord.setEndTime(endTime);
		trainingRecord.setLoginPhotoId(savePhoto1.getId()+"");
		trainingRecord.setLogoutPhotoId(savePhoto2.getId()+"");
		trainingRecord.setCourseCode("121"+subjectId+"110000");
		trainingRecord.setCourseType(1);	//1、实操
		trainingRecord.setSubjectId(subjectId);
		int mileage=10+(int)(Math.random()*20);	//来个10~30的随机数
		trainingRecord.setMileage(mileage);
//		int avevelocity = (int) (mileage/((endTime.getTime()-startTime.getTime())/1000/60/60));
		int avevelocity = (int) (mileage*1000*60*60/(endTime.getTime()-startTime.getTime()));	//防止数值过小损失精度
		trainingRecord.setAvevelocity(avevelocity);
		String formatTrainingLogCode = new DecimalFormat("00000").format(trainingLogCode);
		trainingRecord.setEtrainingLogCode(formatTrainingLogCode);
		
		ApplicationContext applicationContext = SpringApplicationContext.getInstance().getContext();
		DeviceImageService deviceImageService = applicationContext.getBean(com.bjxc.supervise.service.DeviceImageService.class);
		
		//自动检查
//		Integer check = autoCheckService.checkTrainingRecord(trainingRecord);
//		if( check != 0 ){
//		}
		TrainingRecordService trainingRecordService = applicationContext.getBean(com.bjxc.supervise.service.TrainingRecordService.class);
		
		TrainingRecord addTrainingRecord = trainingRecordService.add(trainingRecord);
		deviceImageService.updateTrainingRecordId(savePhoto1.getId(),addTrainingRecord.getId());
		deviceImageService.updateTrainingRecordId(savePhoto2.getId(),addTrainingRecord.getId());
		return addTrainingRecord.getId();
	}
	
	/**
	 * 生成学员登录登出图片
	 * @return
	 */
	public static DeviceImage savePhoto(int deviceId,String deviceNum,int studentId,String imageNum,int type) {
		DeviceImage deviceImage = new DeviceImage();
		deviceImage.setDeviceId(deviceId);
		deviceImage.setObjectId(studentId);
		deviceImage.setImageNum(imageNum);
		deviceImage.setUploadType(1);	//1、自动上传
		deviceImage.setChannelNo(1);	//0、自动分配
		deviceImage.setImageWidth(240);
		deviceImage.setImageHeight(320);
		deviceImage.setEvent(type+17);	//type为0是学员登录，1是学员登出
		deviceImage.setLessonId(100);
		deviceImage.setLocationId(202);
		deviceImage.setFaceProbability(0);
		deviceImage.setPackageNum(0);
		deviceImage.setDataSize(0);
		deviceImage.setCreatetime(new Date());
		deviceImage.setPhotoId(deviceNum+imageNum);
		deviceImage.setIsUpload(1);
		deviceImage.setImageUrl("http://img.91ygxc.com/2017/01/13/efb45f32-9354-46b1-88f2-6d175b2b0bf4_s.jpg");
		
		ApplicationContext applicationContext = SpringApplicationContext.getInstance().getContext();
		DeviceImageService deviceImageService = applicationContext.getBean(com.bjxc.supervise.service.DeviceImageService.class);
		DeviceImage add = deviceImageService.add(deviceImage);
		return add;
	}
	
	/**
	 * 插入GPS数据
	 * @param locationInfo
	 * @return
	 */
	public static com.bjxc.model.LocationInfo insertLocationInfo(com.bjxc.supervise.model.LocationInfo locationInfo,int trainingRecordId) {
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
		ApplicationContext applicationContext = SpringApplicationContext.getInstance().getContext();
		LocationInfoService locationInfoService = applicationContext.getBean(com.bjxc.supervise.service.LocationInfoService.class);
		
		return locationInfoService.add(insertLocationInfo);
	}
	
	public static Message getPhotoMessage(Integer photoNum,String mobile,String deviceNo) {
		File file=new File("/Users/dev/Desktop/9160da2ejw1f3c330kt68j20l90ltwfc.jpg");
		PhotoFileUpload pfile=new PhotoFileUpload();
		
		pfile.setPhotoNum(new DecimalFormat("0000000000").format(photoNum));
		pfile.setFile(file);
		
		Message upTransportMessage = Utils.getUpTransportMessage(pfile, (short)0x0306, mobile, deviceNo,MessageUtils.getCurrentMessageSeq(),MessageUtils.getCurrentMessageNumber());
		return upTransportMessage;
	}

}
