package com.rong.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.rong.entity.Course;
import com.rong.entity.Teacher;
import com.rong.utils.PageInfo;
import com.rong.utils.PropertiesUtils;

/**
 * MengYouQingChun 课程相关的方法
 */
//From Wang Rong
public class CourseDao {
	// 增加
	public void add(Course course) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "insert into course(cName,tId) values(?,?)";
		queryRunner.update(sql, course.getcName(), course.getTeacher().gettId());
	}

	// 删除
	public void delete(Integer cId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "delete from course where cId = ?";
		queryRunner.update(sql, cId);

	}

	// 修改
	public void update(Course course) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update course set cName = ?,tId = ? where cId = ?";
		queryRunner.update(sql, course.getcName(), course.getTeacher().gettId(), course.getcId());

	}

	// 查找
	public PageInfo<Course> list(Course course, PageInfo<Course> pageInfo) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String _sql = "";
		List<Object> _list = new ArrayList<Object>();
		if (course != null && course.getcId() != null) {
			_sql += " and CID = ?";
			_list.add(course.getcId());
		}
		if (course != null && StringUtils.isNoneBlank(course.getcName())) {
			_sql += " and CNAME like ?";
			_list.add("%" + course.getcName() + "%");
		}
		if (course != null && StringUtils.isNoneBlank(course.getTeacher().gettName())) {
			_sql += " and TNAME like ?";
			_list.add("%" + course.getTeacher().gettName() + "%");
		}
		if (course != null && StringUtils.isNoneBlank(course.getTeacher().getTuserName())) {
			_sql += " and TUSERNAME like ?";
			_list.add("%" + course.getTeacher().getTuserName() + "%");
		}
		if (course != null && course.getTeacher().gettId() != null) {
			_sql += " and course.tId =?";
			_list.add(course.getTeacher().gettId());
		}
		// list转化数组
		Object[] arr = new Object[_list.size()];
		for (int i = 0; i < _list.size(); i++) {
			arr[i] = _list.get(i);
		}

		String sql = "select course.*,teacher.tname,teacher.tusername from course,teacher where course.tid=teacher.tId"
				+ _sql + " limit " + (pageInfo.getPageNo() - 1) * pageInfo.getPageSize() + " , "
				+ pageInfo.getPageSize();
//		List<Course> list = queryRunner.query(sql, new BeanListHandler<>(Course.class), arr);

		List<Map<String, Object>> Maplist = queryRunner.query(sql, new MapListHandler(), arr);
		List<Course> list = new ArrayList<>();

		for (Map map : Maplist) {
			Course entity = new Course();
			entity.setcId(Integer.parseInt(map.get("cId") + ""));
			entity.setcName(map.get("cName") + "");
			Teacher teacher = new Teacher();
			teacher.settId(Integer.parseInt(map.get("tId") + ""));
			teacher.settName(map.get("tName") + "");
			teacher.setTuserName(map.get("tuserName") + "");
			entity.setTeacher(teacher);
			list.add(entity);
		}
		pageInfo.setList(list);
		pageInfo.setTotalCount(this.count(course));
		return pageInfo;
	}

	public Long count(Course course) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String _sql = "";
		List<Object> _list = new ArrayList<Object>();
		if (course != null && course.getcId() != null) {
			_sql += " and CID = ?";
			_list.add(course.getcId());
		}
		if (course != null && StringUtils.isNoneBlank(course.getcName())) {
			_sql += " and CNAME like ?";
			_list.add("%" + course.getcName() + "%");
		}
		if (course != null && StringUtils.isNoneBlank(course.getTeacher().gettName())) {
			// 使用下面这个不能判断“”，出现课程管理页面无法分页跳转的bug
			// if(course != null && course.getTeacher().gettName() != null) {
			_sql += " and TNAME = ?";
			_list.add(course.getTeacher().gettName());
		}
		if (course != null && course.getTeacher().gettId() != null) {
			_sql += " and course.tId = ?";
			_list.add(course.getTeacher().gettId());
		}
		// list转化数组
		Object[] arr = new Object[_list.size()];
		for (int i = 0; i < _list.size(); i++) {
			arr[i] = _list.get(i);
		}

		String sql = "select count(*) from course,teacher where course.tid=teacher.tId" + _sql;
		Long count = (Long) queryRunner.query(sql, new ScalarHandler(), arr);
		return count;
	}

	// 按照ID查找
	public Course findById(Integer cId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from course where cId = ?";
		Map<String, Object> map = queryRunner.query(sql, new MapHandler(), cId);
		Course course = new Course();
		course.setcId(Integer.parseInt(map.get("cId") + ""));
		course.setcName(map.get("cName") + "");
		// 二次查询老师信息，当修改课程信息时，默认显示初始的课程信息，然后再进行修改
		Integer tid = Integer.parseInt(map.get("tId") + "");
		Teacher teacher = DaoFactory.getInstance().getTeacherDao().findById(tid);
		course.setTeacher(teacher);
		return course;
	}
}
