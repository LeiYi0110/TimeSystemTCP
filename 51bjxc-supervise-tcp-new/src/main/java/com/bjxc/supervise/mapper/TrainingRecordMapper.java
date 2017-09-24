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

	@Update("update t_training_record set end_time=#{endTime},mileage=#{mileage},avevelocity=#{hour} where id=#{id}")
	void updateEndTime(@Param("id")Integer id,@Param("endTime")Date endTime,@Param("mileage")Integer mileage,@Param("hour")Integer hour);
	
	@Update("update t_training_record set login_photo_id=#{loginPhotoId} where id=#{id}")
	void updateLoginPhoto(@Param("id")Integer id,@Param("loginPhotoId")Integer loginPhotoId);
	
	@Update("update t_training_record set logout_photo_id=#{logoutPhotoId} where id=#{id}")
	void updateLogoutPhoto(@Param("id")Integer id,@Param("logoutPhotoId")Integer logoutPhotoId);
	
	@Select("select * from t_training_record where student_num=#{studentNum} order by id desc limit 1")
	TrainingRecord getLastTrainingLogCode(String studentNum);
	
	@Update("update t_training_record set is_institution_pass=#{inpass},institution_reason=#{inreason},logratio=#{logratio} where id=#{id}")
	void updateInPass(@Param("id")Integer id,@Param("inpass")Integer inpass,@Param("inreason")String inreason,@Param("logratio")Float logratio);

	@Select("select * from t_training_record where id=#{id}")
	TrainingRecord getTrainingRecordById(@Param("id")Integer id);
	
	@Select("select * from t_training_record")
	List<TrainingRecord> getTrainingRecordList();
	
	@Update("update t_training_record tr,"
			+ "(select sum(if(d.max_speed = 0 , 1 , 0)) as zero "
			+ ",sum(if(d.max_speed between 1 and 50 , 1 , 0)) as five "
			+ ",sum(if(d.max_speed > 50 , 1 , 0)) as bigfive"
			+ " from t_training_record a	 left join t_student b on b.stu_num = a.student_num"
			+ " left join t_coach c on c.coach_num = a.coach_num"
			+ " left join t_training_log d on d.training_record_id = #{id}"
			+ ") arr "
			+ "set tr.zero_speed_count=arr.zero,tr.five_speed_count=arr.five,tr.bigfive_speed_count=arr.bigfive "
			+ "where tr.id =#{id};")
	void updateSpeedCount(Integer id);
}
