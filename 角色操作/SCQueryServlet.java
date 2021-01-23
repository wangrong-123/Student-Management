//查询方法的调用，以实现各种查询
//然后把查询结果输出到浏览器jsp页面展示给用户
/**
 * From:Wang Rong
 */
package com.rong.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rong.dao.DaoFactory;
import com.rong.entity.Student;
import com.rong.entity.Teacher;

@WebServlet("/scquery")
public class SCQueryServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if ("query_range".equals(method)) {
			this.query_range(request, response);// 跳转
		} else if ("query_jgl".equals(method)) {
			this.query_jgl(request, response);// 跳转
		} else if ("query_teacher".equals(method)) {
			this.query_teacher(request, response);// 跳转
		} else if ("stuselect".equals(method)) {
			this.stuselect(request, response);// 跳转
		}
	}

	// 学生成绩查询
	public void stuselect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Student student = (Student) request.getSession().getAttribute("user");// 获取当前用户
		try {
			List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_cjByTid(student.getStuId());// 当前学生stuId进行查询
			request.setAttribute("list3", list);// 成绩查询获取为list3
			request.getRequestDispatcher("page/sc/stuselect.jsp").forward(request, response);// 成绩查询输出定向到stuselect.jsp页面显示
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 老师查询：及格率、成绩区间分布
	public void query_teacher(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Teacher teacher = (Teacher) request.getSession().getAttribute("user");// 获取当前用户
		try {
			List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_rangeByTid(teacher.gettId());
			request.setAttribute("list1", list);
			List<Map<String, Object>> list2 = DaoFactory.getInstance().getScDao().query_jglByTid(teacher.gettId());
			request.setAttribute("list2", list2);
			request.getRequestDispatcher("page/sc/query_teacher.jsp").forward(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 及格率查询
	public void query_jgl(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_jgl();
			request.setAttribute("list", list);
			request.getRequestDispatcher("page/sc/query_jgl.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 成绩区间查询
	public void query_range(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<Map<String, Object>> list = DaoFactory.getInstance().getScDao().query_range();
			request.setAttribute("list", list);
			request.getRequestDispatcher("page/sc/query_range.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
