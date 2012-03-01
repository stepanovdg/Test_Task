package com.epam.testapp.presentation.action;

import com.epam.testapp.JDBC.pool.NewsDAOIF;
import com.epam.testapp.model.News;
import com.epam.testapp.presentation.form.NewsForm;
import org.apache.struts.action.*;
import org.apache.struts.actions.LookupDispatchAction;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 2/7/12
 * Time: 9:51 AM
 */
public class NewsAction extends LookupDispatchAction {

    private static final String LIST = "list";
    private static final String ERROR_NEWS_DB_LIST = "error.news.db.list";
    private static final String NEWS_LIST = "newsList";
    private static final String VIEW = "view";
    private static final String ERROR_NEWS_DB_VIEW = "error.news.db.view";
    private static final String EDIT = "edit";
    private static final String VIEW_PAGE = "viewPage";
    private static final String ERROR_NEWS_DB_EDIT = "error.news.db.edit";
    private static final String ERROR_PAGE = "errorPage";
    private static final String DELETE = "delete";
    private static final String ERROR_NEWS_DB_REMOVE = "error.news.db.remove";
    private static final String DELETE_PAGE = "deletePage";
    private static final String ERROR_NEWS_DB_SAVE = "error.news.db.save";
    private static final String SAVE = "save";
    private static final String EDIT_PAGE = "editPage";


    private NewsDAOIF newsDAO;
    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("jsp.edit.button.cancel", "cancel");
        map.put("jsp.menu.link.list", "list");
        map.put("jsp.list.link.news", "list");
        map.put("jsp.menu.button.list", "list");
        map.put("jsp.menu.button.add", "list");
        map.put("jsp.menu.link.add", "add");
        map.put("jsp.list.link.view", "view");
        map.put("jsp.edit.button.save", "save");
        map.put("jsp.list.button.delete", "delete");
        map.put("jsp.view.button.delete", "delete");
        map.put("jsp.view.button.edit", "edit");
        map.put("jsp.list.link.edit", "edit");
    }

    public void setNewsDAO(NewsDAOIF newsDAO) {
        this.newsDAO = newsDAO;
    }


    @Override
    protected Map getKeyMethodMap() {
        return map;
    }

    public ActionForward cancel(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException, ServletException {
        NewsForm newsForm = (NewsForm) form;
        if (newsForm.getNewsMessage().getNewsId() == null) {
            return list(mapping, form, request, response);
        } else {
            return view(mapping, form, request, response);
        }
    }

    public ActionForward list(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {
        NewsForm newsForm = (NewsForm) form;
        newsForm.reset(mapping, request);
        ActionErrors errors = new ActionErrors();
        try {
            newsForm.setNewsList(newsDAO.getList());
        } catch (SQLException e) {
            errors.add(LIST, new ActionMessage(ERROR_NEWS_DB_LIST));
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.findForward(ERROR_PAGE)));
        }
        return mapping.findForward(NEWS_LIST);
    }

    public ActionForward view(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {
        NewsForm newsForm = (NewsForm) form;
        ActionErrors errors = new ActionErrors();
        try {
            if (newsForm.getIdSelected() != null) {
                newsForm.setNewsMessage(newsDAO.fetchById(newsForm.getIdSelected()));
            } else {
                newsForm.setNewsMessage(newsDAO.fetchById(newsForm.getNewsMessage().getNewsId()));
            }
        } catch (SQLException e) {
            errors.add(VIEW, new ActionMessage(ERROR_NEWS_DB_VIEW));
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.findForward(ERROR_PAGE)));
        }
        return mapping.findForward(VIEW_PAGE);
    }


    public ActionForward edit(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {
        NewsForm newsForm = (NewsForm) form;
        ActionErrors errors = new ActionErrors();
        try {
            if (newsForm.getIdSelected() != null) {
                newsForm.setNewsMessage(newsDAO.fetchById(newsForm.getIdSelected()));
            } else {
                newsForm.setNewsMessage(newsDAO.fetchById(newsForm.getNewsMessage().getNewsId()));
            }
        } catch (SQLException e) {
            errors.add(EDIT, new ActionMessage(ERROR_NEWS_DB_EDIT));
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.findForward(ERROR_PAGE)));
        }
        saveToken(request);
        return mapping.findForward(EDIT_PAGE);
    }

    public ActionForward delete(ActionMapping mapping,
                                ActionForm form,
                                HttpServletRequest request,
                                HttpServletResponse response)
            throws IOException, ServletException {
        NewsForm newsForm = (NewsForm) form;
        ActionErrors errors = new ActionErrors();
        News newsMessage = newsForm.getNewsMessage();
        try {
            if (newsForm.getSelected() != null) {
                newsDAO.remove(newsForm.getSelected());
            } else {
                newsDAO.remove(newsMessage.getNewsId());
            }
            newsForm.setNewsList(newsDAO.getList());
        } catch (SQLException e) {
            errors.add(DELETE, new ActionMessage(ERROR_NEWS_DB_REMOVE));
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }
        return mapping.findForward(DELETE_PAGE);
    }

    public ActionForward save(ActionMapping mapping,
                              ActionForm form,
                              HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {
        NewsForm newsForm = (NewsForm) form;
        ActionErrors errors = new ActionErrors();
        News newsMessage = newsForm.getNewsMessage();
        try {
            if (isTokenValid(request)) {
                if (newsMessage.getNewsId() != null) {
                    newsDAO.save(newsMessage);
                } else {
                    newsDAO.add(newsMessage);
                }
            } else {
                return view(mapping, form, request, response);
            }
            newsForm.setNewsList(newsDAO.getList());
        } catch (SQLException e) {
            errors.add(SAVE, new ActionMessage(ERROR_NEWS_DB_SAVE));
        }
        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (new ActionForward(mapping.getInput()));
        }
        resetToken(request);
        return view(mapping, form, request, response);
    }

    public ActionForward add(ActionMapping mapping,
                             ActionForm form,
                             HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {
        NewsForm newsForm = (NewsForm) form;
        Date creationDate = new Date(System.currentTimeMillis());
        News newsMessage = new News(null, "", creationDate, "", "");
        newsForm.setNewsMessage(newsMessage);
        newsForm.setIdSelected(null);
        saveToken(request);
        return mapping.findForward(EDIT_PAGE);
    }


}
