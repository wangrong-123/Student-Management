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
		<script src="${basePath}static/js/jquery-validation-1.14.0/jquery.validate.js" type="text/javascript"></script>
		<script src="${basePath}static/js/jquery-validation-1.14.0/localization/messages_zh.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$("#addForm").validate({
					rules:{
						score:{
							required:true,
							digits:true,
							min:0,
							max:100
						},
					}
				});
			});
		</script>
	</head>
	<body>
		<c:if test="${param.msg ==0}">
			<span style="color:red;">抱歉，提交失败，请重试！</span>
		</c:if>
		<c:if test="${param.msg ==1}">
			<span style="color:green;">恭喜你，提交成功！</span>
		</c:if>
		<form id="addForm" action="${basePath}sc?method=score_submit" method="post">
			<input type="hidden" name="cId" value="${cId}">
			<table class="tablelist">
				<thead>
					<tr>
						<th>学生ID</th>
						<th>学生姓名</th>
						<th>输入成绩</th>
					</tr>
				</thead>
				<c:forEach items="${list}" var="student">
				<tr>
					<td>${student.stuId}</td>
					<td>${student.stuName}</td>
					<td style="color:red;">
						<input type="text" name="score" value="${student.score}">
						<input type="hidden" name="stuId" value="${student.stuId}">
					</td>
				</tr>
				</c:forEach>
			</table>
			<br/>
			<button type="submit" class="mybtn">提交评分</button>
		</form>
	</body>
</html>
