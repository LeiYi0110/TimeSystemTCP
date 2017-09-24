package com.bjxc.supervise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.TrainingLog;

public interface TrainingLogMapper {
	
	Integer add(TrainingLog trainingLog);

	//判定单个学时无效
	//TODO:修改实体类
	@Update("update t_training_log set is_institution_pass=#{inspass},institution_reason=#{inreason} where id=#{id}")
	Integer updateinspass(@Param("id") Integer id,@Param("inspass") Integer inspass,@Param("inreason") String inreason);
	
	@Select("select * from t_training_log where record_num=#{recordnum} order by id desc limit 1")
	TrainingLog getTrainingLogByRecordnum(@Param("recordnum")String recordnum);
	
	@Select("select * from t_training_log where id=#{id}")
	TrainingLog getTrainingLogById(@Param("id")Integer id);
	
	//所有学时判定无效
	@Update("update t_training_log set is_institution_pass=#{inspass},institution_reason=#{inreason} where training_record_id=#{trainingrecordid}")
	Integer updateAllinspass(@Param("trainingrecordid") Integer trainingrecordid,@Param("inspass") Integer inspass,@Param("inreason") String inreason);
	
	//获取该教学日志所有有效的学时记录
	@Select("select * from t_training_log where training_record_id=#{trainingrecordid} and is_institution_pass IN(1,2)")
	List<TrainingLog> getLogList(@Param("trainingrecordid") Integer trainingrecordid);
	
	
	//获取该教学日志所有有效的学时记录
	@Select("select * from t_training_log")
	List<TrainingLog> getAllLogList();

	//获取学时总数
	@Select("select count(1) from t_training_log where training_record_id=#{trainingrecordid}")
	Integer totalTrainingLog(@Param("trainingrecordid") Integer trainingrecordid);

	//获取有效学时总数
	@Select("select count(1) from t_training_log where training_record_id=#{trainingrecordid} and is_institution_pass IN(1,2)")
	Integer totalValidTrainingLog(@Param("trainingrecordid") Integer trainingrecordid);

	@Select("select * from t_training_log where student_num=#{stunum} order by id desc limit 1")
	TrainingLog getStudentLastLog(String stunum);
	
	@Select("select * from t_training_log where training_record_id=#{recordId} order by record_num desc limit 1")
	TrainingLog getRecordFirstLog(Integer recordId);
}
