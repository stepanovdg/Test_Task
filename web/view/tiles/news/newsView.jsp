<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="/view/taglibs.jsp" %>
<bean:define id="newsLink">
    <bean:message key="jsp.list.link.news"/>
</bean:define>
<div id="view">
    <a href="newsAction.do?method=${newsLink}"><bean:message key="jsp.list.button.news"/></a>
    <bean:message key="welcome.view"/>
    <html:form action="/newsAction.do" method="POST">
        <div id="newsBlock">
            <div id=Txt001v>
                <div class="names"><bean:message key="jsp.edit.text.title"/></div>
                <div class="output"><bean:write property="newsMessage.title" name="newsForm"/></div>
            </div>
            <div id=Txt003v>
                <div class="names"><bean:message key="jsp.edit.text.date"/></div>
                <div class="output"><bean:write property="newsMessage.creationDate" name="newsForm"
                                                formatKey="format.date.short"/></div>
            </div>
            <div id=TxtA001v>
                <div class="names"><bean:message key="jsp.edit.text.brief"/></div>
                <div class="output"><bean:write property="newsMessage.brief" name="newsForm"/></div>
            </div>
            <div id=TxtA002v>
                <div class="names"><bean:message key="jsp.edit.text.content"/></div>
                <div class="output"><bean:write property="newsMessage.context" name="newsForm"/></div>
            </div>
        </div>
        <input type="hidden" id="confirmFromView" value="<bean:message key="jsp.list.confirm.delete"/>"/>

        <div id="Btn001v">
            <html:submit property="method">
                <bean:message key="jsp.view.button.edit"/>
            </html:submit>
        </div>
        <div id="Btn002v">
            <html:submit styleId="viewDelete" property="method">
                <bean:message key="jsp.view.button.delete"/>
            </html:submit>
        </div>
    </html:form>
</div>