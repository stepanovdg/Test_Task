package com.epam.testapp.database.jpa;

import com.epam.testapp.JDBC.pool.NewsDAOIF;
import com.epam.testapp.model.News;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 2/29/12
 * Time: 3:03 AM
 */
@Transactional
public class NewsJPADAO implements NewsDAOIF {
    private static final String NEWS_GET_ALL = "from News order by creationDate desc ";
    private Class<News> persistentClass;
    protected EntityManager em;


    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<News> getList() throws SQLException {
        Query query = this.em.createQuery(NEWS_GET_ALL);
        return query.getResultList();
    }

    @Override
    public boolean save(News news) throws SQLException {
        getEntityManager().persist(news);
        return true;
    }

    @Override
    public boolean add(News news) throws SQLException {
        return save(news);
    }

    @Override
    public boolean remove(Integer... deleteId) throws SQLException {
        News news;
        for (Integer id : deleteId) {
            news = getEntityManager().find(persistentClass, id);
            getEntityManager().remove(news);
        }
        return true;
    }

    @Override
    public News fetchById(Integer newsId) throws SQLException {
        return getEntityManager().find(persistentClass, newsId);

    }
}
