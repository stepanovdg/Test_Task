<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="/view/taglibs.jsp" %>
<bean:define id="newsLink">
    <bean:message key="jsp.list.link.news"/>
</bean:define>
<bean:define id="newsView">
    <bean:message key="jsp.list.link.view"/>
</bean:define>
<bean:define id="newsEdit">
    <bean:message key="jsp.list.link.edit"/>
</bean:define>
<div id="list">
    <a href="newsAction.do?method=${newsLink}"><bean:message key="jsp.list.button.news"/></a>
    <bean:message key="welcome.list"/>
    <html:form styleId="newsForm" action="/newsAction.do" method="POST">
        <logic:iterate id="news" name="newsForm" property="newsList" indexId="index">
            <div id="newsBlock">
                <nested:nest property="news">
                    <!--div id=Lbl001>
                    <label>
                    <!bean:write name="index"/>
                    </label>
                    </div-->
                    <div id=Lbl002>
                        <label>
                            <bean:write name="news" property="title"/>
                        </label>
                    </div>
                    <div id=Lbl004>
                        <label>
                            <bean:write name="news" property="creationDate" formatKey="format.date.short"/>
                        </label>
                    </div>
                    <div id=Lbl003>
                        <label>
                            <bean:write name="news" property="brief"/>
                        </label>
                    </div>
                    <div id="listlinks">
                        <div id=Lnk001>
                            <a href="newsAction.do?method=${newsView}&selectedId=${news.newsId}">
                                <bean:message key="jsp.list.button.view"/></a>
                        </div>
                        <div id=Lnk002>
                            <a href="newsAction.do?method=${newsEdit}&selectedId=${news.newsId}">
                                <bean:message key="jsp.list.button.edit"/></a>
                        </div>
                        <div id=Cb001>
                            <html:checkbox styleClass="checkbox" value="${news.newsId}" property="selected"/>
                        </div>
                    </div>
                </nested:nest>
            </div>
        </logic:iterate>
        <input type="hidden" id="confirmFromList" value="<bean:message key='jsp.list.confirm.delete'/>"/>
        <input type="hidden" id="notChecked" value="<bean:message key='jsp.list.alert.delete'/>"/>

        <div id="Btn002l">
            <html:submit styleId="listDelete" property="method">
                <bean:message key="jsp.list.button.delete"/>
            </html:submit>
        </div>
    </html:form>
</div>

