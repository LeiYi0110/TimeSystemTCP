package com.bjxc.supervise.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.Coach;
import com.bjxc.model.Student;
import com.bjxc.model.StudentTrainSituation;

public interface StudentMapper {
	@Select("select * from t_student where id=#{id}")
	Student getStudentById(@Param("id")Integer id);
	
	//@Select("select * from tstudent  where stunum=#{stunum}")
	@Select("select ts.*,(select subject_id from t_student_subject tss,t_student tst where tss.student_id=tst.id and tst.stu_num=#{stunum} order by tss.subject_id limit 1) as subjectId from t_student ts where ts.stu_num=#{stunum}")
	Student getStudentByNum(@Param("stunum")String stunum);

/*	@Update("update t_student set isTCPLogin=1 ,TCPStatusChangeTime=#{date} ,TCPLoginTime=#{date} where stunum=#{stunum}")
	void updateTCPLogin(@Param("stunum")String stunum,@Param("date")Date date);
	
	@Update("update tstudent set isTCPLogin=0 , TCPStatusChangeTime=#{date} , TCPLogoutTime=#{date} where stunum=#{stunum}")
	void updateTCPLogout(@Param("stunum")String stunum,@Param("date")Date date);
	
	@Update("update tstudent set lastestUploadTime=#{date} where stunum=#{stunum}")
	void updateLastestUploadTime(@Param("stunum")String stunum,@Param("date")Date date);*/

	@Select("SELECT tr.student_Id,sum(TIMESTAMPDIFF(MINUTE,tr.start_time,tr.end_time)) as totalhour ,sum(tr.mileage/10) as totalmileage,sum(if(tr.subject_id=ts.subject_id,TIMESTAMPDIFF(MINUTE,tr.start_time,tr.end_time),0)) as currenthour,sum(if(tr.subject_id=ts.subject_id,tr.mileage/10,0))as currentmileage FROM t_training_record tr LEFT JOIN t_student_subject ts ON ts.student_id = tr.student_Id LEFT JOIN t_student t ON tr.student_Id = t.ID WHERE t.stu_num = #{studentNum} GROUP BY tr.student_Id")
	StudentTrainSituation getStudentTrainSituation(String studentNum);
	
}
