package com.bjxc.supervise.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.supervise.model.Device;



public interface DeviceMapper {
	
	Integer add(Device one);
	
	@Select("select * from t_training_device where id = #{id}")
	Device getDevice(@Param("id")Integer id);
	
	@Select("select td.*,ti.Ins_code from t_training_device td"
			+ " inner join t_institution ti on ti.id=td.ins_id "
			+ " where td.imei = #{imei} ")
	Device getDeviceByImei(@Param("imei")String imei);

	/**
	 * 数据库中是否有该车辆
	 */
	@Select("select count(1) from t_training_car where lic_num = #{licnum}")
	Integer checkCar(String licnum);
	
	/**
	 * 数据库中是否有该终端
	 */
	@Select("select count(1) from t_training_device where imei=#{imei}")
	Integer checkDevice(String imei);
	
	/**
	 * 车辆是否被注册
	 */
	@Select("select count(1) from t_dev_assign ds INNER JOIN t_training_car rc on rc.id=ds.training_car_id where rc.lic_num=#{licnum}")
	Integer checkCarSign(String licnum);
	
	/**
	 * 检查是否有该绑定
	 */
	@Select("select count(1) from t_dev_assign ds INNER JOIN t_training_car rc on rc.id=ds.training_car_id INNER JOIN t_training_device d on d.id=ds.device_id where lic_num=#{licnum} and imei=#{imei} and ds.sim=#{mobile}")
	Integer checkAssign(@Param("licnum")String licnum,@Param("imei")String imei,@Param("mobile")String mobile);
	
	/**
	 * 数据库中是否有该终端
	 */
	@Select("select count(1) from t_dev_assign ds INNER JOIN t_training_device d on d.id=ds.device_id where d.imei=#{imei} or ds.sim=#{mobile}")
	Integer checkDeviceSign(@Param("imei")String imei,@Param("mobile")String mobile);
	
	@Insert("insert t_dev_assign(device_id,training_car_id) value((select id from t_training_device where imei=#{imei}),(select id from training_car_id where lic_num=#{licnum}))")
	Integer addSign(@Param("licnum")String licnum,@Param("imei")String imei);
	
	//修改设备登录状态为已登录
	@Update("update t_training_device set is_Login=1 where id=#{deviceId}")
	void updateTCPLogin(@Param("deviceId")Integer deviceId);
	
	//修改设备登录状态为已登录
	@Update("update t_training_device set is_Login=0 where id=#{deviceId}")
	void updateTCPLogout(@Param("deviceId")Integer deviceId);
}
