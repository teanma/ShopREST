package org.iesfm.shop.controller;

import org.iesfm.shop.Article;
import org.iesfm.shop.dao.ArticleDAO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class ArticleController {

    private ArticleDAO articleDAO;

    public ArticleController(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles")
    public List<Article> list() {
        return articleDAO.list();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles/{id}")
    public Article get(@PathVariable("id") int id) {
        if (articleDAO.get(id) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "article not found"
            );
        } else {
            return articleDAO.get(id);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/articles")
    public void insert(@RequestBody Article article) {
        if (articleDAO.insert(article)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "article already exists"
            );
        }
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/articles/{id}")
    public void update(@RequestBody Article article) {
        if (!articleDAO.update(article)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "article not found"
            );
        }
    }

    @RequestMapping(method = RequestMethod.GET, path = "/articles/{tag}")
    public List<Article> list(@RequestParam(value = "tag", required = false) String tag) {
        if (tag == null) {
            return articleDAO.list();
        } else {
            return articleDAO.list(tag);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/articles/{id}")
    public void delete(@PathVariable("id") int id) {
        if (!articleDAO.delete(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "article not found"
            );
        }
    }
}
