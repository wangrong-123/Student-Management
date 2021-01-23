//选课方法的调用，选课操作，老师评分录入成绩等
/**
 * From:Wang Rong
 */
package com.rong.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.rong.dao.DaoFactory;
import com.rong.entity.Course;
import com.rong.entity.Sc;
import com.rong.entity.Student;
import com.rong.entity.Teacher;
import com.rong.utils.PageInfo;
import com.rong.utils.PathUtils;

@WebServlet("/sc")
public class SCServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String method = request.getParameter("method");
		if ("select".equals(method)) {
			this.select(request, response);// 跳转select方法
		} else if ("submit".equals(method)) {
			this.submit(request, response);
		} else if ("tc".equals(method)) {
			this.teacher_course(request, response);
		} else if ("cs".equals(method)) {
			this.course_student(request, response);
		} else if ("score_submit".equals(method)) {
			this.score_submit(request, response);
		}
	}

	// 提交老师的评分
	private void score_submit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer cId = getIntParameter(request, "cId");
		String[] stuIdArr = request.getParameterValues("stuId");
		String[] scoreArr = request.getParameterValues("score");
		try {
			DaoFactory.getInstance().getScDao().update(stuIdArr, scoreArr, cId);
			response.sendRedirect(PathUtils.getBasePath(request) + "sc?method=cs&cId=" + cId + "&msg=1");
		} catch (SQLException e) {
			response.sendRedirect(PathUtils.getBasePath(request) + "sc?method=cs&cId=" + cId + "&msg=0");
			e.printStackTrace();
		}
	}

	// 选课的学生查询
	private void course_student(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1、 获取cid的参数
		Integer cId = getIntParameter(request, "cId");
		// 2、调用ScDao的listStudentByCId方法获取学生列表
		try {
			List<Student> list = DaoFactory.getInstance().getScDao().listStudentByCId(cId);
			request.setAttribute("list", list);
			request.setAttribute("cId", cId);
			request.getRequestDispatcher("page/sc/course_student.jsp").forward(request, response);// 3、返回页面
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void teacher_course(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 1、获取老师session中的对象tId
		Teacher teacher = (Teacher) request.getSession().getAttribute("user");
		Integer pageNo = getIntParameter(request, "pageNo");
		Course course = new Course();
		course.setTeacher(teacher);
		PageInfo<Course> pageInfo = new PageInfo<>(pageNo);
		// 2、 查询课程列表
		try {
			pageInfo = DaoFactory.getInstance().getCourseDao().list(course, pageInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 3、 将信息返回jsp页面
		request.setAttribute("pageInfo", pageInfo);
		request.getRequestDispatcher("page/sc/teacher_course.jsp").forward(request, response);
	}

	// 选课结果保存
	private void submit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String[] cIds = request.getParameterValues("cId");
		List<Integer> cIdArray = new ArrayList<Integer>();
		for (String string : cIds) {
			cIdArray.add(Integer.parseInt(string));
		}
		Student student = (Student) request.getSession().getAttribute("user");
		try {
			int[] arr = DaoFactory.getInstance().getScDao().add(cIdArray, student.getStuId());
			if (arr.length == 0) {
				// 0表示选课不成功，1表示选课成功，select根据msg是0还是1判断输出的信息
				response.sendRedirect(PathUtils.getBasePath(request) + "sc?method=select&msg=0");
//				request.setAttribute("msg", "选课结果保存不成功");
			} else {
				response.sendRedirect(PathUtils.getBasePath(request) + "sc?method=select&msg=1");
//				request.setAttribute("msg", "选课结果保存成功");
			}
//			request.getRequestDispatcher("page/sc/select.jsp").forward(request, response);
		} catch (SQLException e) {
			response.sendRedirect(PathUtils.getBasePath(request) + "sc?method=select&msg=0");
//			request.setAttribute("msg", "选课结果保存不成功");
//			request.getRequestDispatcher("page/sc/select.jsp").forward(request, response);
		}

	}

	// 选课
	private void select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PageInfo<Course> info = new PageInfo<>(1);
		info.setPageSize(2000);// 最大的课程数量为2000
		try {
			info = DaoFactory.getInstance().getCourseDao().list(null, info);//
			// 获取已登录学生信息
			Student student = (Student) request.getSession().getAttribute("user");
			// 获取已选课程的ID
			List<Sc> list = DaoFactory.getInstance().getScDao().listByStuId(student.getStuId());
			request.setAttribute("scs", list);
			request.setAttribute("course", info.getList());
			request.getRequestDispatcher("page/sc/select.jsp").forward(request, response);
		} catch (SQLException e) {
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
