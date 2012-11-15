<%@ page contentType="text/html; charset=UTF-8" isErrorPage="true"%>
<%@page import="java.io.PrintWriter"%>
<%@page import="java.io.StringWriter"%>
<%
	if(exception!=null){ 
	    StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        exception.printStackTrace(pw);
        pw.flush();
        sw.flush();
     out.println(sw.toString());
	}
%>

