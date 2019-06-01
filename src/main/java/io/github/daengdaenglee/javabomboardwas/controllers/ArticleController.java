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
                .map(article -> new ArticleJSON(article))
                .collect(Collectors.toList());
        response.put("data", allArticles);

        return response;
    }

    @GetMapping("/articles/{articleId}")
    public String readArticle(@PathVariable String articleId) {
        return "/articles/" + articleId;
    }

    @PostMapping("/articles")
    public String createArticle() {
        return "/articles";
    }

    @PutMapping("/articles/{articleId}")
    public String updateArticle(@PathVariable String articleId) {
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

        public ArticleJSON(Article article) {
            this.id = article.id;
            this.attributes.put("title", article.title);
            this.attributes.put("body", article.body);
            this.links.put("self", "/api/v1/articles/" + article.id);
        }
    }
}
