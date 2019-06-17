package io.github.daengdaenglee.javabomboardwas.entity.article.article;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString

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

    @Transient
    final private String type = "articles";

    @Transient
    private Link links;

    @Builder
    public Article(Attribute attributes) {
        this.attributes = attributes;
    }

    @Transient
    public Link getLinks() {
        String self = new StringBuffer()
                .append("/")
                .append(this.type)
                .append("/")
                .append(this.id)
                .toString();

        return Link.builder().self(self).build();
    }
}
