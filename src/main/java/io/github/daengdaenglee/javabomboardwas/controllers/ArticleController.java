package io.github.daengdaenglee.javabomboardwas.controllers;

import io.github.daengdaenglee.javabomboardwas.entities.Article;
import io.github.daengdaenglee.javabomboardwas.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    @Autowired
    public ArticleService articleService;

    @GetMapping("/articles")
    public Map<String, List<ArticleJSON>> listAllArticles() {
        Map<String, List<ArticleJSON>> response = new HashMap<>();

        List<ArticleJSON> allArticles = articleService.getAllArticles().stream()
                .map(ArticleJSON::fromArticle)
                .collect(Collectors.toList());
        response.put("data", allArticles);

        return response;
    }

    @GetMapping("/articles/{articleId}")
    public Map<String, ArticleJSON> readArticle(@PathVariable String articleId) {
        Map<String, ArticleJSON> response = new HashMap<>();

        try {
            ArticleJSON articleJSON = ArticleJSON.fromArticle(articleService.getArticleById(articleId));
            response.put("data", articleJSON);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @PostMapping("/articles")
    public Map<String, ArticleJSON> createArticle(@RequestBody Map<String, ArticleJSON> requestBody) {
        Map<String, ArticleJSON> response = new HashMap<>();

        ArticleJSON articleJSON = requestBody.get("data");
        String title = articleJSON.attributes.get("title");
        String body = articleJSON.attributes.get("body");

        try {
            Article article = articleService.makeNewArticle(title, body);
            response.put("data", ArticleJSON.fromArticle(article));
        } catch (IOException e) {
        }

        return response;
    }

    @PutMapping("/articles/{articleId}")
    public Map<String, ArticleJSON> updateArticle(@PathVariable String articleId, @RequestBody Map<String, ArticleJSON> requestBody) {
        Map<String, ArticleJSON> response = new HashMap<>();

        ArticleJSON articleJSON = requestBody.get("data");
        String title = articleJSON.attributes.get("title");
        String body = articleJSON.attributes.get("body");

        try {
            Article article = new Article(articleId, title, body);
            article = articleService.changeArticle(article);

            response.put("data", ArticleJSON.fromArticle(article));
        } catch (Exception e) {

        }

        return response;
    }

    @DeleteMapping("/articles/{articleId}")
    public Object deleteArticle(@PathVariable String articleId) {
        try {
            articleService.deleteArticleById(articleId);
        } catch (Exception e) {
        }

        return null;
    }

    public static class ArticleJSON {
        public String type = "articles";
        public String id;
        public Map<String, String> attributes = new HashMap<>();
        public Map<String, String> links = new HashMap<>();

        public static ArticleJSON fromArticle(Article article) {
            ArticleJSON articleJSON = new ArticleJSON();

            articleJSON.id = article.id;
            articleJSON.attributes.put("title", article.title);
            articleJSON.attributes.put("body", article.body);
            articleJSON.links.put("self", "/api/v1/articles/" + article.id);

            return articleJSON;
        }
    }
}
