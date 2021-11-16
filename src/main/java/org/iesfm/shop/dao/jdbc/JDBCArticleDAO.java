package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

public class JDBCArticleDAO implements ArticleDAO {

    private final static String SELECT_ARTICLE_BY_ID =
            "SELECT id, name, price FROM article WHERE id=:id";

    private final static String SELECT_TAGS_BY_ARTICLE_ID =
            "SELECT name FROM Tag WHERE article_id=:article_id";

    private NamedParameterJdbcTemplate jdbc;

    public JDBCArticleDAO(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Article> list() {
        return null;
    }

    @Override
    public List<Article> list(String tag) {
        return null;
    }

    @Override
    public Article get(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(
                SELECT_ARTICLE_BY_ID,
                params,

                (rs, rowNum) ->
                        new Article(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                getArticleTags(rs.getInt("id"))
                        )

        );
    }
    // Set("Tornillo", "Ferreteria")
    private Set<String> getArticleTags(int articleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("article_id", articleId);
        return new HashSet<>(
                jdbc.query(
                        SELECT_TAGS_BY_ARTICLE_ID,
                        params,
                        (rs, rownum) ->
                                rs.getString("name")
                )
        );
    }

    @Override
    public boolean insert(Article article) {

        return false;
    }

    @Override
    public boolean update(Article article) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
