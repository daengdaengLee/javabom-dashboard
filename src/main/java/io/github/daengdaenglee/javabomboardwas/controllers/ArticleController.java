package io.github.daengdaenglee.javabomboardwas.controllers;

import io.github.daengdaenglee.javabomboardwas.entities.Article;
import io.github.daengdaenglee.javabomboardwas.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        ArticleJSON articleJSON = ArticleJSON.fromArticle(articleService.getArticleById(articleId));
        response.put("data", articleJSON);

        return response;
    }

    @PostMapping("/articles")
    public Map<String, ArticleJSON> createArticle(@RequestBody Map<String, ArticleJSON> requestBody) {
        Map<String, ArticleJSON> response = new HashMap<>();

        ArticleJSON articleJSON = requestBody.get("data");
        String title = articleJSON.attributes.get("title");
        String body = articleJSON.attributes.get("body");

        response.put(
                "data",
                ArticleJSON.fromArticle(articleService.makeNewArticle(title, body))
        );

        return response;
    }

    @PutMapping("/articles/{articleId}")
    public String updateArticle(@PathVariable String articleId, @RequestBody Object requestBody) {
        return "/articles/" + articleId;
    }

    @DeleteMapping("/articles/{articleId}")
    public String deleteArticle(@PathVariable String articleId) {
        return "/articles/" + articleId;
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
