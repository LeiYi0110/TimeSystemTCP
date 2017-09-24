package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Select;

import com.bjxc.model.DrivingField;

public interface DrivingFieldMapper {
	
	@Select("select * from  t_driving_field where id = #{id}")
	DrivingField get(Integer id);
}
