package com.epam.testapp.database.hibernate;

import com.epam.testapp.jdbc.pool.NewsDAOIF;
import com.epam.testapp.model.News;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 3/1/12
 * Time: 10:51 AM
 */
@Transactional
@Repository
public class NewsHibernateDAO implements NewsDAOIF {
    private static final String GET_ALL_NEWS = "getAllNews";
    private static final String DELETE_NEWS = "deleteNews";
    private static final String ID_LIST = "idList";
    private static final String ID = "id";
    private static final String FETCH_BY_ID = "fetchById";
    private Class<News> persistentClass = News.class;

    @Resource
    private SessionFactory sessionFactory;


    @Override
    public List<News> getList() throws SQLException {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.getNamedQuery(GET_ALL_NEWS);
        return (List<News>) query.list();
    }

    @Override
    public boolean save(News news) throws SQLException {
        sessionFactory.getCurrentSession().update(news);
        return true;
    }

    @Override
    public boolean add(News news) throws SQLException {
        sessionFactory.getCurrentSession().save(news);
        return true;
    }

    @Override
    public boolean remove(Integer... deleteId) throws SQLException {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.getNamedQuery(DELETE_NEWS);
        query.setParameterList(ID_LIST, deleteId);
        query.executeUpdate();
        return true;
    }

    @Override
    public News fetchById(Integer newsId) throws SQLException {
       Session session = sessionFactory.getCurrentSession();
       Query query = session.getNamedQuery(FETCH_BY_ID);
       query.setParameter(ID, newsId);
       return (News) query.list().get(0);
    }
}
