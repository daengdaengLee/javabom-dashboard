package io.github.daengdaenglee.javabomboardwas.repositories;

import io.github.daengdaenglee.javabomboardwas.entities.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ArticleRepository {
    @Value("${ARTICLES_STORE_PATH}")
    public String storePath;

    public Article insert(Article article) throws IOException {
        int id = 0;

        for (File articleFile : new File(storePath).listFiles()) {
            String articleFileStr = articleFile.getName();
            int idxExtension = articleFileStr.lastIndexOf(".txt");
            String articleIdStr = articleFileStr.substring(0, idxExtension);
            int articleIdInt = Integer.parseInt(articleIdStr);
            if (articleIdInt > id) id = articleIdInt;
        }
        id += 1;

        String articleId = Integer.toString(id);
        String fileContents = article.title + "\n\n" + article.body;

        FileWriter fileWriter = new FileWriter(storePath + "/" + articleId + ".txt", false);
        fileWriter.write(fileContents);
        fileWriter.flush();
        fileWriter.close();

        return new Article(articleId, article.title, article.body);
    }

    public Article selectById(String id) {
        return new Article(id, null, null);
    }

    public List<Article> selectAll() {
        return new ArrayList<>();
    }

    public Article update(Article article) {
        return article;
    }

    public void deleteById(String id) {
    }
}
