package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.bjxc.model.DevAssign;
import com.bjxc.model.DevAssignInfo;

public interface DevAssignMapper {
	
	@Insert("insert into t_dev_assign(device_id,training_car_id,sim) Values(#{deviceId},#{trainingcarId},#{sim}) ")
	void add(DevAssign devAssign);

	@Delete("delete from t_dev_assign where deviceId=#{device_id}")
	void remove(Integer deviceId);
	
	@Delete("delete from t_dev_assign where sim like CONCAT(CONCAT('%',#{mobile}), '%')")
	void removeByMobile(String mobile);
	
	@Select("select tda.*,td.dev_num,td.map_type,tr.lic_num,tr.car_num from t_dev_assign tda"
			+ " inner join t_training_device td on td.id=tda.device_id"
			+ " inner join t_training_car tr on tr.id=tda.training_car_id"
			+ " where tda.sim like CONCAT(CONCAT('%',#{mobile}), '%')")
	DevAssignInfo getDevAssignByMobile(String mobile);
}
