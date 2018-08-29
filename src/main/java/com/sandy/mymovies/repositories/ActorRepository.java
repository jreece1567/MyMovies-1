package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Actor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA interface to the Actor table.
 */
@Repository
public interface ActorRepository extends CrudRepository<Actor, String> {

  @Query("SELECT imdbId FROM Actor WHERE name=?1")
  List<String> findAllByName(String name);

  @Query("SELECT name FROM Actor WHERE imdbId=?1 ORDER BY name")
  List<String> findAllByImdbId(String imdbId);

  @Query("SELECT DISTINCT name FROM Actor ORDER BY name")
  List<String> findAllDistinctActors();

  @Query("SELECT name FROM Actor WHERE name LIKE %?1%")
  List<String> searchActors(String searchArg);

  @Query("DELETE FROM Actor WHERE imdbId=?1")
  void deleteActorsByImdbId(String imdbId);
}
