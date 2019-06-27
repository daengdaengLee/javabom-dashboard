package io.github.daengdaenglee.javabomboardwas.entity.article;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "title", column = @Column(name = "title", nullable = false)),
            @AttributeOverride(name = "content", column = @Column(name = "content", nullable = false))
    })
    private Attribute attributes;

    @Builder
    public Article(Long id, Attribute attributes) {
        this.id = id;
        this.attributes = attributes;
    }
}
