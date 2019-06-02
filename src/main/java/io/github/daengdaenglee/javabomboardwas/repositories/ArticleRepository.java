package io.github.daengdaenglee.javabomboardwas.repositories;

import io.github.daengdaenglee.javabomboardwas.entities.Article;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
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
            int idxExtension = articleFileStr.lastIndexOf(".json");
            String articleIdStr = articleFileStr.substring(0, idxExtension);
            int articleIdInt = Integer.parseInt(articleIdStr);
            if (articleIdInt > id) id = articleIdInt;
        }
        id += 1;

        String articleId = Integer.toString(id);
        String fileContents = new StringBuffer()
                .append("{")
                .append("\"id\":\"")
                .append(id)
                .append("\",\"title\":\"")
                .append(article.title)
                .append("\",\"body\":")
                .append(article.body)
                .append("\"")
                .append("}")
                .toString();

        FileWriter fileWriter = new FileWriter(storePath + "/" + articleId + ".json", false);
        fileWriter.write(fileContents);
        fileWriter.flush();
        fileWriter.close();

        return new Article(articleId, article.title, article.body);
    }

    public Article selectById(String id) throws IOException {
        File articleFile = new File(storePath + "/" + id + ".txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(articleFile));

        String line = bufferedReader.readLine();
        String title = "";
        String body = "";
        boolean isTitle = true;
        while (line != null) {
            if (isTitle) title += line;
            else body += line;

            if (line.isEmpty()) isTitle = false;

            line = bufferedReader.readLine();
        }
        bufferedReader.close();

        return new Article(id, title, body);
    }

    public List<Article> selectAll() {
        return new ArrayList<>();
    }

    public Article update(Article article) throws IOException {
        String fileContents = new StringBuffer()
                .append("{")
                .append("\"id\":\"")
                .append(article.id)
                .append("\",\"title\":\"")
                .append(article.title)
                .append("\",\"body\":")
                .append(article.body)
                .append("\"")
                .append("}")
                .toString();

        FileWriter fileWriter = new FileWriter(storePath + "/" + article.id + ".json", false);

        fileWriter.write(fileContents);
        fileWriter.flush();
        fileWriter.close();

        return article;
    }

    public void deleteById(String id) throws Exception {
        File articleFile = new File(storePath + "/" + id + ".txt");
        boolean isDeleted = articleFile.delete();
        if (!isDeleted) throw new Exception("Cannot delete file");
    }
}
