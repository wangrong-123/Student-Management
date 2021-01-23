package com.rong.dao;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DaoFactory方法，管理员，学生等实体的dao连接
 * 
 * @author Wang Rong
 *
 */
public class DaoFactory {
	private static DaoFactory factory = new DaoFactory();

	private Map<String, Object> map = new ConcurrentHashMap<>();

	private DaoFactory() {

	}

	public static DaoFactory getInstance() {
		return factory;
	}

	// 管理员
	public AdminDao getAdminDao() {
		AdminDao dao = (AdminDao) map.get("AdminDao");
		if (dao != null) {
			return dao;
		} else {
			dao = new AdminDao();
			map.put("AdminDao", dao);
		}
		return dao;
	}

	// 学生
	public StudentDao getStudentDao() {
		StudentDao dao = (StudentDao) map.get("StudentDao");
		if (dao != null) {
			return dao;
		} else {
			dao = new StudentDao();
			map.put("StudentDao", dao);
		}
		return dao;
	}

	// 课程
	public CourseDao getCourseDao() {
		CourseDao dao = (CourseDao) map.get("CourseDao");
		if (dao != null) {
			return dao;
		} else {
			dao = new CourseDao();
			map.put("CourseDao", dao);
		}
		return dao;
	}

	// 选课
	public ScDao getScDao() {
		ScDao dao = (ScDao) map.get("ScDao");
		if (dao != null) {
			return dao;
		} else {
			dao = new ScDao();
			map.put("ScDao", dao);
		}
		return dao;
	}

	// 老师
	public TeacherDao getTeacherDao() {
		TeacherDao dao = (TeacherDao) map.get("TeacherDao");
		if (dao != null) {
			return dao;
		} else {
			dao = new TeacherDao();
			map.put("TeacherDao", dao);
		}
		return dao;
	}

	public static void main(String[] args) {
		System.out.println("调用了DaoFactory方法");
		System.out.println(DaoFactory.getInstance().getStudentDao());
		System.out.println(DaoFactory.getInstance().getStudentDao());

	}
}