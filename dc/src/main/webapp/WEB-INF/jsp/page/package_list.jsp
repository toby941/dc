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
<title>套餐管理</title>
</head>
<body style="width: 100%;">
<div id="main">
<div class="mainCon">
<div class="rightCon">
<h1 class="tit">套餐列表
</h1>
<table class="tabYH" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <th>套餐ID</th>
    <th>套餐名称</th>
    <th>操作</th>
  </tr>
      <c:forEach items="${command.packages}" var="p">
         <tr>
        <td><c:out value="${p.id}"></c:out> </td>
        <td><c:out value="${p.coursePackageName}"></c:out></td>
        <td>
        <a href="/view/package/${p.id}">修改</a>&nbsp;&nbsp;&nbsp;&nbsp;
       </td></tr>
       </c:forEach>
</table>
</div>
</div>
</body>
</html>