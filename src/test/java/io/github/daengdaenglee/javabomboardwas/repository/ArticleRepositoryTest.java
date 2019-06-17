package io.github.daengdaenglee.javabomboardwas.repository;

import io.github.daengdaenglee.javabomboardwas.entity.article.article.Article;
import io.github.daengdaenglee.javabomboardwas.entity.article.article.Attribute;
import io.github.daengdaenglee.javabomboardwas.entity.article.article.Link;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleRepositoryTest {
    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void saveArticle_withArticle_success_returnArticle() {
        // given
        Article input = Article.builder()
                .attributes(Attribute.builder()
                        .title("Test Article")
                        .content("This is test article")
                        .build())
                .build();

        // when
        Article output = articleRepository.save(input);

        // then
        assertThat(output.getAttributes()).isEqualTo(input.getAttributes());
        assertThat(output.getId()).isEqualTo(output.getPk().toString());
        assertThat(output.getLinks().getSelf()).isEqualTo(
                Link.builder().self("/articles/" + output.getId()).build().getSelf()
        );
    }
}
