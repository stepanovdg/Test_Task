package com.epam.testapp.database.jpa;

import com.epam.testapp.jdbc.pool.NewsDAOIF;
import com.epam.testapp.model.News;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 2/29/12
 * Time: 3:03 AM
 */
@Transactional
public class NewsJPADAO implements NewsDAOIF {
    private static final String GET_ALL_NEWS = "getAllNews";
    private static final String DELETE_NEWS = "deleteNews";
    private static final String ID_LIST = "idList";
    private static final String ID = "id";
    private static final String FETCH_BY_ID = "fetchById";
    private static final String CREATION_DATE = "creationDate";
    private Class<News> persistentClass = News.class;
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
        /*CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<News> criteriaQuery = criteriaBuilder.createQuery(persistentClass);
        Root<News> newsRoot = criteriaQuery.from(persistentClass);
        criteriaQuery.orderBy(criteriaBuilder.desc(newsRoot.<Object>get(CREATION_DATE)));
        TypedQuery<News> typedQuery = em.createQuery(criteriaQuery);
        return typedQuery.getResultList();  */
        Query query = em.createNamedQuery(GET_ALL_NEWS);
        return (List<News>) query.getResultList();
    }

    @Override
    public boolean save(News news) throws SQLException {
        getEntityManager().flush();
        return true;
    }

    @Override
    public boolean add(News news) throws SQLException {
        getEntityManager().persist(news);
        return true;
    }

    @Override
    public boolean remove(Integer... deleteId) throws SQLException {
        /* News news;
     for (Integer id : deleteId) {
         news = getEntityManager().find(persistentClass, id);
         getEntityManager().remove(news);
     }
     return true;  */
        List<Integer> inList = new ArrayList<>();
        Collections.addAll(inList, deleteId);
        Query query = em.createNamedQuery(DELETE_NEWS);
        query.setParameter(ID_LIST, inList);
        query.executeUpdate();
        return true;
    }

    @Override
    public News fetchById(Integer newsId) throws SQLException {
        /*return getEntityManager().find(persistentClass, newsId);   */
        Query query = em.createNamedQuery(FETCH_BY_ID);
        query.setParameter(ID, newsId);
        return (News) query.getSingleResult();
    }
}
