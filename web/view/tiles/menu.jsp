<%@ page contentType="text/html;charset=UTF-8"
         language="java" pageEncoding="utf-8"%>
<%@ include file="/view/taglibs.jsp" %>
<bean:define id="newsLink">
    <bean:message key="jsp.menu.link.list"/>
</bean:define>
<bean:define id="newsAdd">
    <bean:message key="jsp.menu.link.add"/>
</bean:define>

<div id="menuBackground">
    <div id="menuTitle">
        <bean:message key="welcome.menu"/>
    </div>
    <div id="menuLink">
        <li>
            <a href="newsAction.do?method=${newsLink}"><bean:message key="jsp.menu.button.list"/> </a>
        </li>
        <li>
            <a href="newsAction.do?method=${newsAdd}"><bean:message key="jsp.menu.button.add"/></a>
        </li>
    </div>
</div>
