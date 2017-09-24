package com.bjxc.supervise.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.bjxc.model.DeviceImage;
import com.bjxc.supervise.mapper.DeviceImageMapper;

@Service
public class DeviceImageService {
	
	@Resource
	private DeviceImageMapper deviceImageMapper;
	
	public void init(DeviceImage deviceImage) {
		deviceImageMapper.init(deviceImage);
	}
	
	public void updateTrainingRecordId(int id,int recordId) {
		deviceImageMapper.updateTrainingRecordId(id,recordId);
	}
	
	public DeviceImage add(DeviceImage deviceImage) {
		deviceImageMapper.add(deviceImage);
		return deviceImage;
	}
	
	public String getLastPhotoNum() {
		String lastPhotoNum = deviceImageMapper.getLastPhotoNum();
		return lastPhotoNum;
	}
	
	public void updateUrl(int photoNum,String deviceNum,String url) {
		deviceImageMapper.updateUrl(photoNum,deviceNum,url);
	}
	
	public List<DeviceImage> getDeviceImage(Integer trainingRecordId){
		return deviceImageMapper.getDeviceImage(trainingRecordId);
	}

	public List<DeviceImage> findByNum(String photoNum, String deviceNum) {
		return deviceImageMapper.findByNum(photoNum,deviceNum);
	}
}
