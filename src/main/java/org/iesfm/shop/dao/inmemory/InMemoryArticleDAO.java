package org.iesfm.shop.dao.inmemory;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class InMemoryArticleDAO implements ArticleDAO {

    private Map<Integer, Article> articles = new HashMap<>();

    @Override
    public List<Article> list() {
        return new LinkedList<>(articles.values());
    }

    @Override
    public List<Article> list(String tag) {
        List<Article> articles = new LinkedList<>();
        for (Article article : list()) {
            if (article.getTags().contains(tag)) {
                articles.add(article);
            }
        }
        return articles;
    }

    @Override
    public Article get(int id) {
        return articles.get(id);
    }

    @Override
    public boolean insert(Article article) {
        if (!articles.containsKey(article.getId())) {
            articles.put(article.getId(), article);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Article article) {
        if(articles.containsKey(article.getId())) {
            articles.put(article.getId(), article);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        if(articles.containsKey(id)) {
            articles.remove(id);
            return true;
        }
        return false;
    }
}
