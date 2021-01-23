//接受请求的信息

/**关于课程的相关方法调用，增加，删除，查询等
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
import com.rong.entity.Course;
import com.rong.entity.Teacher;
import com.rong.utils.PageInfo;
import com.rong.utils.PathUtils;

@WebServlet("/course")
public class CourseServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if ("list".equals(method)) {
			this.list(request, response);
		} else if ("add".equals(method)) {
			this.add(request, response);
		} else if ("v_add".equals(method)) {
			this.v_add(request, response);
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
			DaoFactory.getInstance().getCourseDao().delete(Integer.parseInt(id));
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "course?method=list");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void editsubmit(HttpServletRequest request, HttpServletResponse response) {
		String cName = request.getParameter("cName");
		Integer cId = getIntParameter(request, "cId");
		Integer tId = Integer.parseInt(request.getParameter("tId"));
		Course course = new Course();
		course.setcId(cId);
		course.setcName(cName);
		Teacher teacher = new Teacher();
		teacher.settId(tId);
		course.setTeacher(teacher);
		try {
			DaoFactory.getInstance().getCourseDao().update(course);
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "course?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void findById(HttpServletRequest request, HttpServletResponse response) {
		String id = request.getParameter("id");
		PageInfo<Teacher> pageInfo = new PageInfo<>(1);
		pageInfo.setPageSize(10000);

		try {
			Course course = DaoFactory.getInstance().getCourseDao().findById(Integer.parseInt(id));
			pageInfo = DaoFactory.getInstance().getTeacherDao().list(null, pageInfo);
			request.setAttribute("course", course);
			request.setAttribute("teacher", pageInfo.getList());
			request.getRequestDispatcher("page/course/update.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void v_add(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		PageInfo<Teacher> pageInfo = new PageInfo<>(1);
		pageInfo.setPageSize(10000);
		try {
			pageInfo = DaoFactory.getInstance().getTeacherDao().list(null, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("teachers", pageInfo.getList());
		request.getRequestDispatcher("page/course/add.jsp").forward(request, response);

	}

	private void add(HttpServletRequest request, HttpServletResponse response) {
		String cName = request.getParameter("cName");
		Integer tId = Integer.parseInt(request.getParameter("tId"));
		Course course = new Course();
		course.setcName(cName);
		Teacher teacher = new Teacher();
		teacher.settId(tId);
		course.setTeacher(teacher);
		try {
			DaoFactory.getInstance().getCourseDao().add(course);
			// 重新定向到列表页面
			response.sendRedirect(PathUtils.getBasePath(request) + "course?method=list");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void list(HttpServletRequest request, HttpServletResponse response) {
		// 当前页码
		Integer pageNo = getIntParameter(request, "pageNo");
		// 查询的条件
		Integer cId = getIntParameter(request, "cId");
		String cName = request.getParameter("cName");
		String tName = request.getParameter("tName");
		String tuserName = request.getParameter("tuserName");

		Course course = new Course();
		course.setcId(cId);
		course.setcName(cName);
		Teacher teacher = new Teacher();
		teacher.settName(tName);
		teacher.setTuserName(tuserName);
		course.setTeacher(teacher);

		PageInfo<Course> pageInfo = new PageInfo<>(pageNo);
		try {
			pageInfo.setTotalCount(DaoFactory.getInstance().getCourseDao().count(course));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			pageInfo = DaoFactory.getInstance().getCourseDao().list(course, pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			request.setAttribute("pageInfo", pageInfo);
			// 回到页面
			request.setAttribute("course", course);
			request.getRequestDispatcher("page/course/list.jsp").forward(request, response);
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
