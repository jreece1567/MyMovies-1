package com.sandy.mymovies.models.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(indexes = {
        @Index(name = "IDX_TITLE", columnList = "title"),
        @Index(name = "IDX_RELEASE_YEAR", columnList = "releaseYear"),
        @Index(name = "IDX_RATING", columnList = "rating"),
        @Index(name = "IDX_DIRECTOR", columnList = "director")})
@Data
//@AllArgsConstructor
public class Movie implements Serializable {

    @Id
    private String imdbId;

    @Column
    private String title;

    @Column
    private Integer releaseYear;

    @Column
    private String duration;

    @Column
    private String rating;

    @Column
    private String director;

    @Column
    private String imageUrl;

    @Column
    private String description;

    //@ManyToOne
    //private Genre genre;

    //@ManyToOne
    //private Tag tag;

    //@Column
    //private Cast cast;

    public Movie(String imdbId, String title, Integer releaseYear, String duration,
            String rating, String director, String imageUrl, String description) {
        this.imdbId = imdbId;
        this.title = title;
        this.releaseYear = releaseYear;
        this.duration = duration;
        ;
        this.rating = rating;
        this.director = director;
        this.imageUrl = imageUrl;
        this.description = description;
    }
}
