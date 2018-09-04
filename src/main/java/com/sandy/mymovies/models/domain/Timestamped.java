package com.sandy.mymovies.models.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;

/**
 * Superclass that adds create/update timestamping to all tables that inherit from this class.
 */
@MappedSuperclass
@Getter
public class Timestamped {

  /**
   * The row-creation timestamp.
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date createdAt;

  /**
   * The row-updated timestamp.
   */
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updatedAt;

  /**
   * Sets the creation-timestamp. Called before the row is first created.
   */
  @PrePersist
  protected void onCreate() {
    createdAt = new Date();
    updatedAt = createdAt;
  }

  /**
   * Sets the updated-timestamp. Called before the row is updated.
   */
  @PreUpdate
  protected void onUpdate() {
    updatedAt = new Date();
  }

}
