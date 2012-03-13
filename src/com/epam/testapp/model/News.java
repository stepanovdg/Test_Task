package com.epam.testapp.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;


/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 2/7/12
 * Time: 10:01 AM
 */
@Entity
@Table(name = "NEWS")
public class News implements Serializable {

    @Id
    @Column(name = "NEWS_ID")
    @GenericGenerator(name = "generator",
            strategy = "sequence-identity",
            parameters =
            @org.hibernate.annotations.Parameter(name = "sequence",value = "NEWS_SEQ"))
    @GeneratedValue(generator = "generator")
    private Integer newsId;

    @Column(name = "TITLE", length = 100, nullable = false)
    private String title;


    @Column(name = "CREATION_DATE", length = 10, nullable = false)
    private Date creationDate;

    @Column(name = "BRIEF", length = 500, nullable = false)
    private String brief;

    @Column(name = "CONTEXT", length = 2048, nullable = false)
    private String context;

    public News() {
    }

    public News(Integer newsId, String title, Date creationDate, String brief, String context) {
        this.newsId = newsId;
        this.title = title;
        this.creationDate = creationDate;
        this.brief = brief;
        this.context = context;
    }


    public Integer getNewsId() {
        return newsId;
    }

    public void setNewsId(Integer newsId) {
        this.newsId = newsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
