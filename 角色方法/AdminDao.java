package com.rong.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.rong.entity.Admin;
import com.rong.utils.PropertiesUtils;

/**
 * MengYouQingChun 管理员方法
 */
//From Wang Rong
public class AdminDao {

	// 增加
	public void add(Admin admin) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "insert into admin(userName,pwd,adminName) values(?,?,?)";
		queryRunner.update(sql, admin.getUserName(), admin.getPwd(), admin.getAdminName());
	}

	// 删除
	public void delete(Integer Id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "delete from admin where Id = ?";
		queryRunner.update(sql, Id);

	}

	// 修改
	public void update(Admin admin) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "update admin set userName = ?,pwd = ?,adminName = ? where Id = ?";
		queryRunner.update(sql, admin.getUserName(), admin.getPwd(), admin.getAdminName(), admin.getId());

	}

	// 查找
	public List<Admin> list(Admin admin) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from admin";
		List<Admin> list = queryRunner.query(sql, new BeanListHandler<>(Admin.class));

		return list;
	}

	// 按照ID查找
	public Admin findById(Integer Id) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from admin where Id = ?";
		Admin admin = queryRunner.query(sql, new BeanHandler<>(Admin.class), Id);

		return admin;
	}

	public Admin login(Admin admin) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from admin where userName = ? and pwd = ?";
		Admin entity = queryRunner.query(sql, new BeanHandler<>(Admin.class), admin.getUserName(), admin.getPwd());

		return entity;
	}

}
