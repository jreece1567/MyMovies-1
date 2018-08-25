package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Actor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor,String> {

    List<Actor> findAllByName(String name);

    List<Actor> findAllByImdbId(String imdbId);

    @Query("SELECT DISTINCT name FROM Actor")
    List<Actor> findAllDistinctActors();
}
