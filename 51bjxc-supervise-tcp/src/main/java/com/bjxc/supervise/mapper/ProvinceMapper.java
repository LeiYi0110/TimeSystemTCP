package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Insert;

import com.bjxc.model.Province;

public interface ProvinceMapper {
	
	@Insert("insert into tprovinceRecord(msgid,extendmsgid,msgcontent,createtime) values(#{msgid},#{extendmsgid},#{msgcontent},#{createtime})")
	void add(Province province);

}
