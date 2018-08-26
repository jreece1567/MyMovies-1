package com.sandy.mymovies.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sandy.mymovies.models.domain.Tag;

/**
 * JPA interface to Tag table.
 */
public interface TagRepository extends CrudRepository<Tag, String> {

  @Query("SELECT tag FROM Tag WHERE imdbId = ?1")
  List<String> findAllByImdbId(String imdbId);

  @Query("SELECT imdbId FROM Tag WHERE tag = ?1")
  List<String> findAllByTag(String tag);

  @Query("SELECT DISTINCT tag FROM Tag")
  List<String> findAllDistinctTags();

}
