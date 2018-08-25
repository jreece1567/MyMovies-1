package com.sandy.mymovies.models.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(indexes = {
        @Index(name = "IDX_IMDBID", columnList = "imdbId"),
        @Index(name = "IDX_SEASON", columnList = "season")})
@Data
public class Episode implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String imdbId;

    @Column
    private Integer season;

    @Column
    private Integer episodeNumber;

    @Column
    private String title;

    @Column
    private String description;

    public Episode(String imdbId, Integer season, Integer episodeNumber, String title,
            String description) {
        this.imdbId = imdbId;
        this.season = season;
        this.episodeNumber = episodeNumber;
        this.title = title;
        this.description = description;
    }
    // @todo - consider compound key? imdbId, season, episode number, or, use custom SQL (SELECT * FROM Episode WHERE imdbId=? AND season=? ORDER BY episodeNumber)
}
