package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating a genre with an imdbId.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    /**
     * The name of the genre.
     */
    private String genre;

    /**
     * The imdbId of a movie associated with this genre.
     */
    private String imdbId;
}
