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
    @Column(name = "primary_key", updatable = false)
    private Long pk;

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

    @Transient
    private String id;

    @Builder
    public Article(Long pk, Attribute attributes) {
        this.pk = pk;
        this.attributes = attributes;
    }

    @Transient
    public Link getLinks() {
        String self = new StringBuffer()
                .append("/")
                .append(this.type)
                .append("/")
                .append(this.getId())
                .toString();

        return Link.builder().self(self).build();
    }

    @Transient
    public String getId() {
        return this.pk.toString();
    }
}
