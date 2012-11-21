<%@ page contentType="text/html; charset=UTF-8"%>
<link type="text/css" href="/style/main.css" rel="stylesheet" />
<link href="/style/jquery-ui.css" rel="stylesheet" type="text/css" />
<link type="text/css" href="/style/chosen.css" rel="stylesheet" />

<script type="text/javascript" src="/js/jquery.js"></script>
<script type="text/javascript" src="/js/jquery-ui.js"></script>
<script src="/js/plugins/chosen.jquery.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/jspf/taglibs.jspf"%>
<c:if test="${not empty refresh}">
	<c:choose>
		<c:when test="${refresh == 1}">
			<script>
			  parent.leftFrame.location.reload();
      </script>
		</c:when>
		<c:when test="${refresh == 2}">
			<script>
			  parent.location.reload();
      </script>
		</c:when>
	</c:choose>
	<c:remove var="refresh" scope="session" />
</c:if>
<%@page import="java.util.*"%>