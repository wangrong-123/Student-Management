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
		<table class="tablelist">
			<thead>
				<tr>
					<th>课程ID</th>
					<th>课程名称</th>
					<th>授课老师</th>
					<th>课程分数</th>
				</tr>
			</thead>
			<c:forEach items="${list3}" var="fenshu">
			<tr>
				<td>${fenshu.cId}</td>
				<td>${fenshu.cname}</td>
				<td>${fenshu.tName}</td>
				<td>${fenshu.score}</td>
			</tr>
			</c:forEach>
		</table>

	</body>
</html>
