package com.bjxc.supervise.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.model.TrainingCar;
import com.bjxc.supervise.mapper.TrainingCarMapper;

@Service
public class TrainingCarService {
	@Resource 
	private TrainingCarMapper trainingCarMapper;
	
	public TrainingCar getTrainingCarById(int id) {
//		TrainingCar trainingCar = new TrainingCar();
//		trainingCar.setId(1);
//		trainingCar.setCarnum("21324");
//		return trainingCar;
		return trainingCarMapper.getTrainingCarById(id);
	}
	
	public TrainingCar getTrainingCarByLicnum(String licnum) {
		return trainingCarMapper.getTrainingCarByLicnum(licnum);
	}
}
