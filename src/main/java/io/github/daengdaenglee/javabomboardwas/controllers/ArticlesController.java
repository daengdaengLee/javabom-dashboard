package io.github.daengdaenglee.javabomboardwas.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ArticlesController {
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
}
