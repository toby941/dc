<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="/WEB-INF/tags/c.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link type="text/css" href="/style/main.css" rel="stylesheet" />
<link type="text/css" href="/style/zTreeStyle.css" rel="stylesheet" />
</head>
<body>
<ul id="tree" class="tree"></ul>

<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/plugins/jquery.ztree-2.6.js"></script>
<script type="text/javascript">
  var zTreeNodes = null;
  var url = null;
</script>
   <script type="text/javascript">
   zTreeNodes =[{"children":[
{"children":[],"menuName":"菜品列表","open":false,"target":"mainFrame","url":"/list"},
{"children":[],"menuName":"套餐列表","open":false,"target":"mainFrame","url":"/packagelist"}],"menuName":"功能管理","open":true,"target":"mainFrame","url":""}];  
 
  </script>
<script type="text/javascript">
   url = "/list";
</script>
<script type="text/javascript">
  $(document).ready(function() {
    var setting = {
      showLine : true,
      checkable : false,
      nameCol : "menuName",
      nodesCol : "children"
    };

    var zTree = $("#tree").zTree(setting, zTreeNodes);
    var nodes = zTree.getNodes();
    parent.mainFrame.location.href = url;

  });
</script>
</body>
</html>