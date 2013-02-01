<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="text/css" href="/style/main.css" rel="stylesheet" />
<link href="/style/jquery-ui.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="/style/chosen.css" rel="stylesheet" />
<style type="text/css">
/*popup*/
div{text-align:center;}
.masker{position: absolute; z-index: 998; top: 0; left: 0; right: 0; bottom: 0; background-color: #000; opacity: .5; filter:progid:DXImageTransform.Microsoft.Alpha(Opacity=50); }
.popwin{position: absolute; z-index: 999; top: 0; left: 0;}

</style>
<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script src="/js/plugins/chosen.jquery.js" type="text/javascript"></script>
<script src="http://open.web.meitu.com/sources/xiuxiu.js" type="text/javascript"></script>
<script type="text/javascript" charset="utf-8" src="/ue/editor_config.js"></script>
<script type="text/javascript" charset="utf-8" src="/ue/editor_all.js"></script>
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
      <td>
     <!--  <form:textarea path="desc" cols="60" rows="6" />  -->
      <div>
    <script  id="editor" type="text/plain" name="desc">${command.desc}</script>
    </div>
      </td>
    </tr>
     <tr>
      <td align="right">预览 </td>
       <td align="left"><img src="${host}/packagephoto/${command.coursePackage.id}/1.jpg" alt=""  height="150" id="photo"/> </td>
    </tr>
    <tr id="normalupload">
      <td align="right">图片:</td>
      <td><input type="file" name="photo1"/></td>
    </tr>
     <tr style="display: none" id="flashupload">
      <td align="right">图片:</td>
      <td><input type="button" id="editPhoto" class="btnBY"  value="编辑图片"/>
      <input type="hidden" name="tmpid" id="tmpid" />
      </td>
    </tr>
    <tr>
      <td align="right"><input type="submit" value="提交" class="btnBY"/></td>
      <td><input type="button" onclick="location.href='/packagelist'" value="返回" class="btnBY" /></td>
    </tr>
  </table>
</form:form>
<div id="masker" class="masker" style="width: 1324px; height: 1223px;display: none;" ></div>
<div class="popwin" id="center" style="display: none;">
<div id="altContent"></div>
</div>

<script type="text/javascript">
  $(document).ready(function(){
	  if(xiuxiu!=null){
		  
		  $("#normalupload").hide();
		  $("#flashupload").show();
		xiuxiu.embedSWF("altContent",2,"800","600");
	       /*第1个参数是加载编辑器div容器，第2个参数是编辑器类型，第3个参数是div容器宽，第4个参数是div容器高*/
		xiuxiu.setUploadURL("${host}/uploadphoto");//修改为您自己的上传接收图片程序
		xiuxiu.onInit = function ()
		{
			xiuxiu.loadPhoto($("#photo").attr("src"));
		}	
		xiuxiu.onUploadResponse = function (data)
		{
			if(!isNaN(data)){
			$("#photo").attr("src","/tmpphoto/"+data+".jpg");
			$("#tmpid").val(data);
			}
			xiuxiu.close();
		}
		xiuxiu.onClose = function() {
			$("#masker").hide();
			$("#center").hide();
		}
		xiuxiu.onDebug = function (data)
		{
			alert("错误响应 " + data);
		}
		
		$("#editPhoto").click(function(){
			$("#masker").show();
			$("#center").show();
		});
	  }
  });
</script>
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