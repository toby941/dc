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
<script type="text/javascript" charset="utf-8" src="/ue/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ue/editor_all.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>菜品详细</title>
  <style type="text/css">
        .clear {
            clear: both;
        }
    </style>
</head>
<body style="width: 100%;">
<form:form commandName="command" id="myform" name="myform" action="/edit/${command.course.courseNo}" method="post" enctype="multipart/form-data">
  <h1 class="tit">修改菜品信息</h1>
  <table align="center" class="tabNF reg">
    <tr>
      <td align="right"><span class="must">*</span>菜品名称:</td>
      <td><form:input path="course.courseName" disabled="true" /></td>
    </tr>
    <tr>
      <td align="right">菜品类别:</td>
      <td><form:input path="course.courseTypeStr" disabled="true" /></td>
    </tr>
    <tr>
      <td align="right">菜品描述:</td>
      
      <!--  <td><form:textarea path="desc" cols="60" rows="6" /> </td>-->
      <td>
      <div>
    <script  id="editor" type="text/plain" name="desc">${command.desc}</script>
    </div>
    </td>
    </tr>
     <tr>
      <td align="right">预览 </td>
       <td align="left"><img src="/photo/${command.course.courseNo}/1.jpg" alt=""  height="150"/> </td>
      
    </tr>
    <tr>
      <td align="right">图片:</td>
      <td><input type="file" name="photo1"/> </td>
    </tr>
    <tr>
      <td align="right">预览 </td>
       <td align="left"><img src="/photo/${command.course.courseNo}/2.jpg" alt=""  height="150"/> </td>
      
    </tr>
    <tr>
      <td align="right">图片:</td>
      <td><input type="file" name="photo2"/> </td>
    </tr>
     <tr>
      <td align="right">预览 </td>
       <td align="left"><img src="/photo/${command.course.courseNo}/3.jpg" alt=""  height="150"/> </td>
      
    </tr>
    <tr>
      <td align="right">图片:</td>
      <td><input type="file" name="photo3"/> </td>
    </tr>
    <tr>
      <td align="right"><input type="submit" value="提交" class="btnBY"/></td>
      <td><input type="button" onclick="location.href='/list'" value="返回" class="btnBY" /></td>
    </tr>
  </table>
</form:form>
<script type="text/javascript">
window.UEDITOR_HOME_URL = "/ue/";
    //实例化编辑器
    var ue = UE.getEditor('editor');

    ue.addListener('ready',function(){
        this.focus()
    });

 
</script>
</body>
</html>