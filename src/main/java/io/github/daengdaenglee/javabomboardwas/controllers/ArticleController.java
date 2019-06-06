package io.github.daengdaenglee.javabomboardwas.controllers;

import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;
import io.github.daengdaenglee.javabomboardwas.entities.requests.DataRequest;
import io.github.daengdaenglee.javabomboardwas.entities.responses.DataResponse;
import io.github.daengdaenglee.javabomboardwas.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<DataResponse<List<Article>>> listAllArticles() throws Exception {
        List<Article> articles = articleService.getAllArticles().stream()
                .map(article -> {
                    article.getLinks().setSelf("/api/v1" + article.getLinks().getSelf());
                    return article;
                })
                .collect(Collectors.toList());
        DataResponse<List<Article>> dataResponse = new DataResponse<>(articles);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @GetMapping("/articles/{articleId}")
    public ResponseEntity<DataResponse<Article>> readArticle(@PathVariable String articleId) throws Exception {
        Article article = articleService.getArticleById(articleId);
        DataResponse<Article> dataResponse = new DataResponse<>(article);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @PostMapping("/articles")
    public ResponseEntity<DataResponse<Article>> createArticle(
            @RequestBody DataRequest<Article> requestBody
    ) throws Exception {
        Article article = requestBody.getData();
        article = articleService.makeNewArticle(article);
        article.getLinks().setSelf("/api/v1" + article.getLinks().getSelf());

        DataResponse<Article> dataResponse = new DataResponse<>(article);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @PutMapping("/articles/{articleId}")
    public ResponseEntity<DataResponse<Article>> updateArticle(
            @PathVariable String articleId,
            @RequestBody DataRequest<Article> requestBody
    ) throws Exception {
        Article article = requestBody.getData();
        String self = article.getLinks().getSelf();
        article.getLinks().setSelf(self.replace("/api/v1", ""));

        article = articleService.changeArticle(article);

        article.getLinks().setSelf("/api/v1" + article.getLinks().getSelf());

        DataResponse<Article> dataResponse = new DataResponse<>(article);

        return new ResponseEntity<>(dataResponse, HttpStatus.OK);
    }

    @DeleteMapping("/articles/{articleId}")
    public ResponseEntity deleteArticle(@PathVariable String articleId) throws Exception {
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
