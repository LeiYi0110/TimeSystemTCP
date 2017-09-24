package com.bjxc.supervise.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.Coach;

public interface CoachMapper {
	@Select("select * from t_coach where id=#{id}")
	Coach getCoachById(@Param("id")Integer id);
	
	@Select("select * from t_coach where coach_num=#{coachnum}")
	Coach getCoachByNum(@Param("coachnum")String coachnum);
	
	//修改教练登录状态为已登录并修改状态更改时间
	@Update("update t_coach set is_tcp_login=1,tcp_status_changetime=#{date} where coach_num=#{coachnum}")
	void updateTCPLogin(@Param("coachnum")String coachnum,@Param("date")Date date);
	
	//修改教练登录状态为未登录并修改状态更改时间
	@Update("update t_coach set is_tcp_login=0,tcp_status_changetime=#{date} where coach_num=#{coachnum}")
	void updateTCPLogout(@Param("coachnum")String coachnum,@Param("date")Date date);
}
