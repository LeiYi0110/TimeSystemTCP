package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.supervise.model.Device;



public interface DeviceMapper {
	
	Integer add(Device one);
	
	@Select("select * from tDevice where id = #{id}")
	Device getDevice(@Param("id")Integer id);
	
	@Select("select td.*,ti.Inscode from tDevice td"
			+ " inner join tinstitution ti on ti.id=td.insId "
			+ " where td.imei = #{imei} ")
	Device getDeviceByImei(@Param("imei")String imei);

	/**
	 * 数据库中是否有该车辆
	 */
	@Select("select count(1) from trainingcar where licnum = #{licnum}")
	Integer checkCar(String licnum);
	
	/**
	 * 数据库中是否有该终端
	 */
	@Select("select count(1) from tdevice where imei=#{imei}")
	Integer checkDevice(String imei);
	
	/**
	 * 车辆是否被注册
	 */
	@Select("select count(1) from tdevassign ds INNER JOIN trainingcar rc on rc.id=ds.trainingcarId where rc.licnum=#{licnum}")
	Integer checkCarSign(String licnum);
	
	/**
	 * 数据库中是否有该终端
	 */
	@Select("select count(1) from tdevassign ds INNER JOIN tdevice d on d.id=ds.deviceId where d.imei=#{imei} or ds.sim=#{mobile}")
	Integer checkDeviceSign(@Param("imei")String imei,@Param("mobile")String mobile);
	
	@Insert("insert tdevassign(deviceId,trainingcarId) value((select id from tdevice where imei=#{imei}),(select id from trainingcar where licnum=#{licnum}))")
	Integer addSign(@Param("licnum")String licnum,@Param("imei")String imei);
	
	//修改设备登录状态为已登录
	@Update("update tdevice set isLogin=1 where id=#{deviceId}")
	void updateTCPLogin(@Param("deviceId")Integer deviceId);
	
	//修改设备登录状态为已登录
	@Update("update tdevice set isLogin=0 where id=#{deviceId}")
	void updateTCPLogout(@Param("deviceId")Integer deviceId);
}
