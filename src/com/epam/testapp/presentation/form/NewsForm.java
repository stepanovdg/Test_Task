package com.epam.testapp.presentation.form;

import com.epam.testapp.model.News;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 2/7/12
 * Time: 10:28 AM
 */
public class NewsForm extends ActionForm {
    private News newsMessage;
    private List<News> newsList;
    private Integer[] selected;
    private Integer idSelected;


    public Integer getIdSelected() {
        return idSelected;
    }

    public void setIdSelected(Integer idSelected) {
        this.idSelected = idSelected;
    }


    @Override
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // selected = null;
        // idSelected = null;
        //  newsMessage = null;
    }

    public Integer[] getSelected() {
        return selected;
    }

    public void setSelected(Integer[] selected) {
        this.selected = selected;
    }

    public News getNewsMessage() {
        return newsMessage;
    }

    public void setNewsMessage(News newsMessage) {
        this.newsMessage = newsMessage;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
