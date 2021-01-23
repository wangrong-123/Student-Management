package com.rong.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.rong.entity.Sc;
import com.rong.entity.Student;
import com.rong.utils.PropertiesUtils;

/**
 * 各种方法的聚集 选课
 *
 */
//From Wang Rong
public class ScDao {
	// 增加
	public int[] add(List<Integer> cIdArray, Integer stuId) throws SQLException {
		DataSource dataSource = PropertiesUtils.getDataSource();
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false);
		QueryRunner queryRunner = new QueryRunner(dataSource);
		// 先删除，后更新最近一次的选课提交，避免最近一次提交无法覆盖上一次的提交
		String _sql = "delete from sc where stuId = ?";
		queryRunner.update(connection, _sql, stuId);
		Object[][] object = new Object[cIdArray.size()][2];
		// 将cIdArray,stuId保存为二维数组
		for (int i = 0; i < cIdArray.size(); i++) {
			object[i][0] = stuId;
			object[i][1] = cIdArray.get(i);
		}
		String sql = "insert into sc(stuId,cId) values(?,?)";
		int[] arr = queryRunner.batch(connection, sql, object);
		connection.commit();
		return arr;
	}

	// 删除
	public void delete(Integer scId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "delete from sc where scId = ?";
		queryRunner.update(sql, scId);

	}

	// 按照ID序号修改密码
	public void update(String[] stuIdArr, String[] scoreArr, Integer cId) throws SQLException {
		DataSource dataSource = PropertiesUtils.getDataSource();
		Connection connection = dataSource.getConnection();
		connection.setAutoCommit(false);

		QueryRunner queryRunner = new QueryRunner(dataSource);
		Object[][] objects = new Object[stuIdArr.length][3];
		for (int i = 0; i < stuIdArr.length; i++) {
			objects[i][0] = Integer.parseInt(scoreArr[i] == null ? "0" : scoreArr[i]);
			objects[i][1] = cId;
			objects[i][2] = Integer.parseInt(stuIdArr[i]);
		}
		String sql = "update sc set score = ? where cId = ? and stuId = ?";
		queryRunner.batch(sql, objects);
		connection.commit();

	}

	// 查询
	public List<Sc> list(Sc sc) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from sc";
		List<Sc> list = queryRunner.query(sql, new BeanListHandler<>(Sc.class));

		return list;
	}

	// 选课时判断是否已选课程的查询
	public List<Sc> listByStuId(Integer stuId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from sc where stuId = ?";
		List<Sc> list = queryRunner.query(sql, new BeanListHandler<>(Sc.class), stuId);

		return list;
	}

	// 按照ID查找
	public Sc findById(Integer scId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from sc where scId = ?";
		Sc sc = queryRunner.query(sql, new BeanHandler<>(Sc.class), scId);

		return sc;
	}

	// 选课打分时，关联查询学生
	public List<Student> listStudentByCId(Integer cId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select student.*,score from sc,student where sc.stuId = student.stuId and  cId = ?";
		List<Student> list = queryRunner.query(sql, new BeanListHandler<>(Student.class), cId);
		return list;
	}

	// 管理员成绩区间查询的方法
	public List<Map<String, Object>> query_range() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select course.cid,cname,ifnull(bad,0) bad,ifnull(common,0) common,ifnull(good,0) good,ifnull(best,0) best"
				+ " from course" + " left join (" + " select cid,count(*) bad from sc where score<60 group by cid"
				+ " ) A on course.cid = A.cid" + " left join ("
				+ " select cid,count(*) common from sc where score>=60 and score<=70 group by cid"
				+ " ) B on  course.cid = B.cid" + " left join("
				+ " select cid,count(*) good from sc where score>70 and score<=85 group by cid"
				+ " ) C on course.cid = C.cid" + " left join ("
				+ " select cid,count(*) best from sc where score>85 and score<=100 group by cid"
				+ " ) D on course.cid =D.cid ";

		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());
		return list;
	}

	// 老师查询区间分布
	public List<Map<String, Object>> query_rangeByTid(Integer tId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select course.cid,cname,ifnull(bad,0) bad,ifnull(common,0) common,ifnull(good,0) good,ifnull(best,0) best"
				+ " from course" + " left join (" + " select cid,count(*) bad from sc where score<60 group by cid"
				+ " ) A on course.cid = A.cid" + " left join ("
				+ " select cid,count(*) common from sc where score>=60 and score<=70 group by cid"
				+ " ) B on  course.cid = B.cid" + " left join("
				+ " select cid,count(*) good from sc where score>70 and score<=85 group by cid"
				+ " ) C on course.cid = C.cid" + " left join ("
				+ " select cid,count(*) best from sc where score>85 and score<=100 group by cid"
				+ " ) D on course.cid =D.cid where tid = ?";

		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), tId);
		return list;
	}

	// 管理员及格率查询方法
	public List<Map<String, Object>> query_jgl() throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select A.cid,(" + " select cname from course where A.cid = course.cid "
				+ " ) cname,jgnum,allnum,round(jgnum/allnum,2)*100 jgl from ("
				+ " select cid, count(*) jgnum from sc where score>=60 group by cid " + " ) A,("
				+ " select cid, count(*) allnum from sc group by cid " + " ) B where A.cid = B.cid ";
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler());
		return list;
	}

	// 老师及格率查询
	public List<Map<String, Object>> query_jglByTid(Integer tId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select * from course,(" + " select A.cid,jgnum,allnum,round(jgnum/allnum,2)*100 jgl from ("
				+ " select cid, count(*) jgnum from sc where score>=60 group by cid " + " ) A,("
				+ " select cid, count(*) allnum from sc group by cid " + " ) B where A.cid = B.cid"
				+ " ) C where course.cid = C.cid " + " and tid = ?";
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), tId);
		return list;
	}

	// 学生成绩查询
	public List<Map<String, Object>> query_cjByTid(Integer stuId) throws SQLException {
		QueryRunner queryRunner = new QueryRunner(PropertiesUtils.getDataSource());
		String sql = "select sc.cId,cName,tName,score from sc,course,teacher where sc.cId=course.cId and teacher.tId=course.tId and stuId = ?";
		List<Map<String, Object>> list = queryRunner.query(sql, new MapListHandler(), stuId);
		return list;
	}

}
