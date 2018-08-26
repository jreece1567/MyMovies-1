package com.sandy.mymovies.models.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating an episode with an imdbId and season.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Episode implements Serializable {

    /**
     * The imdbId of a show in which the episode appears.
     */
    private String imdbId;

    /**
     * The season of a show in which the episode appears.
     */
    private Integer season;

    /**
     * The episode number (within a season) in which the episode appears.
     */
    private Integer episodeNumber;

    /**
     * The title of the episode.
     */
    private String title;

    /**
     * The description the episode.
     */
    private String description;

}
