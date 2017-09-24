package com.bjxc.supervise.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.bjxc.model.Coach;
import com.bjxc.model.Student;
import com.bjxc.model.StudentTrainSituation;
import com.bjxc.supervise.mapper.StudentMapper;

@Service
public class StudentService {
	
	@Resource
	private StudentMapper studentMapper;
	
	public Student getStudentById(Integer id) {
		return studentMapper.getStudentById(id);
	}
	
	public Student getStudentByNum(String studentnum){
		return studentMapper.getStudentByNum(studentnum);
	}
	
	public void updateTCPLogin(String studentnum,Date date) {
//		studentMapper.updateTCPLogin(studentnum,date);
	}
	
	public void updateTCPLogout(String studentnum,Date date) {
//		studentMapper.updateTCPLogout(studentnum,date);
	}

	public void updateLastestUploadTime(String studentNum, Date date) {
//		studentMapper.updateLastestUploadTime(studentNum,date);
	}
	
	public StudentTrainSituation getStudentTrainSituation(String studentNum){
		return studentMapper.getStudentTrainSituation(studentNum);
	}

}
