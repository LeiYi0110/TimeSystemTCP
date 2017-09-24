package com.bjxc.supervise.service;

import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bjxc.json.JacksonBinder;
import com.bjxc.model.AmapConversionResult;
import com.bjxc.model.Coach;
import com.bjxc.model.DeviceImage;
import com.bjxc.model.DrivingField;
import com.bjxc.model.LocationInfo;
import com.bjxc.model.Student;
import com.bjxc.model.TrainingLog;
import com.bjxc.model.TrainingRecord;

/**
 * 
 * @author levin
 *
 */

@Service
public class AutoRecordCheckService {

	private static final Logger logger = LoggerFactory.getLogger(AutoRecordCheckService.class);

	@Resource
	private SystemConfigService systemConfigService;
	
	@Resource
	private CoachService coachService;

	@Resource
	private StudentService studentService;
	
	@Resource
	private LocationInfoService locationInfoService;

	@Resource
	private DrivingFieldService drivingFieldService;
	
	@Resource
	TrainingLogService trainingLogService;
	
	@Resource
	TrainingRecordService trainingRecordService;

	@Resource
	DeviceImageService deviceImageService;	

	@Value("${bjxc.amap.webServiceKey}")
	private String webServiceKey; 
	
	
	
	private final Integer Invalid = -2;
	private final Integer Valid = 2;

	/**
	 * 0表示未审核，1表示手动审核通过，-1表示手动审核未通过，2表示自动审核通过，-2表示自动审核未通过
	 * 在上报学时记录之后调用 
	 * @param tlog
	 * @return
	 */
	public void checkMinRecord(TrainingLog tlog) {

      	logger.info("分钟学时自动审核开始....." + tlog.getRecordnum());

		// yyyyMMddHHmmss，临时方法：从数据库中获取
 		String trainningStartTime = systemConfigService.getTrainningStartTime();
 		String trainningEndTime = systemConfigService.getTrainningEndTime();
  		Date trainningStartDate = getMinDateOneDay(trainningStartTime, "yyyyMMddHHmmss");
		Date trainningEndDate = getMinDateOneDay(trainningEndTime, "yyyyMMddHHmmss");
		
		//
   		LocationInfo locationInfo = locationInfoService.getLocationInfoById(tlog.getLocationinfoid());

		
		// 1:有效培训时段：不在有效培训时间段内的学时无效，有效时段应按地市范围设置，可按培训机构设置；（参数：实车教学有效培训时段、课堂和模拟教学有效培训时段、远程教学有效培训时段）
 		Date trainningTime = getMinDateOneDay(locationInfo.getTime(), "yyMMddHHmmss");
		logger.info("1、有效培训时段：" + trainningTime.toString());
		if (trainningTime.before(trainningStartDate) || trainningTime.after(trainningEndDate)) {
			trainingLogService.updatePass(tlog.getId(), Invalid, "培训时段无效");
			return;
		}

		// 2:教练员规则：是否备案、是否列入黑名单、是否超出准教车型；(学时)
		Coach coachByNum = coachService.getCoachByNum(tlog.getCoachnum());
 		Student studentByNum = studentService.getStudentByNum(tlog.getStudentnum());
		logger.info("2、教练员规则：" + tlog.getCoachnum());
		if (coachByNum.getIsProvince() != 1) {
			trainingLogService.updatePass(tlog.getId(), Invalid, "教练员未备案");
			return;
		}
 		if (!coachByNum.getTeachpermitted().toUpperCase().contains(studentByNum.getTraintype().toUpperCase())) {
			trainingLogService.updatePass(tlog.getId(), Invalid, "教练员超出准教车型");
			return;
		}
		

		// 3:教练车规则：是否备案、是否年审、是否与培训车型不符；(学时)，测试暂时不检查
//		logger.info("3、教练车：" + coachByNum.get);
//		if (trainingLogWithLocation.getTrainingCarIsProvince() != 1) {
//			trainingLogService.updatePass("-2", trainingLogWithLocation.getId() + "", "教练车备案审核不通过");
//			continue;
//		}
//		if (!trainingLogWithLocation.getTrainingCarPerdritype().toUpperCase()
//				.contains(studentByNum.getTraintype().toUpperCase())) {
//			trainingLogService.updatePass("-2", trainingLogWithLocation.getId() + "", "教练车培训车型审核不通过");
//			continue;
//		}

		// 4:分钟学时中发动机转速为0，该分钟学时无效；（学时）
		logger.info("4、发动机转速：" + locationInfo.getEngine_speed());
  		if (locationInfo.getEngine_speed() == 0) {
			trainingLogService.updatePass(tlog.getId(), Invalid, "发动机转速为0");
			return;
		}

		// 5:连续2分钟或以上分钟学时中最大速度为0或行驶记录速度为0，该2分钟或以上的分钟学时无效；（参数：最大允许停车时间，默认2分钟）（学时）
 		logger.info("5、连续2分钟或以上分钟学时中最大速度为0或行驶记录速度为0，该2分钟或以上的分钟学时无效");
 		if (locationInfo.getCarSpeed() == 0) {
			String start = tlog.getRecordnum().substring(0, 24);
			String end = tlog.getRecordnum().substring(24);
			//判断前一个学时速度是否为0
			String preRecordNum = start + (Integer.parseInt(end) - 1);
			
			TrainingLog _trainingLog = trainingLogService.getTrainingLogByRecordnum(preRecordNum);
			if( _trainingLog != null ){
				LocationInfo _locationInfo = locationInfoService.getLocationInfoById(_trainingLog.getLocationinfoid());
				if (_locationInfo != null && (_locationInfo.getCarSpeed() == 0)) {
					trainingLogService.updatePass(tlog.getId(), Invalid, "连续行驶速度为0");
					trainingLogService.updatePass(_trainingLog.getId(), Invalid, "连续行驶速度为0");
					return;
				}
			}
		}

		// 6:连续2分钟或以上分钟学时的里程为0，该2分钟或以上的分钟学时无效；（学时）
 		logger.info("6、连续2分钟或以上分钟学时的里程为0，该2分钟或以上的分钟学时无效");
 		if (locationInfo.getSum_distance()==0) {
			String start = tlog.getRecordnum().substring(0, 24);
			String end = tlog.getRecordnum().substring(24);
			//判断前一个里程是否为0
			String preRecordNum = start + (Integer.parseInt(end) - 1);
			TrainingLog _trainingLog = trainingLogService.getTrainingLogByRecordnum(preRecordNum);
			if (_trainingLog != null && _trainingLog.getMileage() == 0) {
				trainingLogService.updatePass(tlog.getId(), Invalid, "连续里程为0");
				trainingLogService.updatePass(_trainingLog.getId(), Invalid, "连续里程为0");
				return;
			}
		}

		// 7:判断学时记录中上传的GNSS坐标是否在教学区域内，出区域的分钟学时为无效学时。
 		logger.info("7、判断学时记录中上传的GNSS坐标是否在教学区域内");
		
//		double latitude = locationInfo.getLatitude();
//		double longtitude = locationInfo.getLongtitude();
// 		double longtitudetemp =  longtitude/1000000;
//		double latitudetemp = latitude/1000000;
// 		Map map = new HashMap<>();
//		map.put("longtitude", longtitudetemp);
// 		map.put("latitude", latitudetemp);
// 		List<Map> list = new ArrayList<>();
//		list.add(map);
//		try {
// 			List<Map> conversion = conversion(list);
//			Map map2 = conversion.get(0);
//			double longtitude2 = Double.parseDouble((String) map2.get("longtitude")) ;
//			double latitude2 = Double.parseDouble((String) map2.get("latitude"));
//			locationInfo.setLongtitude((int) (longtitude2*1000000));
//			locationInfo.setLatitude((int) (latitude2*1000000));
//		} catch (Exception e1) {
// 			e1.printStackTrace();
//		}
		
		Point2D.Double point = new Point2D.Double(locationInfo.getLatitude(),
				locationInfo.getLongtitude());
		List<Point2D.Double> pointList = new ArrayList<Point2D.Double>();
		DrivingField drivingField = drivingFieldService.getDrivingFieldById(coachByNum.getDrivingFieldId());
		String location = drivingField.getLocation();
		String[] split = location.split(";");
		for (int i = 0; i < split.length; i++) {
			String[] split2 = split[i].split(",");
			pointList.add(new Point2D.Double(Double.parseDouble(split2[1]) * 1000000,
					Double.parseDouble(split2[0]) * 1000000));
		}
		if (!checkWithJdkPolygon(point, pointList)) {
			trainingLogService.updatePass(tlog.getId(), Invalid, "位置不在教学区域");
			return;
		}

		// 学时自动审核通过
		trainingLogService.updatePass(tlog.getId(), Valid, "");

		logger.info("分钟学时自动审核通过结束....." + tlog.getRecordnum());
	}

	/**
	 * 自动检查教学日志，在学员登出的时候调用
	 * 
	 * @param trecord
	 * @return
	 */
	public void checkTrainingRecord(TrainingRecord teachLog) {
		
		logger.info("实车教学日志审核开始....." + teachLog.getId());

		// R1：一次电子教学日志的总里程<100米 ，该条教学日志对应的分钟学时均无效。
		int mileage = teachLog.getMileage();

		logger.info("1、一次电子教学日志的总里程<100米审核" + mileage);

		if (mileage < 1) {
			
			// 设置该条教学日志对应的分钟学时均无效
			trainingLogService.updateAllinspass(teachLog.getId(), Invalid, "总里程<100米");

			// 设置该条教学日志结果
			trainingRecordService.updateInPass(teachLog.getId(), Invalid, "总里程<100米", 0f);
			
			return;
		}

		// R2:处理照片规则
		logger.info("2、照片规则");
		checkPhoto(teachLog);

		// R3：学时规则满足，有效学时>=75%
		logger.info("3、学时规则满足，有效学时>=75%");
		checkTotalLog(teachLog);
		
	}
	
	
	/**
	 * 检查教学日志的照片
	 * 
	 * @param teachLog
	 */
	public void checkPhoto(TrainingRecord teachLog) {
		// 时间规则：照片的时间不在登录登出时间范围之内，该照片无效。
		// 数量规则: 检查本次电子教学日志签到、签退、定时照片（每15分钟一张）的数量，缺少的照片为缺失照片；

		// 未检查照片列表：1表示有效，-1表示无效，其他表示未检查

		List<Date> invalidDeviceImageList = new ArrayList<Date>(10);

		// 按照时间升序排列，获取该电子教学日志的所有照片
		List<DeviceImage> deviceImageList = deviceImageService.getDeviceImage(teachLog.getId());
		
		//教学日志开始与结束
		Date teachStartDate = new Date(teachLog.getStartTime().getTime()-1000*10);
		Date teachEndDate = teachLog.getEndTime();

		if( deviceImageList == null || deviceImageList.size()<1){
			trainingLogService.updateAllinspass(teachLog.getId(), Invalid, "无照片.");
			return;
		}
		
		//检查学员签到、学员签出照片，第一张为签到照片，最后一张为签出照片
		DeviceImage deviceImageIn = deviceImageList.get(0);
		if( deviceImageIn.getEvent() != 17){
			invalidDeviceImageList.add(getMinDate(deviceImageIn.getCreatetime()));
		}
		if( deviceImageList.size()>1){
			DeviceImage deviceImageOut = deviceImageList.get(deviceImageList.size()-1);
			if(deviceImageOut.getEvent() != 18){
				invalidDeviceImageList.add(getMinDate(teachEndDate));
			}
		}
		
		
	
		
		// 从0开始包含签到、签出的照片，都是以分钟为判断标准
		int minnum = 0;
		Date minDate = new Date(teachStartDate.getTime() + minnum * 15 * 60 * 1000);
		Date minDate15 = null;
		Date imageDate = null;
		boolean valid = false;
		while (minDate.before(teachEndDate)) {
			minnum++;
			minDate15 = new Date(minDate.getTime() + 15 * 60 * 1000);
			if(  minDate15.after(teachEndDate)){
				break;
			}
			
			// 15分钟判断是否有照片，如果没有表示是无效照片
			for (DeviceImage deviceImage : deviceImageList) {
				imageDate = deviceImage.getCreatetime();
				// 如果有对应时段的照片，说明照片有效
				if (!imageDate.before(minDate) && !imageDate.after(minDate15)) {
					valid = true;
					break;
				}
			}

			// 如果是无效照片，则记录到无效照片列表
			if (!valid) {
				invalidDeviceImageList.add(minDate);
				logger.info("无效照片：" + minDate.toString());
			}

			// 继续判断
			valid = false;
			minDate = minDate15;
		}

		// 根据无效照片，设置无效学时
		if (invalidDeviceImageList.size() > 0) {
			// 根据教学日志id获取其所有学时列表
			List<TrainingLog> trainingLogList = trainingLogService.getLogList(teachLog.getId());
			long startDateFlag = invalidDeviceImageList.get(0).getTime();
			long endDateFlag = 0;
			long imageDateflag = 0;
			Date invalidImageDate = null;
			for (int i = 0; i < invalidDeviceImageList.size(); i++) {
				invalidImageDate = invalidDeviceImageList.get(i);
				endDateFlag = invalidImageDate.getTime();

				// 如果连续两张照片无效，则所有分钟学时无效
				if (endDateFlag > startDateFlag + 15 * 60 * 1000) {
					trainingLogService.updateAllinspass(teachLog.getId(), Invalid, "连续两张照片无效或缺失.");
					break;
				} else { // 继续判断
					startDateFlag = endDateFlag;
				}

				// 设置前后7个学时无效
				for (TrainingLog trainingLog : trainingLogList) {
					imageDateflag = trainingLog.getRecordtime().getTime();
					if (Math.abs(imageDateflag - endDateFlag) <= 7) {
						trainingLogService.updatePass(trainingLog.getId(),Invalid, "无效或缺失照片.");
					}
				}
			}

		}
	}

	/**
	 * 检查教学日志的学时是否满足3/4
	 * 
	 * @param teachLog
	 */
	private void checkTotalLog(TrainingRecord teachLog) {
		int totalLog = trainingLogService.totalTrainingLog(teachLog.getId());
		int totalValidLog = trainingLogService.totalValidTrainingLog(teachLog.getId());
		if (totalLog != 0) {
			float logratio = (float)totalValidLog / totalLog;
			if (logratio < 0.75) {
				logratio = (float) logratio*4 / 3;
				if (logratio > 1) {
					logratio = 1.00f;
				}
				// 更新状态
				trainingRecordService.updateInPass(teachLog.getId(), Invalid, "计算认可学时："+logratio*100+"%通过("+Math.floor(logratio*totalLog)+"条)", logratio);
			} else {
				trainingRecordService.updateInPass(teachLog.getId(), Valid, "计算认可学时：" + logratio*100+"%通过("+Math.floor(logratio*totalLog)+"条)", logratio);
			}
		}else{
			trainingRecordService.updateInPass(teachLog.getId(), Invalid, "计算认可学时：0条", 0f);
		}
	}
	

	/**
	 * 将时间转换成分钟
	 * 
	 * @param date
	 * @return
	 */
	private Date getMinDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String datestr = sdf.format(date);
		try {
			Date newDate = sdf.parse(datestr);
			return newDate;
		} catch (Exception e) {
			// Do nothing
		}
		return date;
	}

	/**
	 * 获取同一天的小时分钟秒，去掉天的差别
	 * 
	 * @param datestr
	 * @param formatstr
	 * @return
	 */
	private Date getMinDateOneDay(String datestr, String formatstr) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatstr);
		SimpleDateFormat sdf2 = new SimpleDateFormat("2017-01-01 HH:mm:ss");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date1 = sdf.parse(datestr);
			String str2 = sdf2.format(date1);
			return sdf3.parse(str2);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		//
		return new Date();
	}

	/**
	 * java 判断点是否在多边形区域内
	 * 
	 * @param point
	 * @param polygon
	 * @return
	 */
	private boolean checkWithJdkPolygon(Point2D.Double point, List<Point2D.Double> polygon) {
		java.awt.Polygon p = new Polygon();
		// java.awt.geom.GeneralPath
		final int TIMES = 1000;
		for (Point2D.Double d : polygon) {
			int x = (int) d.x * TIMES;
			int y = (int) d.y * TIMES;
			p.addPoint(x, y);
		}
		int x = (int) point.x * TIMES;
		int y = (int) point.y * TIMES;
		return p.contains(x, y);
	}
	
	/*
	 * 把GPS坐标改为高德坐标
	 */
	public List<Map> conversion(List<Map> list) throws Exception{

		if(list.size() < 1){
			return list;
		}

		String coordinateString = StringUtils.join(
				list.stream().filter(f -> f.get("longtitude") != null && f.get("latitude") != null)
						.map(m -> (m.get("longtitude") + "," + m.get("latitude"))).collect(Collectors.toList()),
				";");
		String url = String.format(
				"http://restapi.amap.com/v3/assistant/coordinate/convert?key=%s&locations=%s&coordsys=gps",
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
