package com.rong.entity;

//课程Course    From Wang Rong 
public class Course {

	private Integer cId;// 课程ID
	private String cName;// 课程名称
	private Teacher teacher;// 课程的授课老师

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}
