package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Select;

import com.bjxc.model.DrivingField;

public interface DrivingFieldMapper {
	
	@Select("select * from  TDrivingField where id = #{id}")
	DrivingField get(Integer id);
}
