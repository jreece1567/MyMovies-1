package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Tag;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA interface to the Tag table.
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, String> {

  @Query("SELECT tag FROM Tag WHERE imdbId = ?1 ORDER BY tag")
  List<String> findAllByImdbId(String imdbId);

  @Query("SELECT imdbId FROM Tag WHERE tag = ?1")
  List<String> findAllByTag(String tag);

  @Query("SELECT COUNT(imdbId) FROM Tag WHERE tag = ?1")
  Integer countAllByTag(String tag);

  @Query("SELECT DISTINCT tag FROM Tag ORDER BY tag")
  List<String> findAllDistinctTags();

  @Query("SELECT DISTINCT tag FROM Tag WHERE tag LIKE %?1%")
  List<String> searchTags(String searchArg);

  @Transactional
  @Modifying
  @Query("DELETE FROM Tag WHERE imdbId=?1")
  void deleteTagsByImdbId(String imdbId);

}
