package com.sandy.mymovies.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sandy.mymovies.models.domain.Actor;

/**
 * JPA interface to Actor table.
 */
public interface ActorRepository extends CrudRepository<Actor, String> {

  @Query("SELECT imdbId FROM Actor WHERE name=?1")
  List<String> findAllByName(String name);

  @Query("SELECT name FROM Actor WHERE imdbId=?1")
  List<String> findAllByImdbId(String imdbId);

  @Query("SELECT DISTINCT name FROM Actor")
  List<String> findAllDistinctActors();
}
