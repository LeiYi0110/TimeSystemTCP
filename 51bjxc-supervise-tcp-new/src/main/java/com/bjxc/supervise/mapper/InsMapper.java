package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Select;

import com.bjxc.model.Ins;

public interface InsMapper {
	
	@Select("select * from t_institution where id=#{insId}")
	Ins getInsById(Integer insId);
}
