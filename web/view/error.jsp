<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ include file="taglibs.jsp"%>
<bean:define id="newsLink">
    <bean:message key="jsp.menu.link.list"/>
</bean:define>
<div id="error">
    <bean:message key="welcome.error"/>
    <html:errors/>
    <a href="newsAction.do?method=${newsLink}"><bean:message key="jsp.list.button.news"/></a>
</div>