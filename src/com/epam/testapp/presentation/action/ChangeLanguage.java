package com.epam.testapp.presentation.action;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 2/21/12
 * Time: 7:44 AM
 */

public class ChangeLanguage extends DispatchAction{
    @Override
    protected ActionForward unspecified(ActionMapping mapping,
                                        ActionForm form,
                                        HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        setLocale(request, Locale.forLanguageTag(request.getParameter("language")));
        return mapping.findForward("newsList");
    }


}

