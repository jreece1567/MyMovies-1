package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Genre;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA interface to the Genre table.
 */
@Repository
public interface GenreRepository extends CrudRepository<Genre, String> {

  @Query("SELECT genre FROM Genre WHERE imdbId = ?1 ORDER BY genre")
  List<String> findAllByImdbId(String imdbId);

  @Query("SELECT imdbId FROM Genre WHERE genre = ?1")
  List<String> findAllByGenre(String genre);

  @Query("SELECT COUNT(imdbId) FROM Genre WHERE genre = ?1")
  Integer countAllByGenre(String genre);

  @Query("SELECT DISTINCT genre FROM Genre ORDER BY genre")
  List<String> findAllDistinctGenres();

  @Query("SELECT DISTINCT genre FROM Genre WHERE genre LIKE %?1%")
  List<String> searchGenres(String searchArg);

  @Transactional
  @Modifying
  @Query("DELETE FROM Genre WHERE imdbId=?1")
  void deleteGenresByImdbId(String imdbId);

}
