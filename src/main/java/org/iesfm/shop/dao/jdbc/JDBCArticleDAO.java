package org.iesfm.shop.dao.jdbc;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

public class JDBCArticleDAO implements ArticleDAO {

    private final static Logger log = LoggerFactory.getLogger(JDBCArticleDAO.class);

    private final static String DELETE_ARTICLE =
            "DELETE FROM Article WHERE id=:id";

    private final static String UPDATE_ARTICLE =
            "UPDATE FROM Article SET name=:name, price=:price WHERE id=:id";

    private final static String INSERT_ARTICLE =
            "INSERT INTO Article(id, name,price) VALUES(:id, :name, :price)";

    private final static String INSERT_TAG =
            "INSERT INTO Tag(article_id, name) VALUES(:id, :tag)";

    private final static String SELECT_ARTICLES =
            "SELECT id, name, price FROM article";

    private final static String SELECT_ARTICLES_WITH_TAG =
            "       SELECT " +
                    "a.* " +
                    "FROM article a " +
                    "INNER JOIN Tag t ON a.id=t.article_id " +
                    "WHERE t.name=:tag";

    private final static String SELECT_ARTICLE_BY_ID =
            "SELECT id, name, price FROM article WHERE id=:id";

    private final static String SELECT_TAGS_BY_ARTICLE_ID =
            "SELECT name FROM Tag WHERE article_id=:article_id";

    private final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, rowNum) ->
            new Article(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    getArticleTags(rs.getInt("id"))
            );

    private NamedParameterJdbcTemplate jdbc;

    public JDBCArticleDAO(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Article> list() {
        return jdbc.query(SELECT_ARTICLES, ARTICLE_ROW_MAPPER);
    }

    @Override
    public List<Article> list(String tag) {
        Map<String, Object> params = new HashMap<>();
        params.put("tag", tag);
        return jdbc.query(SELECT_ARTICLES_WITH_TAG, params, ARTICLE_ROW_MAPPER);
    }

    @Override
    public Article get(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        return jdbc.queryForObject(
                SELECT_ARTICLE_BY_ID,
                params,
                ARTICLE_ROW_MAPPER
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
        try {
            Map<String, Object> insertArticleParams = new HashMap<>();
            insertArticleParams.put("id", article.getId());
            insertArticleParams.put("name", article.getName());
            insertArticleParams.put("price", article.getPrice());
            jdbc.update(INSERT_ARTICLE, insertArticleParams);

            List<Map<String, Object>> rowsParam = new LinkedList<>();
            for (String tag : article.getTags()) {
                Map<String, Object> rowParams = new HashMap<>();
                rowParams.put("id", article.getId());
                rowParams.put("tag", tag);
                rowsParam.add(rowParams);
            }

            jdbc.batchUpdate(INSERT_TAG,
                    // Convierte la lista de mapas en un array de mapas
                    rowsParam.toArray(Map[]::new)
            );

//            for(String tag : article.getTags()) {
//                insertTag(article.getId(), tag);
//            }
            return true;
        } catch (DuplicateKeyException e) {
            return false;
        }
    }

    private void insertTag(int articleId, String tag) {
        Map<String, Object> insertTagParams = new HashMap<>();
        insertTagParams.put("id", articleId);
        insertTagParams.put("tag", tag);
        try {
            jdbc.update(INSERT_TAG, insertTagParams);
        } catch (DuplicateKeyException e) {
            log.warn("Tag duplicado", e);
        }
    }

    @Override
    public boolean update(Article article) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        Map<String, Object> params = new HashMap<>();
        params.put(
                "id", id
        );
        return jdbc.update(DELETE_ARTICLE, params) == 1;

    }
}
