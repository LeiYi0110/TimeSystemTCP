package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bjxc.model.LocationInfo;

public interface LocationInfoMapper {
	
	Integer add(LocationInfo locationInfo);

	@Select("select * from r_location_info where id=#{id}")
	LocationInfo getLocationInfoById(@Param("id")Integer id);
	
}
