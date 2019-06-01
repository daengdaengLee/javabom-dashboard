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
    public Map<String, ArticleJSON> readArticle(@PathVariable String articleId) {
        Map<String, ArticleJSON> response = new HashMap<>();

        ArticleJSON article = new ArticleJSON(articleService.getArticleById(articleId));
        response.put("data", article);

        return response;
    }

    @PostMapping("/articles")
    public Map<String, ArticleJSON> createArticle(@RequestBody Object requestBody) {
        Map<String, ArticleJSON> response = new HashMap<>();

        Map data = (Map) ((Map) requestBody).get("data");
        Map attributes = (Map) data.get("attributes");
        String title = (String) attributes.get("title");
        String body = (String) attributes.get("body");

        ArticleJSON article = new ArticleJSON(articleService.makeNewArticle(title, body));
        response.put("data", article);

        return response;
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
