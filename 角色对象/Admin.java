package com.rong.entity;

//管理员 Admin    From Wang Rong 
public class Admin {
	private Integer Id;// 管理员ID
	private String userName;// 管理员账户
	private String pwd;// 管理员登录密码
	private String adminName;// 管理员名称

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

}
