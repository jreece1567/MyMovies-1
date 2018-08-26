package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating a tag with an imdbId.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    /**
     * The name of the tag.
     */
    private String tag;

    /**
     * The imdbId of a movie associated with this tag.
     */
    private String imdbId;
}
