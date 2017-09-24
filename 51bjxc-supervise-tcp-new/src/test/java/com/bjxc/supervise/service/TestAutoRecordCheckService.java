/*package com.bjxc.supervise.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bjxc.model.TrainingLog;
import com.bjxc.model.TrainingRecord;

@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations="classpath:spring/applicationContext.xml") // 加载配置
public class TestAutoRecordCheckService {
	
	@Resource
	private AutoRecordCheckService autoRecordCheckService;
	
	@Resource
	TrainingLogService trainingLogService;
	
	@Resource
	TrainingRecordService trainingRecordService;
	
	
	@Test
	public void TestcheckMinRecord(){
		
//		List<TrainingLog> loglist = trainingLogService.getAllLogList();
//		for (TrainingLog trainingLog : loglist) {
//			autoRecordCheckService.checkMinRecord(trainingLog);
//
//		}
		TrainingLog trainingLog = trainingLogService.getTrainingLogById(989);
		autoRecordCheckService.checkMinRecord(trainingLog);

	}
	
	@Test
	public void TestcheckTrainingRecord(){
		List <TrainingRecord>list = trainingRecordService.getTrainingRecordList();
		for (TrainingRecord trainingRecord : list) {
			autoRecordCheckService.checkTrainingRecord(trainingRecord);

		}
//		TrainingRecord teachLog = trainingRecordService.getTrainingRecordById(711);
//		autoRecordCheckService.checkTrainingRecord(teachLog);
	}

}
*/