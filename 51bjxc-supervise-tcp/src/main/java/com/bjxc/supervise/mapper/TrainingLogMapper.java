package com.bjxc.supervise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.TrainingLog;

public interface TrainingLogMapper {
	
	Integer add(TrainingLog trainingLog);

	//判定单个学时无效
	@Update("update TrainingLog set inspass=#{inspass},inreason=#{inreason} where id=#{id}")
	Integer updateinspass(@Param("id") Integer id,@Param("inspass") Integer inspass,@Param("inreason") String inreason);
	
	@Select("select * from TrainingLog where recordnum=#{recordnum} order by id desc limit 1")
	TrainingLog getTrainingLogByRecordnum(@Param("recordnum")String recordnum);
	
	//所有学时判定无效
	@Update("update TrainingLog set inspass=#{inspass},inreason=#{inreason} where trainingrecordid=#{trainingrecordid}")
	Integer updateAllinspass(@Param("trainingrecordid") Integer trainingrecordid,@Param("inspass") Integer inspass,@Param("inreason") String inreason);
	
	//获取该教学日志所有有效的学时记录
	@Select("select * from TrainingLog where trainingrecordid=#{trainingrecordid} and inspass IN(1,2)")
	List<TrainingLog> getLogList(@Param("trainingrecordid") Integer trainingrecordid);
	
	
	//获取该教学日志所有有效的学时记录
	@Select("select * from TrainingLog")
	List<TrainingLog> getAllLogList();

	//获取学时总数
	@Select("select count(1) from TrainingLog where trainingrecordid=#{trainingrecordid}")
	Integer totalTrainingLog(@Param("trainingrecordid") Integer trainingrecordid);

	//获取有效学时总数
	@Select("select count(1) from TrainingLog where trainingrecordid=#{trainingrecordid} and inspass IN(1,2)")
	Integer totalValidTrainingLog(@Param("trainingrecordid") Integer trainingrecordid);

	@Select("select * from traininglog where studentnum=#{stunum} order by id desc limit 1")
	TrainingLog getStudentLastLog(String stunum);
}
