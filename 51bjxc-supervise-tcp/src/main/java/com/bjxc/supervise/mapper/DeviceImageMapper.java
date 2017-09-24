package com.bjxc.supervise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.DeviceImage;

public interface DeviceImageMapper {
	
	@Insert("insert into tdeviceimage(deviceId,imageNum,createtime) values(#{deviceId},#{imageNum},now())")
	void init(DeviceImage deviceImage);
	
	Integer add(DeviceImage deviceImage);
	
	@Select("select * from tdeviceimage where imageNum=#{imageNum} limit 1")
	DeviceImage getDeviceImageById(String imageNum);
	
	@Update("update tdeviceimage set trainingRecordId=#{recordId} where id=#{id}")
	void updateTrainingRecordId(@Param("id")int id,@Param("recordId")int recordId);
	
	@Select("select tdeviceimage.imageNum from tdeviceimage order by imageNum desc limit 1")
	String getLastPhotoNum();
	
	@Update("update tdeviceimage set objectId=#{objectId},uploadType=#{uploadType},channelNo=#{channelNo},imageWidth=#{imageWidth},imageHeight=#{imageHeight},event=#{event},lessonId=#{lessonId},locationId=#{locationId},faceProbability=#{faceProbability},packageNum=#{packageNum},dataSize=#{dataSize} where id=#{id}")
	void update(DeviceImage deviceImage);
	
	@Update("update tdeviceimage set imageUrl=#{url},isUpload=1 where imageNum=#{photoNum} and deviceId=#{deviceId} order by id desc limit 1")
	void updateUrl(@Param("photoNum")int photoNum,@Param("deviceId")int deviceId,@Param("url")String url);

	@Select("select * from tdeviceimage where trainingRecordId=#{trainingRecordId} order by createtime asc")
	List <DeviceImage> getDeviceImage(@Param("trainingRecordId")Integer trainingRecordId);

	@Select("select * from tdeviceimage where imageNum=#{photoNum} and deviceId = #{deviceId} order by id desc")
	List<DeviceImage> findByNum(@Param("photoNum")String photoNum, @Param("deviceId")Integer deviceId);
	

}
