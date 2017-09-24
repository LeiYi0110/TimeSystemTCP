package com.bjxc.supervise.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bjxc.model.DeviceImage;

public interface DeviceImageMapper {
	
	@Insert("insert into t_device_image(device_num,image_num,record_time,create_time) values(#{deviceNum},#{imageNum},#{recordTime},now())")
	void init(DeviceImage deviceImage);
	
	Integer add(DeviceImage deviceImage);
	
//	@Select("select * from t_device_image where image_num=#{imageNum} limit 1")
//	DeviceImage getDeviceImageById(String imageNum);
	
	@Update("update t_device_image set training_record_id=#{recordId} where id=#{id}")
	void updateTrainingRecordId(@Param("id")int id,@Param("recordId")int recordId);
	
	@Select("select t_device_image.image_num from t_device_image order by image_num desc limit 1")
	String getLastPhotoNum();
	
	@Update("update t_device_image set objectNum=#{objectNum},uploadMode=#{uploadMode},channelNo=#{channelNo},imageSize=#{imageSize},event=#{event},courseId=#{courseId},locationId=#{locationId},faceProbability=#{faceProbability},packageNum=#{packageNum},dataSize=#{dataSize} where id=#{id}")
	void update(DeviceImage deviceImage);
	
	@Update("update t_device_image set image_url=#{url},is_upload=1 where image_num=#{photoNum} and device_num=#{deviceNum} order by id desc limit 1")
	void updateUrl(@Param("photoNum")int photoNum,@Param("deviceNum")String deviceNum,@Param("url")String url);

	@Select("select * from t_device_image where training_record_id=#{trainingRecordId} order by create_time asc")
	List <DeviceImage> getDeviceImage(@Param("trainingRecordId")Integer trainingRecordId);

	@Select("select * from t_device_image where image_num=#{photoNum} and device_num = #{deviceNum} order by id desc")
	List<DeviceImage> findByNum(@Param("photoNum")String photoNum, @Param("deviceNum")String deviceNum);
	

}
