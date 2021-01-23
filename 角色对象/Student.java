package com.rong.entity;

//学生实体 Student    From Wang Rong 
public class Student {
	private Integer stuId;// 学生ID
	private String stuName;// 学生姓名
	private String stuNo;// 学生学号
	private String stuPwd;// 学生登录密码

	private Integer score;// 提交学生成绩时查询的成绩

	public Integer getStuId() {
		return stuId;
	}

	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}

	public String getStuPwd() {
		return stuPwd;
	}

	public void setStuPwd(String stuPwd) {
		this.stuPwd = stuPwd;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
