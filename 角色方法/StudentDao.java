package com.rong.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import com.rong.entity.Student;
import com.rong.utils.PageInfo;
import com.rong.utils.PropertiesUtils;

/**
 * MengYouQingChun 学生包含的方法
 */
//From Wang Rong
public class StudentDao {
	// 增加
	public void add(Student student) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "insert into student(stuName,stuNo,stuPwd) values(?,?,?)";
		queryRunner.update(sql, student.getStuName(), student.getStuNo(), student.getStuPwd());
	}

	// 删除
	public void delete(Integer stuId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "delete from student where stuId = ?";
		queryRunner.update(sql, stuId);

	}

	// 修改
	public void update(Student student) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update student set stuName = ?,stuNo = ? where stuId = ?";
		queryRunner.update(sql, student.getStuName(), student.getStuNo(), student.getStuId());

	}

	// 按照ID序号修改密码
	public void update(String pwd, Integer stuId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update  student set stuPwd = ? where stuId = ? ";
		queryRunner.update(sql, pwd, stuId);
	}

	// 分页
	public PageInfo<Student> list(Student student, PageInfo<Student> pageInfo) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String _sql = "";
		List<Object> _list = new ArrayList<Object>();
		if (student.getStuId() != null) {
			_sql += " and STUID = ?";
			_list.add(student.getStuId());
		}
		if (StringUtils.isNoneBlank(student.getStuName())) {
			_sql += " and STUNAME like ?";
			_list.add("%" + student.getStuName() + "%");
		}
		if (StringUtils.isNoneBlank(student.getStuNo())) {
			_sql += " and STUNO like ?";
			_list.add("%" + student.getStuNo() + "%");
		}
		// list转化数组
		Object[] arr = new Object[_list.size()];
		for (int i = 0; i < _list.size(); i++) {
			arr[i] = _list.get(i);
		}

		String sql = "select * from student where 1=1 " + _sql + " limit  "
				+ (pageInfo.getPageNo() - 1) * pageInfo.getPageSize() + " , " + pageInfo.getPageSize();
		System.out.println("调用了StudentDao方法！");
		System.out.println(sql);
		System.out.println(Arrays.toString(arr));

		List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class), arr);

		pageInfo.setList(list);
		pageInfo.setTotalCount(this.count(student));
		return pageInfo;

	}

	public Long count(Student student) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String _sql = "";
		List<Object> _list = new ArrayList<Object>();
		if (student.getStuId() != null) {
			_sql += " and STUID = ?";
			_list.add(student.getStuId());
		}
		if (StringUtils.isNoneBlank(student.getStuName())) {
			_sql += " and STUNAME like ?";
			_list.add("%" + student.getStuName() + "%");
		}
		if (StringUtils.isNoneBlank(student.getStuNo())) {
			_sql += " and STUNO like ?";
			_list.add("%" + student.getStuNo() + "%");
		}
		// list变数组
		Object[] arr = new Object[_list.size()];
		for (int i = 0; i < _list.size(); i++) {
			arr[i] = _list.get(i);
		}
		String sql = "select count(*) from student where 1=1" + _sql;
		Long count = (Long) queryRunner.query(sql, new ScalarHandler(), arr);
		return count;
	}

	// 查找
//	public List<Student> list(Student student) throws SQLException {
//		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
//		String sql = "select * from student";
//		List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class));
//
//		return list;
//	}

	// 按照ID查找
	public Student findById(Integer stuId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from student where stuId = ?";
		Student student = queryRunner.query(sql, new BeanHandler<>(Student.class), stuId);
		return student;
	}

	// 登录
	public Student login(String stuNo, String stuPwd) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from student where stuNo = ? and stuPwd = ?";
		Student student = queryRunner.query(sql, new BeanHandler<>(Student.class), stuNo, stuPwd);
		return student;
	}

}
