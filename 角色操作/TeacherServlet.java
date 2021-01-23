//接受请求的信息
//老师相关的方法调用，涉及到删除查询等操作

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
import com.rong.entity.Teacher;
import com.rong.utils.MD5;
import com.rong.utils.PageInfo;
import com.rong.utils.PathUtils;

@WebServlet("/teacher")
public class TeacherServlet extends HttpServlet {
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
			DaoFactory.getInstance().getTeacherDao().delete(Integer.parseInt(id));
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "teacher?method=list");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void editsubmit(HttpServletRequest request, HttpServletResponse response) {
		Integer tId = Integer.parseInt(request.getParameter("tId"));
		String tName = request.getParameter("tName");
		String tuserName = request.getParameter("tuserName");
		Teacher teacher = new Teacher();
		teacher.settId(tId);
		teacher.settName(tName);
		teacher.setTuserName(tuserName);
		try {
			DaoFactory.getInstance().getTeacherDao().update(teacher);
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "teacher?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void findById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		try {
			Teacher teacher = DaoFactory.getInstance().getTeacherDao().findById(Integer.parseInt(id));
			request.setAttribute("teacher", teacher);
			request.getRequestDispatcher("page/teacher/update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		String tName = request.getParameter("tName");
		String tuserName = request.getParameter("tuserName");
		String pwd = request.getParameter("pwd");
		Teacher teacher = new Teacher();
		teacher.setTuserName(tuserName);
		teacher.settName(tName);
		teacher.setPwd(MD5.encryByMd5(MD5.encryByMd5(pwd)));
		try {
			DaoFactory.getInstance().getTeacherDao().add(teacher);
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "teacher?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void list(HttpServletRequest request, HttpServletResponse response) {
		// 当前页码
		Integer pageNo = getIntParameter(request, "pageNo");
		Integer tId = getIntParameter(request, "tId");
		String tName = request.getParameter("tName");
		String tuserName = request.getParameter("tuserName");

		Teacher teacher = new Teacher();
		teacher.setTuserName(tuserName);
		teacher.settName(tName);
		teacher.settId(tId);

		PageInfo<Teacher> pageInfo = new PageInfo<>(pageNo);
		try {
			pageInfo = DaoFactory.getInstance().getTeacherDao().list(teacher, pageInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			request.setAttribute("pageInfo", pageInfo);
			// 回到页面
			request.setAttribute("teacher", teacher);
			request.getRequestDispatcher("page/teacher/list.jsp").forward(request, response);
		} catch (Exception e) {
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
