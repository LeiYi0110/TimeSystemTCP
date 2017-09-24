package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.bjxc.model.DevAssign;
import com.bjxc.model.DevAssignInfo;

public interface DevAssignMapper {
	
	@Insert("insert into tdevassign(deviceId,trainingcarId,sim) Values(#{deviceId},#{trainingcarId},#{sim}) ")
	void add(DevAssign devAssign);

	@Delete("delete from tdevassign where deviceId=#{deviceId}")
	void remove(Integer deviceId);
	
	@Delete("delete from tdevassign where sim like CONCAT(CONCAT('%',#{mobile}), '%')")
	void removeByMobile(String mobile);
	
	@Select("select tda.*,td.devnum,td.mapType,tr.licnum,tr.carnum from tdevassign tda"
			+ " inner join tdevice td on td.id=tda.deviceId"
			+ " inner join trainingcar tr on tr.id=tda.trainingcarId"
			+ " where tda.sim like CONCAT(CONCAT('%',#{mobile}), '%')")
	DevAssignInfo getDevAssignByMobile(String mobile);
}
