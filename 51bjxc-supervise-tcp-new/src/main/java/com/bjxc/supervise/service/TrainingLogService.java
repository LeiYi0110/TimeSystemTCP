package com.bjxc.supervise.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.model.TrainingLog;
import com.bjxc.supervise.mapper.TrainingLogMapper;

@Service
public class TrainingLogService {

	@Resource
	private TrainingLogMapper trainingLogMapper;

	public TrainingLog add(TrainingLog trainingLog) {
		trainingLogMapper.add(trainingLog);
		return trainingLog;
	}
	
	public TrainingLog getStudentLastLog(String stunum) {
		return trainingLogMapper.getStudentLastLog(stunum);
	}

	public void updatePass(Integer id, Integer inspass, String inreason) {
		trainingLogMapper.updateinspass(id, inspass, inreason);
	}

	public TrainingLog getTrainingLogByRecordnum(String recordnum) {
		return trainingLogMapper.getTrainingLogByRecordnum(recordnum);
	}

	public TrainingLog getTrainingLogById(Integer recordnum) {
		return trainingLogMapper.getTrainingLogById(recordnum);
	}

	public void updateAllinspass(Integer trainingrecordid, Integer inspass, String inreason) {
		trainingLogMapper.updateAllinspass(trainingrecordid, inspass, inreason);
	}

	public List<TrainingLog> getLogList(Integer trainingrecordid) {
		return trainingLogMapper.getLogList(trainingrecordid);
	}
	
	public List<TrainingLog> getAllLogList() {
		return trainingLogMapper.getAllLogList();
	}

	public int totalTrainingLog(Integer trainingrecordid) {
		return trainingLogMapper.totalTrainingLog(trainingrecordid);
	}

	public int totalValidTrainingLog(Integer trainingrecordid) {
		return trainingLogMapper.totalValidTrainingLog(trainingrecordid);
	}

	public TrainingLog getRecordFirstLog(Integer recordId){
		return trainingLogMapper.getRecordFirstLog(recordId);
	}
}
