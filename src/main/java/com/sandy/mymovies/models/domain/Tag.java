package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean associating a tag with an imdbId.
 */
@Entity
@Table(indexes = {@Index(name = "IDX_TAG", columnList = "tag"),
    @Index(name = "IDX_IMDBID", columnList = "imdbId")})
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
