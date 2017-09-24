package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Select;

public interface TrainingCarMapper {
	@Select("select * from t_training_car where id=#{id}")
	com.bjxc.model.TrainingCar getTrainingCarById(int id);
	
	@Select("select * from t_training_car where lic_num=#{licnum}")
	com.bjxc.model.TrainingCar getTrainingCarByLicnum(String licnum);
}
