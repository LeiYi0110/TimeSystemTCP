package com.bjxc.supervise.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.TrainingRecord;

public interface TrainingRecordMapper {
	
	Integer add(TrainingRecord trainingRecord);

	@Update("update trainingrecord set endTime=#{endTime},mileage=#{mileage},avevelocity=#{hour} where id=#{id}")
	void updateEndTime(@Param("id")Integer id,@Param("endTime")Date endTime,@Param("mileage")Integer mileage,@Param("hour")Integer hour);
	
	@Update("update trainingrecord set loginPhotoId=#{loginPhotoId} where id=#{id}")
	void updateLoginPhoto(@Param("id")Integer id,@Param("loginPhotoId")Integer loginPhotoId);
	
	@Update("update trainingrecord set logoutPhotoId=#{logoutPhotoId} where id=#{id}")
	void updateLogoutPhoto(@Param("id")Integer id,@Param("logoutPhotoId")Integer logoutPhotoId);
	
	@Select("select * from trainingrecord where studentId=#{studentId} order by id desc limit 1")
	TrainingRecord getLastTrainingLogCode(Integer studentId);
	
	@Update("update trainingrecord set inpass=#{inpass},inreason=#{inreason},logratio=#{logratio} where id=#{id}")
	void updateInPass(@Param("id")Integer id,@Param("inpass")Integer inpass,@Param("inreason")String inreason,@Param("logratio")Float logratio);

	@Select("select * from trainingrecord where id=#{id}")
	TrainingRecord getTrainingRecordById(@Param("id")Integer id);
	
	@Select("select * from trainingrecord")
	List<TrainingRecord> getTrainingRecordList();
}
