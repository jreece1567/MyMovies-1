package com.sandy.mymovies.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sandy.mymovies.models.domain.Genre;

/**
 * JPA interface to Genre table.
 */
public interface GenreRepository extends CrudRepository<Genre, String> {

  @Query("SELECT genre FROM Genre WHERE imdbId = ?1")
  List<String> findAllByImdbId(String imdbId);

  @Query("SELECT imdbId FROM Genre WHERE genre = ?1")
  List<String> findAllByGenre(String genre);

  @Query("SELECT DISTINCT genre FROM Genre")
  List<String> findAllDistinctGenres();

}
