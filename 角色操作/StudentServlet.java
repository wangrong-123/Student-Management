//接受请求的信息
//学生相关的方法调用，涉及到删除查询等操作
/**
 * From:Wang Rong
 */
package com.rong.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.rong.dao.DaoFactory;
import com.rong.entity.Student;
import com.rong.utils.MD5;
import com.rong.utils.PageInfo;
import com.rong.utils.PathUtils;

@WebServlet("/student")
public class StudentServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String method = request.getParameter("method");
		if ("list".equals(method)) {
			this.list(request, response);
		} else if ("add".equals(method)) {
			this.add(request, response);
		} else if ("edit".equals(method)) {
			this.findById(request, response);
		} else if ("editsubmit".equals(method)) {
			this.editsubmit(request, response);
		} else if ("delete".equals(method)) {
			this.delete(request, response);
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			DaoFactory.getInstance().getStudentDao().delete(Integer.parseInt(id));
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "student?method=list");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void editsubmit(HttpServletRequest request, HttpServletResponse response) {
		Integer stuId = Integer.parseInt(request.getParameter("stuId"));
		String stuNo = request.getParameter("stuNo");
		String stuName = request.getParameter("stuName");
		Student student = new Student();
		student.setStuName(stuName);
		student.setStuNo(stuNo);
		student.setStuId(stuId);
		try {
			DaoFactory.getInstance().getStudentDao().update(student);
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "student?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void findById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			Student student = DaoFactory.getInstance().getStudentDao().findById(Integer.parseInt(id));
			request.setAttribute("student", student);
			request.getRequestDispatcher("page/student/update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		String stuNo = request.getParameter("stuNo");
		String stuName = request.getParameter("stuName");
		String stuPwd = request.getParameter("stuPwd");
		Student student = new Student();
		student.setStuName(stuName);
		student.setStuNo(stuNo);
		student.setStuPwd(MD5.encryByMd5(MD5.encryByMd5(stuPwd)));
		try {
			DaoFactory.getInstance().getStudentDao().add(student);
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "student?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void list(HttpServletRequest request, HttpServletResponse response) {
		// 当前页
		Integer pageNo = getIntParameter(request, "pageNo");
		Integer stuId = getIntParameter(request, "stuId");
		String stuName = request.getParameter("stuName");
		String stuNo = request.getParameter("stuNo");

		Student student = new Student();
		student.setStuId(stuId);
		student.setStuName(stuName);
		student.setStuNo(stuNo);

		PageInfo<Student> pageInfo = new PageInfo<>(pageNo);
		try {
			pageInfo = DaoFactory.getInstance().getStudentDao().list(student, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			request.setAttribute("pageInfo", pageInfo);
			request.setAttribute("student", student);
			request.getRequestDispatcher("page/student/list.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public Integer getIntParameter(HttpServletRequest request, String name) {
		if (StringUtils.isNoneBlank(request.getParameter(name))) {
			return Integer.parseInt(request.getParameter(name));
		} else {
			return null;
		}
	}

}
