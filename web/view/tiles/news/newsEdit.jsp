<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="/view/taglibs.jsp" %>
<bean:define id="newsLink">
    <bean:message key="jsp.list.link.news"/>
</bean:define>
<bean:define id="date">
    <bean:write property='newsMessage.creationDate' name='newsForm' formatKey='format.date.short'/>
</bean:define>
<bean:define id="currentDate">
    <bean:write property='newsMessage.creationDate' name='newsForm' />
</bean:define>
<div id="edit">
    <a href="newsAction.do?method=${newsLink}"><bean:message key="jsp.list.button.news"/></a>
    <bean:message key="welcome.edit"/>
    <html:form styleId="editForm" action="/newsAction.do" method="POST">
        <input type="hidden" id="dateValue" value="${date}"/>
        <input type="hidden" id="dateCurrent" value="${currentDate}"/>

        <div id="newsBlock">
            <nested:nest property="news">
                <div id=Txt001>
                    <div class="names"><bean:message key="jsp.edit.text.title"/></div>
                    <div class="output"><html:text styleId="editTitle" property="newsMessage.title"
                                                   maxlength="100"/></div>
                    <div class="hidden">
                        <label id="countTitle">
                            <bean:message key="jsp.edit.count.title.part1"/>
                            <span id="title_counter"></span>
                            <bean:message key="jsp.edit.count.title.part2"/>
                        </label>
                    </div>
                </div>
                <div id=Txt003>
                    <div class="names"><bean:message key="jsp.edit.text.date"/></div>
                    <div class="output"><html:text styleId="editDate" property="newsMessage.creationDate"
                                                   maxlength="10" value="${date}">
                    </html:text></div>
                    <div class="hidden">
                        <label id="countDate">
                            <bean:message key="jsp.edit.count.date.part1"/>
                            <span id="date_counter"></span>
                            <bean:message key="jsp.edit.count.date.part2"/>
                        </label>
                    </div>
                </div>
                <div id=TxtA001>
                    <div class="names"><bean:message key="jsp.edit.text.brief"/></div>
                    <div class="output"><html:textarea styleId="editBrief" property="newsMessage.brief"
                            /></div>
                    <div class="hidden">
                        <label id="countBrief">
                            <bean:message key="jsp.edit.count.brief.part1"/>
                            <span id="brief_counter"></span>
                            <bean:message key="jsp.edit.count.brief.part2"/>
                        </label>
                    </div>
                </div>
                <div id=TxtA002>
                    <div class="names"><bean:message key="jsp.edit.text.content"/></div>
                    <div class="output"><html:textarea styleId="editContext" property="newsMessage.context"
                            /></div>
                    <div class="hidden">
                        <label id="countContext">
                            <bean:message key="jsp.edit.count.context.part1"/>
                            <span id="context_counter"></span>
                            <bean:message key="jsp.edit.count.context.part2"/>
                        </label>
                    </div>
                </div>
            </nested:nest>
        </div>
        <input type="hidden" id="alertTitle" value="<bean:message key='jsp.list.alert.title'/>"/>
        <input type="hidden" id="alertBrief" value="<bean:message key='jsp.list.alert.brief'/>"/>
        <input type="hidden" id="alertDate" value="<bean:message key='jsp.list.alert.date'/>"/>
        <input type="hidden" id="alertContent" value="<bean:message key='jsp.list.alert.content'/>"/>
        <input type="hidden" id="alertLastSymb" value="<bean:message key='jsp.list.alert.lastSymb'/>"/>



        <div id="Btn002v">
            <html:submit styleId="editCancel" property="method">
                <bean:message key="jsp.edit.button.cancel"/>
            </html:submit>
        </div>
         <div id="Btn001v">
            <html:submit styleId="editSave" property="method">
                <bean:message key="jsp.edit.button.save"/>
            </html:submit>
        </div>
    </html:form>
</div>