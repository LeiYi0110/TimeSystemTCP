package com.bjxc.supervise.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.supervise.mapper.DeviceMapper;
import com.bjxc.supervise.model.Device;

@Service
public class DeviceService {
	
	
	@Resource
	private DeviceMapper deviceMapper;
	
	public void test() {
		
		System.out.println("OK");
		
	}
	
	public Device getDevice(Integer id)
	{
		return deviceMapper.getDevice(id);
	}
	
	public Device getDeviceByImei(String imei)
	{
		Device deviceByImei = deviceMapper.getDeviceByImei(imei);
//		Device device=new Device();
//		device.setImei(imei);
//		device.setDevnum("6106147544950515");
//		device.setInscode("2236581429084424");
//		device.setPasswd("a741Ls0z5h+J");
//		device.setId(1);
//		device.setCarId(46);
//		device.setLicnum("浙E1578学");
//		
//		device.setKey("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");
		return deviceByImei;
	}
	
	public Integer searchDevice(String licnum,String imei,String mobile){
		Integer result=0;		
		if(deviceMapper.checkAssign(licnum,imei,mobile)>0){
			result=5;
			return result;
		}
		if(deviceMapper.checkCar(licnum)>0){				//判断数据库中是否有车
			if(deviceMapper.checkCarSign(licnum)>0){		//检测车是否被注册
				result=1;
				return result;
			}
		}else{
			result=2;
			return result;
		}
		if(deviceMapper.checkDevice(imei)>0){						//判断数据库中是否有终端
			if(deviceMapper.checkDeviceSign(imei,mobile)>0){				//检测终端是否被注册
				result=3;
				return result;
			}
		}else{
			result=4;
			return result;
		}
		return result;
	}

	public void updateTCPLogin(Integer deviceId) {
		deviceMapper.updateTCPLogin(deviceId);
	}
	
	public void updateTCPLogout(Integer deviceId) {
		deviceMapper.updateTCPLogout(deviceId);
	}
}
