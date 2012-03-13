<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles-el" prefix="tiles" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/ie.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/news.js"></script>
</head>
<body>
<div id="page">
    <div id="header">
        <tiles:insert attribute="header"/>
    </div>
    <div id="mider">
        <div id="menu">
            <tiles:insert attribute="menu"/>
        </div>
        <div id="body">
            <tiles:insert attribute="body"/>
        </div>
    </div>
    <div id="footer">
        <tiles:insert attribute="footer"/>
    </div>
</div>
</body>
</html>
