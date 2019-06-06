package io.github.daengdaenglee.javabomboardwas.repositories;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.daengdaenglee.javabomboardwas.entities.articles.Article;
import io.github.daengdaenglee.javabomboardwas.entities.articles.Links;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ArticleRepositoryImpl implements ArticleRepository {
    @Value("${ARTICLES_STORE_PATH}")
    public String storePath;

    public Article insert(Article article) throws IOException {
        int id = 0;

        for (File articleFile : new File(storePath).listFiles()) {
            String articleFileStr = articleFile.getName();
            int idxExtension = articleFileStr.lastIndexOf(".json");
            String articleIdStr = articleFileStr.substring(0, idxExtension);
            int articleIdInt = Integer.parseInt(articleIdStr);
            if (articleIdInt > id) id = articleIdInt;
        }
        id += 1;

        String articleId = Integer.toString(id);
        article = Article.builder()
                .id(articleId)
                .attributes(article.getAttributes())
                .links(new Links("/articles/" + articleId))
                .type(article.getType())
                .build();

        String fileContents = new ObjectMapper().writeValueAsString(article);
        FileWriter fileWriter = new FileWriter(storePath + "/" + articleId + ".json", false);
        fileWriter.write(fileContents);
        fileWriter.flush();
        fileWriter.close();

        return article;
    }

    public Article selectById(String id) throws IOException {
        File articleFile = new File(storePath + "/" + id + ".json");
        String json = readArticleFileContent(articleFile);
        Article article = new ObjectMapper().readerFor(Article.class).readValue(json);

        return article;
    }

    public List<Article> selectAll() throws IOException {
        List<Article> allArticles = new ArrayList<>();

        for (File articleFile : new File(storePath).listFiles()) {
            String json = readArticleFileContent(articleFile);
            Article article = new ObjectMapper().readerFor(Article.class).readValue(json);
            allArticles.add(article);
        }

        return allArticles.stream()
                .sorted((a, b) -> {
                    int id1 = Integer.parseInt(a.getId());
                    int id2 = Integer.parseInt(b.getId());
                    return id1 - id2;
                })
                .collect(Collectors.toList());
    }

    public Article update(Article article) throws IOException {
        String fileContents = new ObjectMapper().writeValueAsString(article);
        FileWriter fileWriter = new FileWriter(storePath + "/" + article.getId() + ".json", false);
        fileWriter.write(fileContents);
        fileWriter.flush();
        fileWriter.close();

        return article;
    }

    public Article deleteById(String id) throws IOException {
        File articleFile = new File(storePath + "/" + id + ".json");
        String json = readArticleFileContent(articleFile);
        Article article = new ObjectMapper().readerFor(Article.class).readValue(json);

        boolean isDeleted = articleFile.delete();

        return isDeleted ? article : null;
    }

    public String readArticleFileContent(File articleFile) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(articleFile));

        String line = bufferedReader.readLine();
        String json = "";
        while (line != null) {
            json += line;
            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        return json;
    }
}

