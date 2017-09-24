package com.bjxc.supervise.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.model.TrainingRecord;
import com.bjxc.supervise.mapper.TrainingRecordMapper;

@Service
public class TrainingRecordService {
	
	@Resource 
	private TrainingRecordMapper trainingRecordMapper;
	
	public TrainingRecord add(TrainingRecord studyingTime){
		trainingRecordMapper.add(studyingTime);
		return studyingTime;
	}

	public void updateEndTime(Integer id,Date endTime,Integer mileage,Integer hour){
		trainingRecordMapper.updateEndTime(id,endTime,mileage,hour);
	}
	
	public void updateLoginPhoto(Integer id,Integer loginId){
		trainingRecordMapper.updateLoginPhoto(id,loginId);
	}
	
	public void updateLogoutPhoto(Integer id,Integer logoutId){
		trainingRecordMapper.updateLogoutPhoto(id,logoutId);
	}
	
	public Integer getLastTrainingLogCode(Integer studentId){
		TrainingRecord lastTrainingLogCode = trainingRecordMapper.getLastTrainingLogCode(studentId);
		if(lastTrainingLogCode==null){
			return 0;
		}
		return Integer.parseInt(lastTrainingLogCode.getEtrainingLogCode());
	}
	
	public void updateInPass(Integer id, Integer inpass, String inreason, Float logratio){
		trainingRecordMapper.updateInPass(id, inpass, inreason, logratio);
	}
	
	public TrainingRecord getTrainingRecordById(Integer id){
		return trainingRecordMapper.getTrainingRecordById(id);
	}
	
	
	public List<TrainingRecord> getTrainingRecordList(){
		return trainingRecordMapper.getTrainingRecordList();
	}
}
