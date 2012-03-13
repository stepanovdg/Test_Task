package com.epam.testapp.database.hibernate;

import com.epam.testapp.JDBC.pool.NewsDAOIF;
import com.epam.testapp.model.News;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
    private static final String CREATION_DATE = "creationDate";
    private Class<News> persistentClass = News.class;

    @Resource
    private SessionFactory sessionFactory;


    @Override
    public List<News> getList() throws SQLException {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(persistentClass);
        criteria.addOrder(Order.desc(CREATION_DATE));
        return (List<News>) criteria.list();
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

         News news;
         Session session = sessionFactory.getCurrentSession();
        for (Integer id : deleteId) {
            news = (News) session.get(persistentClass, id);
            session.delete(news);
        }
        return true;
    }

    @Override
    public News fetchById(Integer newsId) throws SQLException {
        Session session = sessionFactory.getCurrentSession();
        return (News) session.get(persistentClass, newsId);
    }
}
