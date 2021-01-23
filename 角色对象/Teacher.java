package com.rong.entity;

//老师实体 Teacher    From Wang Rong 
public class Teacher {

	private Integer tId;// 老师ID
	private String tName;// 老师姓名
	private String tuserName;// 老师账户名称
	private String pwd;// 老师登录密码

	public Integer gettId() {
		return tId;
	}

	public void settId(Integer tId) {
		this.tId = tId;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String getTuserName() {
		return tuserName;
	}

	public void setTuserName(String tuserName) {
		this.tuserName = tuserName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

}
