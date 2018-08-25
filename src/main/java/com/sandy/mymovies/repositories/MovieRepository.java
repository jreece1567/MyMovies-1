package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovieRepository extends CrudRepository<Movie, String> {

    List<Movie> findAllByReleaseYear(Integer releaseYear);

    List<Movie> findAllByDirector(String director);

    List<Movie> findAllByRating(String rating);

    List<Movie> findAllByTitle(String title);

    @Query("SELECT DISTINCT director from Movie")
    List<String> findAllDistinctDirectors();

    @Query("SELECT DISTINCT rating from Movie")
    List<String> findAllDistinctRatings();

    @Query("SELECT DISTINCT releaseYear from Movie")
    List<Integer> findAllDistinctReleaseYears();

    @Query("SELECT DISTINCT title from Movie")
    List<String> findAllDistinctTitles();

}
