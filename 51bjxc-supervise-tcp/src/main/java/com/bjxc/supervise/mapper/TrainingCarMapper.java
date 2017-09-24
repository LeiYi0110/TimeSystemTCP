package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Select;

public interface TrainingCarMapper {
	@Select("select * from trainingcar where id=#{id}")
	com.bjxc.model.TrainingCar getTrainingCarById(int id);
	
	@Select("select * from trainingcar where licnum=#{licnum}")
	com.bjxc.model.TrainingCar getTrainingCarByLicnum(String licnum);
}
