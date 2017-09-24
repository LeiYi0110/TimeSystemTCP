package com.bjxc.supervise.Action;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.bjxc.model.Coach;
import com.bjxc.model.Student;
import com.bjxc.supervise.http.crypto.JSPTConstants;
import com.bjxc.supervise.model.Device;
import com.bjxc.supervise.model.ExtendMessageBody;
import com.bjxc.supervise.model.FakeStudent;
import com.bjxc.supervise.model.LoginStudent;
import com.bjxc.supervise.model.StudyingTime;
import com.bjxc.supervise.model.UpTransportMessageBody;
import com.bjxc.supervise.netty.Action;
import com.bjxc.supervise.netty.Header;
import com.bjxc.supervise.netty.HexUtils;
import com.bjxc.supervise.netty.Message;
import com.bjxc.supervise.netty.MessageUtils;
import com.bjxc.supervise.netty.client.ProvinceClient;
import com.bjxc.supervise.netty.server.SpringApplicationContext;
import com.bjxc.supervise.netty.server.TCPMap;
import com.bjxc.supervise.service.CoachService;
import com.bjxc.supervise.service.DeviceImageService;
import com.bjxc.supervise.service.DeviceService;
import com.bjxc.supervise.service.StudentService;
import com.bjxc.supervise.service.TrainingRecordService;

import io.netty.channel.ChannelHandlerContext;

public class TriggerAction implements Action{
	
	private static final Logger logger = LoggerFactory.getLogger(TriggerAction.class);
	@Resource
	private CoachService coachService;
	@Resource
	private StudentService studentService;
	@Resource
	private TrainingRecordService trainingRecordService;
	@Resource
	private DeviceImageService deviceImageService;
	@Resource
	private DeviceService deviceService;

	public void action(ActionContext actionContext) {
		logger.info("TriggerAction转发服务");
		
		//封装后的消息分解
		Message packageMessage = actionContext.getMessag();	//外层用于封装消息
		
		logger.info("主消息总长度："+packageMessage.getBody().length);
		Message message = new Message(packageMessage.getBody());	//里层需要转发的消息
		short messageId = message.getHeader().getId();
		logger.info("message id = " + Integer.toHexString(messageId));
		logger.info("message = " + HexUtils.BinaryToHexString(message.getBytes()));

		ChannelHandlerContext context = null;
		context = TCPMap.getInstance().getChannelMap().get(JSPTConstants.JSPT_PLATFORMNO);
		logger.info("context: "+context);
//		byte[] req = HexUtils.HexStringToBinary("7E8009000160000001589887765106833C130201002201663331343334323234353736333830343600000045333134333534313035323036363438333334303034353236333336343337353812121100000000006400000001000000010260841506EA60D6006F0066003817011511094210EA7F57D690A197861DAEC5A40B4529ACCC883D1884DB5F840A4E8111D7630B4C89A9359E7319B1B48BD1004FE8B11321179E4643AE2064CC8478B6A87669ACF944660881B799ADFDADC410529B1BF2CFD599E03869CC7553FDD563040575F6C0D9EE3329E65D671333673930F54D280457911E2191522B0CEDB39F0A07ED8F5595FCD00FF0045FA86BFC11C897CEDD8163C751E491C1A368DC1765FFFE65863A5B62730960B1260A14C2E0DA61052843ABDB15F15EDCF0084B847D02716313479E45FD137F81AF9F8A95340F379196B7E36BB2405908E3F5613B264511F50FE9F263750B470278EBB687194856AEBEF9D1E64E863C716E294223516CEB3485D6B47E");
//		Message studentLogin = new Message(req);
//		context.write(studentLogin);
		switch (messageId) {
			case  (short)0x01F0: //平台登录，传递消息到省平台
				context = TCPMap.getInstance().getChannelMap().get(JSPTConstants.JSPT_PLATFORMNO);
				context.writeAndFlush(message);
				break;
			case  (short)0x01F1:{ //平台登出，传递消息到省平台
				context = TCPMap.getInstance().getChannelMap().get(JSPTConstants.JSPT_PLATFORMNO);
				context.writeAndFlush(message);
				
				//重新建立省平台连接
				ApplicationContext applicationContext = SpringApplicationContext.getInstance().getContext();
				ProvinceClient client = applicationContext.getBean(com.bjxc.supervise.netty.client.ProvinceClient.class);
				try {
					logger.info("start reconnect province error.");
					client.start();
				} catch (Exception e) {
					logger.info("reconnect province error." + e);
				}
				break;
				}
			case -2:{	//伪造学员学车
				context = TCPMap.getInstance().getChannelMap().get(JSPTConstants.JSPT_PLATFORMNO);
				FakeStudent fakeStudent = new FakeStudent(message.getBody());
				
				String studentNum = fakeStudent.getStudentNum();
				Student studentByNum = studentService.getStudentByNum(studentNum);
				int studentId = studentByNum.getId();
				
				String coachNum = fakeStudent.getCoachNum();
				Coach coachByNum = coachService.getCoachByNum(coachNum);
				int coachId = coachByNum.getId();
				
				//tdevassign里面135条数据（注意，数据库中数据大多差一位）
				String deviceNum = "2226046735231156";
				int deviceId = 211;
				
				Device device = deviceService.getDevice(deviceId);
				
				String trainingCarNum = "京K9887学";
				int trainingCarId = 93;
				
				String mobile = "15898877651";
				
				Integer lastTrainingLogCode = trainingRecordService.getLastTrainingLogCode(studentId);	//该学员最后一条电子教学日志的编号
				String lastPhotoNum = deviceImageService.getLastPhotoNum();	//最大的一张照片编号
				
				if(fakeStudent.getCount()>0){	//只造当天相应分钟学时记录
					
					int subjectId = studentByNum.getSubjectId();
					if(subjectId!=2&&subjectId!=3){
						subjectId = 2;
					}
					
					int trainingLogCode = lastTrainingLogCode+1;
					addTrainingRecord(context, studentNum, coachNum, studentId, coachId, deviceId, deviceNum,
							trainingCarId, mobile, trainingLogCode, subjectId, new Date(),fakeStudent.getCount(),lastPhotoNum, device);
					
				}else{	//直接造完整的科目二科目三学车记录
					//一次1小时的学车要1份电子教学日志，60个学时记录，一个登录，一个登出
					
					Calendar curr = Calendar.getInstance();
					curr.set(Calendar.YEAR,curr.get(Calendar.YEAR)-1);
					Date startTime=curr.getTime();	////假设他从1年前开始学车，每天学一节

					int subjectCount2 = 16;	//科目二需要16个小时的学车
					int subjectCount3 = 24;	//科目二需要24个小时的学车
					
					for(int i=1;i<=subjectCount2;i++){	//科目2，每天每次学车1小时，需要？次
						int subjectId = 2;
						startTime.setTime(startTime.getTime()+24*60*60*1000*(i-1));	//每次增加1天
						int trainingLogCode = lastTrainingLogCode+i;
						
						addTrainingRecord(context, studentNum, coachNum, studentId, coachId, deviceId, deviceNum, trainingCarId
								, mobile, trainingLogCode, subjectId, startTime, 60, lastPhotoNum, device);
					}
					
					for(int i=1;i<=subjectCount3;i++){	//科目3，每天每次学车1小时，需要？次
						int subjectId = 3;
						startTime.setTime(startTime.getTime()+24*60*60*1000*(i-1));	//每次增加1天
						int trainingLogCode = lastTrainingLogCode+subjectCount2+i;
						
						addTrainingRecord(context, studentNum, coachNum, studentId, coachId, deviceId, deviceNum,
								trainingCarId, mobile, trainingLogCode, subjectId, startTime,60,lastPhotoNum,device);
					}
				}
			}
			default:{ //从WEB透传消息到终端，根据手机号码识别不同的终端连接
				context = TCPMap.getInstance().getChannelMap().get(packageMessage.getHeader().getMobile());
				if( context == null){
					//保存消息通道
					logger.info("终端通道为空："+packageMessage.getHeader().getMobile());
					return;
//					TCPMap.getInstance().getChannelMap().put(message.getHeader().getMobile(), actionContext.getChannelHandlerContext());
//					logger.info("保存终端消息通道："+message.getHeader().getMobile());
				}
				
				//重新获取通道
//				context = TCPMap.getInstance().getChannelMap().get(packageMessage.getHeader().getMobile());
				
				//网页发过来的消息流水号与TCP消息流水号增长不一致
				Header header = message.getHeader();
				header.setNumber(MessageUtils.getCurrentMessageNumber());
				message.setHeader(header);
				
				context.writeAndFlush(message);
			}
		}
	}

	/**
	 * 添加一条电子教学日志，包含一次登录登出及各分钟学时的省平台透传，及保存数据库
	 * @param context	省平台通道
	 * @param studentNum
	 * @param coachNum
	 * @param studentId
	 * @param coachId
	 * @param deviceId
	 * @param deviceNum
	 * @param trainingCarId
	 * @param mobile
	 * @param i	电子教学日志编号
	 * @param subjectId
	 * @param startTime	该条电子教学日志的开始时间
	 * @param count	该条电子教学日志分钟学时数
	 */
	public static void addTrainingRecord(ChannelHandlerContext context, String studentNum, String coachNum, int studentId,
			int coachId, int deviceId, String deviceNum, int trainingCarId, String mobile, int trainingLogCode, int subjectId,
			Date startTime,int count,String lastPhotoNum,Device device) {
		
		long endTimeLong = startTime.getTime()+60*1000*count;
		Date endTime = new Date(endTimeLong);
		
		//1、添加电子教学日志到数据库
		Integer trainingRecordId = FakeMethod.addTrainingRecord(studentId, coachId, trainingCarId, deviceId, subjectId, trainingLogCode, startTime, endTime, deviceNum, lastPhotoNum);
		logger.info("教学日志：" + trainingRecordId);

		//2、学员登录转发省平台
		Message studentLogin = FakeMethod.getStudentLogin(mobile, studentNum, coachNum, subjectId,deviceNum);
		context.writeAndFlush(studentLogin);
		
		for(int j=1;j<=count;j++){	//3、每次学车有count分钟学时
			
			long recordTimeLong = startTime.getTime()+60*1000*j;
			Date recordTime = new Date(recordTimeLong);
			
			StudyingTime studyTimeMessage = FakeMethod.getStudyTimeMessage(deviceNum, mobile, j, studentNum, coachNum, recordTime, subjectId);
			
			//学时记录保存到数据库
			FakeMethod.saveStudyTime(studyTimeMessage, trainingRecordId);
			
			
			Message commonMessage = Utils.getUpTransportMessage2(studyTimeMessage, (short)0x0203, mobile, deviceNum,device.getKey(),device.getPasswd());
			
			
			logger.info("passthroughHandler message to province: " + Integer.toHexString(commonMessage.getHeader().getId()));

			
			//分钟学时记录转发省平台
			context.writeAndFlush(commonMessage);
			logger.info("添加分钟学时：" + studyTimeMessage.getStudyingTimeNum());
			logger.info("转发到省平台：" + HexUtils.BinaryToHexString(commonMessage.getBytes()));
			
		}
		
		//4、学员登出转发省平台
		Message studentLogout = FakeMethod.getStudentLogout(mobile, studentNum, coachNum, subjectId,deviceNum);
		context.writeAndFlush(studentLogout);
	}
}
