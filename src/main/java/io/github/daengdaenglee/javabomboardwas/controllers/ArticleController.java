package io.github.daengdaenglee.javabomboardwas.controllers;

import io.github.daengdaenglee.javabomboardwas.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {
    @Autowired
    public ArticleService articleService;

    @GetMapping("/articles")
    public String listAllArticles() {
        return "/articles";
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
}
