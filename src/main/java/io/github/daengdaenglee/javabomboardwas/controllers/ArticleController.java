package io.github.daengdaenglee.javabomboardwas.controllers;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;
import io.github.daengdaenglee.javabomboardwas.entities.articles.Attributes;
import io.github.daengdaenglee.javabomboardwas.entities.articles.Links;
import io.github.daengdaenglee.javabomboardwas.entities.responses.ArticleData;
import io.github.daengdaenglee.javabomboardwas.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        try {
            List<ArticleJSON> allArticles = articleService.getAllArticles().stream()
                    .map(ArticleJSON::fromArticle)
                    .collect(Collectors.toList());
            response.put("data", allArticles);
        } catch (Exception e) {
        }

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
    public ResponseEntity<ArticleData> createArticle(
            @RequestBody ArticleData requestBody
    ) throws IOException {
        Article article = articleService.makeNewArticle(requestBody.getData());
        article.getLinks().setSelf("/api/v1" + article.getLinks().getSelf());

        ArticleData articleData = new ArticleData(article);

        return new ResponseEntity<>(articleData, HttpStatus.OK);
    }

    @PutMapping("/articles/{articleId}")
    public ResponseEntity<ArticleData> updateArticle(
            @PathVariable String articleId,
            @RequestBody ArticleData requestBody
    ) throws IOException {
        Article article = requestBody.getData();
        String self = article.getLinks().getSelf();
        article.getLinks().setSelf(self.replace("/api/v1", ""));
        article = articleService.changeArticle(article);
        article.getLinks().setSelf("/api/v1" + article.getLinks().getSelf());

        ArticleData articleData = new ArticleData(article);

        return new ResponseEntity<>(articleData, HttpStatus.OK);
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable String articleId) {
        articleService.deleteArticleById(articleId);

        return new ResponseEntity(HttpStatus.OK);
    }

    public static class ArticleJSON {
        public String type = "articles";
        public String id;
        public Map<String, String> attributes = new HashMap<>();
        public Map<String, String> links = new HashMap<>();

        public static ArticleJSON fromArticle(Article article) {
            ArticleJSON articleJSON = new ArticleJSON();

            articleJSON.id = article.getId();
            articleJSON.attributes.put("title", article.getAttributes().getTitle());
            articleJSON.attributes.put("body", article.getAttributes().getBody());
            articleJSON.links.put("self", "/api/v1/articles/" + article.getId());

            return articleJSON;
        }
    }
}
