<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>选课</title>
		<link rel="stylesheet" href="${basePath}static/css/styles.css" />
		<link rel="stylesheet" href="${basePath}static/css/font-awesome-4.7.0/css/font-awesome.min.css" />
		<script src="${basePath}static/js/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript">

		</script>
	</head>
	<body>
		<c:if test="${param.msg ==0}">
			<span style="color:red;">抱歉，选课失败，请重试！</span>
		</c:if>
		<c:if test="${param.msg ==1}">
			<span style="color:green;">恭喜你，选课成功！</span>
		</c:if>
		<form action="${basePath}sc?method=submit" method="post">
			<table class="tablelist">
				<c:forEach items="${course}" var="course">
				<tr>
					<td width="70px" align="center">
						<!-- 选中的cId -->
						<input type="checkbox"
							<c:forEach items="${scs}" var="sc">
								<c:if test="${sc.cId eq course.cId}">checked="checked"</c:if>
							</c:forEach>
						  name="cId" value="${course.cId}">
					</td>
					<td>
					${course.cName}
					</td>
					<td>
					${course.teacher.tName}
					</td>
				</tr>
				</c:forEach>
			</table>
			<br/>
			<p><font color="red">梦游@青春 提示：请您在选课阶段选课！</font></p>
			<br/>

 			<!--  以下注释部分为添加是否已选课的判断，若已选课，则不能再选课。
			<c:if test="${scs != null}">
				<span style="color:green;">梦游@青春提示您：您已经选过课程了！</span>
				<br/>
				<br/>
				<button class="mybtn" disabled="disabled">
					<i class="fa fa-save"></i>
					提交选课
				</button>
				<!--  <span style="color:green;">梦游@青春提示您：您已经选过课程了！</span> -->
		<!--	</c:if>
			-->

			<!--
			<c:if test="${scs == null}">
				<!--<span style="color:green;">梦游@青春提示您：您只有一次选课机会，请谨慎提交！</span>-->
		<!--		<button class="mybtn">
					<i class="fa fa-save"></i>
					提交选课
				</button>
			</c:if>
			-->

			
			<button class="mybtn">
				<i class="fa fa-save"></i>
				提交选课
			</button>
			
		</form>
		
	</body>
</html>
