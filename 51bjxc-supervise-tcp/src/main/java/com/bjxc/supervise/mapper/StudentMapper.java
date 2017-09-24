package com.bjxc.supervise.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.Coach;
import com.bjxc.model.Student;
import com.bjxc.model.StudentTrainSituation;

public interface StudentMapper {
	@Select("select * from tstudent where id=#{id}")
	Student getStudentById(@Param("id")Integer id);
	
	//@Select("select * from tstudent  where stunum=#{stunum}")
	@Select("select ts.*,(select subjectid from tstudentsubject tss,tstudent tst where tss.StudentId=tst.id and tst.stunum=#{stunum} order by tss.subjectid limit 1) as subjectId from tstudent ts where ts.stunum=#{stunum}")
	Student getStudentByNum(@Param("stunum")String stunum);

	@Update("update tstudent set isTCPLogin=1 ,TCPStatusChangeTime=#{date} ,TCPLoginTime=#{date} where stunum=#{stunum}")
	void updateTCPLogin(@Param("stunum")String stunum,@Param("date")Date date);
	
	@Update("update tstudent set isTCPLogin=0 , TCPStatusChangeTime=#{date} , TCPLogoutTime=#{date} where stunum=#{stunum}")
	void updateTCPLogout(@Param("stunum")String stunum,@Param("date")Date date);
	
	@Update("update tstudent set lastestUploadTime=#{date} where stunum=#{stunum}")
	void updateLastestUploadTime(@Param("stunum")String stunum,@Param("date")Date date);

	@Select("select tr.id,tr.studentId,tr.subjectId,ts.SubjectId as currentSubject,sum(TIMESTAMPDIFF(MINUTE,tr.startTime,tr.endTime)) as totalhour,sum(tr.mileage/10) as totalmileage,sum(if(tr.subjectId=ts.SubjectId,TIMESTAMPDIFF(MINUTE,tr.startTime,tr.endTime),0)) as currenthour,sum(if(tr.subjectId=ts.SubjectId,tr.mileage/10,0))as currentmileage from trainingrecord tr left join tstudentsubject ts on ts.StudentId=tr.studentId INNER JOIN tstudent t on t.stunum=#{studentNum} where tr.studentId=t.ID")
	StudentTrainSituation getStudentTrainSituation(String studentNum);
	
}
