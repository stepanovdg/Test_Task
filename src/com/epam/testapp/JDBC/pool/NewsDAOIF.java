package com.epam.testapp.JDBC.pool;

import com.epam.testapp.model.News;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 2/7/12
 * Time: 10:05 AM
 */
public interface NewsDAOIF {
    public List<News> getList() throws SQLException;
    public boolean save(News news) throws SQLException;
    public boolean add(News news) throws SQLException;
    public boolean remove(Integer... deleteId) throws SQLException;
    public News fetchById(Integer newsId) throws SQLException;


}
