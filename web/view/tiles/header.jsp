<%@ page contentType="text/html;charset=UTF-8"
         language="java" pageEncoding="utf-8"%>
<%@ include file="/view/taglibs.jsp" %>
<div id="logo">
    <bean:message key="welcome.title"/>
</div>
<div id="language">
    <a href="changeLanguage.do?language=en">
        <bean:message key="jsp.header.link.english"/></a>
    <a href="changeLanguage.do?language=ru">
        <bean:message key="jsp.header.link.russian"/></a>
</div>

