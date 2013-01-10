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
<title>套餐详细</title>
</head>
<body style="width: 100%;">
<form:form commandName="command" id="myform" name="myform" action="/packageedit/${command.coursePackage.id}" method="post" enctype="multipart/form-data">
  <h1 class="tit">修改套餐信息</h1>
  <table align="center" class="tabNF reg">
    <tr>
      <td align="right"><span class="must">*</span>套餐名称:</td>
      <td><form:input path="coursePackage.coursePackageName" disabled="true" /></td>
    </tr>
    <tr>
      <td align="right">套餐描述:</td>
      <td><form:textarea path="desc" cols="60" rows="6" /> </td>
    </tr>
     <tr>
      <td align="right">预览 </td>
       <td align="left"><img src="/packagephoto/${command.coursePackage.id}/1.jpg" alt=""  height="150"/> </td>
      
    </tr>
    <tr>
      <td align="right">图片:</td>
      <td><input type="file" name="photo1"/> </td>
    </tr>
    <tr>
      <td align="right"><input type="submit" value="提交" class="btnBY"/></td>
      <td><input type="button" onclick="location.href='/packagelist'" value="返回" class="btnBY" /></td>
    </tr>
  </table>
</form:form>

</body>
</html>