package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Genres;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface GenreRepository extends CrudRepository<Genres,String> {

    @Query("SELECT imdbId FROM Genres where genre = ?1")
    List<String> findAllByGenre(String genre);

    @Query("SELECT DISTINCT genre FROM Genres")
    List<String> findAllDistinctGenres();
}
