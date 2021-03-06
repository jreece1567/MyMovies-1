package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Bean associating a tag with an imdbId.
 */
@Entity
@Table(indexes = {@Index(name = "IDX_TAG_TAG", columnList = "tag"),
    @Index(name = "IDX_TAG_IMDBID", columnList = "imdbId")})
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Tag extends Timestamped {

  /**
   * The unique row-id.
   */
  @Id
  @GeneratedValue
  private Long id;

  /**
   * The name of the tag.
   */
  private String tag;

  /**
   * The imdbId of a movie associated with this tag.
   */
  private String imdbId;

  /**
   * All-args constructor (can't use lombok since this bean has an auto-generated 'id' field).
   *
   * @param tag The tag name.
   * @param imdbId The imdbId of a movie associated with this tag.
   */
  public Tag(final String tag, final String imdbId) {
    this.tag = tag;
    this.imdbId = imdbId;
  }

}
