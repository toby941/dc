<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="text/css" href="/style/main.css" rel="stylesheet" />
<link href="/style/jquery-ui.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="/style/chosen.css" rel="stylesheet" />

<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script src="/js/plugins/chosen.jquery.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜品管理</title>
</head>
<body style="width: 100%;">
<div id="main">
<div class="mainCon">
<div class="rightCon">
<h1 class="tit">菜品列表
</h1>
<table class="tabYH" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>菜品ID</th>
    <th>菜品名称</th>
    <th>菜品类别</th>
    <th>操作</th>
  </tr>
      <c:forEach items="${command.courses}" var="course">
         <tr>
        <td><c:out value="${course.courseNo}"></c:out> </td>
        <td><c:out value="${course.courseName}"></c:out></td>
         <td><c:out value="${course.courseTypeStr}"></c:out></td>
        <td>
        <a href="/admin/companies/view/1">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="/admin/companies/delete/1" data-method="post" data-confirm="您确定要删除该公司吗？">删除</a> </td></tr>
       </c:forEach>
</table>
</div>
</div>
</body>
</html>