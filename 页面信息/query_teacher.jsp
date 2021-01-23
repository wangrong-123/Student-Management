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
					<th>ID</th>
					<th>课程名称</th>
					<th>[0,60)</th>
					<th>[60,70]</th>
					<th>(70,85]</th>
					<th>[85,100]</th>
					<!-- 查看成绩区间时不能打分
					<th width="170px">操作</th>
					 -->
				</tr>
			</thead>
			<c:forEach items="${list1}" var="temp">
			<tr>
				<td>${temp.cId}</td>
				<td>${temp.cname}</td>
				<td>${temp.bad}</td>
				<td>${temp.common}</td>
				<td>${temp.good}</td>
				<td>${temp.best}</td>
				<!-- 及格率查看不需要打分
				<td>
					<button class="edit" type="button" onclick="window.location.href='${basePath}sc?method=cs&cId=${course.cId}'">
						<i class="fa fa-edit"></i>
						打分
					</button>
				</td>
				-->
			</tr>
			</c:forEach>
		</table>
		<!-- 分隔两个区间表格和及格率表格 -->
		<br/>
		<table class="tablelist">
			<thead>
				<tr>
					<th>ID</th>
					<th>课程名称</th>
					<th>及格人数</th>
					<th>总人数</th>
					<th>及格率</th>
					<!--  及格率查看不需要任何操作
					<th width="170px">操作</th>
					-->
				</tr>
			</thead>
			<c:forEach items="${list2}" var="temp">
			<tr>
				<td>${temp.cId}</td>
				<td>${temp.cname}</td>
				<td>${temp.jgnum}</td>
				<td>${temp.allnum}</td>
				<td>${temp.jgl}%</td>
				<!-- 及格率查看不需要打分
				<td>
					<button class="edit" type="button" onclick="window.location.href='${basePath}sc?method=cs&cId=${course.cId}'">
						<i class="fa fa-edit"></i>
						打分
					</button>
				</td>
				 -->
			</tr>
			</c:forEach>
		</table>
	</body>
</html>
