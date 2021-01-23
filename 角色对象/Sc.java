package com.rong.entity;

//选课表 Sc    From Wang Rong 
public class Sc {
	private Integer scId;// 选课ID
	private Integer stuId;// 学生ID
	private Integer cId;// 课程ID
	private Double score;// 课程的分数

	public Integer getScId() {
		return scId;
	}

	public void setScId(Integer scId) {
		this.scId = scId;
	}

	public Integer getStuId() {
		return stuId;
	}

	public void setStuId(Integer stuId) {
		this.stuId = stuId;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

}
