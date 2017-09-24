package com.bjxc.supervise.service;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.bjxc.model.Coach;
import com.bjxc.supervise.mapper.CoachMapper;

@Service
public class CoachService {
	
	@Resource
	private CoachMapper coachMapper;
	
	public Coach getCoachById(Integer id) {
		return coachMapper.getCoachById(id);
	}
	
	public Coach getCoachByNum(String coachnum){
		return coachMapper.getCoachByNum(coachnum);
	}

	public void updateTCPLogin(String coachnum,Date date) {
		coachMapper.updateTCPLogin(coachnum,date);
	}
	
	public void updateTCPLogout(String coachnum,Date date) {
		coachMapper.updateTCPLogout(coachnum,date);
	}
}
