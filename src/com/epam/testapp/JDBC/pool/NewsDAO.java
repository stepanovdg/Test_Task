package com.epam.testapp.jdbc.pool;

import com.epam.testapp.model.News;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Stepanov Dmitriy
 * Date: 2/7/12
 * Time: 10:05 AM
 */
public class NewsDAO implements NewsDAOIF {
    private ConnectionManager connectionManager;

    private static final String NEWS_CREATE = "begin insert into NEWS (TITLE,CREATION_DATE,BRIEF,CONTEXT) values (?, ?, ?, ?) return NEWS_ID into :id; end; ";
    private static final String NEWS_SAVE = "update NEWS set TITLE = ?,CREATION_DATE = ?,BRIEF = ?,CONTEXT = ? where NEWS_ID = ?";
    private static final String NEWS_GET_ALL = "select * from NEWS order by CREATION_DATE desc ";
    private static final String NEWS_GET = "select * from NEWS where NEWS_ID = ?";
    private static final String NEWS_DELETE_ONE = "delete from NEWS where NEWS_ID =?";
    private static final String NEWS_DELETE = "delete from NEWS where NEWS_ID in (";


    public void setConnectionManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    @Override
    public List<News> getList() throws SQLException {
        List<News> newsList = new ArrayList<>();
        News news;
        String title;
        String brief;
        String content;
        Date newsDate;
        Integer newsId;
        try (Connection cn = connectionManager.getConnection()) {
            try (Statement st = cn.createStatement()) {
                try (ResultSet rs = st.executeQuery(NEWS_GET_ALL)) {
                    if (rs.next()) {
                        do {
                            newsId = rs.getInt(1);
                            title = rs.getString(2);
                            newsDate = rs.getDate(3);
                            brief = rs.getString(4);
                            content = rs.getString(5);
                            news = new News(newsId, title, newsDate, brief, content);
                            newsList.add(news);
                        } while (rs.next());
                    } else {
                        throw new SQLException();
                    }
                    return newsList;
                }
            }

        }
    }

    @Override
    public boolean save(News news) throws SQLException {
        try (Connection cn = connectionManager.getConnection()) {
            try (PreparedStatement st = cn.prepareStatement(NEWS_SAVE)) {
                st.setString(1, news.getTitle());
                st.setDate(2, news.getCreationDate());
                st.setString(3, news.getBrief());
                st.setString(4, news.getContext());
                st.setInt(5, news.getNewsId());
                return st.execute();
            }
        }
    }

    @Override
    public boolean add(News news) throws SQLException {
        try (Connection cn = connectionManager.getConnection()) {
            try (CallableStatement st = cn.prepareCall(NEWS_CREATE)) {
                st.setString(1, news.getTitle());
                st.setDate(2, news.getCreationDate());
                st.setString(3, news.getBrief());
                st.setString(4, news.getContext());
                st.registerOutParameter(5, Types.NUMERIC);
                boolean result = st.execute();
                Integer id = st.getInt(5);
                news.setNewsId(id);
                return result;
            }

        }
    }

    @Override
    public boolean remove(Integer... deleteId) throws SQLException {
        boolean result;
        StringBuilder delete = new StringBuilder(NEWS_DELETE);
        try (Connection cn = connectionManager.getConnection()) {
            if (deleteId.length == 1) {
                delete= new StringBuilder(NEWS_DELETE_ONE);
            } else {
                  for (Integer id : deleteId) {
                    delete.append('?');
                    delete.append(',');
                }
                delete.deleteCharAt(delete.length() - 1);
                delete.append(')');
            }

            try (PreparedStatement st = cn.prepareStatement(delete.toString())) {
                for (int index = 0; index < deleteId.length; index++) {
                    st.setInt(index + 1, deleteId[index]);
                }
                result = st.execute();
                return result;
            }

        }
    }

    @Override
    public News fetchById(Integer newsId) throws SQLException {
        News news;
        String title;
        String brief;
        String content;
        Date newsDate;
        try (Connection cn = connectionManager.getConnection()) {
            try (PreparedStatement st = cn.prepareStatement(NEWS_GET)) {
                st.setInt(1, newsId);
                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        title = rs.getString(2);
                        newsDate = rs.getDate(3);
                        brief = rs.getString(4);
                        content = rs.getString(5);
                        news = new News(newsId, title, newsDate, brief, content);
                    } else {
                        throw new SQLException();
                    }
                    return news;
                }
            }

        }
    }


}
