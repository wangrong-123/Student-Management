package com.rong.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import com.rong.dao.DaoFactory;
import com.rong.entity.Admin;
import com.rong.entity.Student;
import com.rong.entity.Teacher;
import com.rong.utils.MD5;

/**
 * 学生、老师、管理员等登录验证
 * 
 * From :Wang Rong
 *
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String type = req.getParameter("type");

		if (StringUtils.isBlank(userName) || StringUtils.isBlank(password) || StringUtils.isBlank(type)) {
			req.setAttribute("error", "输入的信息不能为空！");
			req.getRequestDispatcher("login.jsp").forward(req, resp);
			return;
		}

		HttpSession session = req.getSession();

		// 判断空值，空行等等
		if (StringUtils.isNotBlank(type)) {
			try {
				if ("0".equals(type)) {
					// 学生登录验证
					Student student = DaoFactory.getInstance().getStudentDao().login(userName,
							MD5.encryByMd5(MD5.encryByMd5(password)));
					if (student != null) {
						session.setAttribute("user", student);
						session.setAttribute("type", type);
						resp.sendRedirect("index.jsp");// 跳转

					} else {
						// 用户名或者密码错误
						req.setAttribute("error", "您的用户名或者密码错误！");
						req.getRequestDispatcher("login.jsp").forward(req, resp);

					}

				} else if ("1".equals(type)) {
					// 老师登录验证
					Teacher teacher = DaoFactory.getInstance().getTeacherDao().login(userName,
							MD5.encryByMd5(MD5.encryByMd5(password)));
					if (teacher != null) {
						session.setAttribute("user", teacher);
						session.setAttribute("type", type);
						resp.sendRedirect("index.jsp");// 跳转

					} else {
						// 用户名或者密码错误
						req.setAttribute("error", "您的用户名或者密码错误！");
						req.getRequestDispatcher("login.jsp").forward(req, resp);

					}

				} else {
					// 管理员登录验证
					Admin admin = new Admin();
					admin.setUserName(userName);
					admin.setPwd(MD5.encryByMd5(MD5.encryByMd5(password)));
					Admin entity = DaoFactory.getInstance().getAdminDao().login(admin);
					if (entity != null) {
						// 执行跳转
						session.setAttribute("user", entity);
						session.setAttribute("type", type);
						resp.sendRedirect("index.jsp");// 跳转
					} else {
						// 用户名或者密码错误
						req.setAttribute("error", "您的用户名或者密码错误！");
						req.getRequestDispatcher("login.jsp").forward(req, resp);
					}

				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

		}

	}

}
