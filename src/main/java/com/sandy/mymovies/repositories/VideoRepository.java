package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Video;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA interface to the Video table.
 */
@Repository
public interface VideoRepository extends CrudRepository<Video, String> {

  // Video.releaseYear-related methods

  List<Video> findAllByReleaseYear(Integer releaseYear);

  @Query("SELECT imdbId FROM Video WHERE releaseYear = ?1")
  List<String> findAllImdbIdsByReleaseYear(Integer releaseYear);

  @Query("SELECT DISTINCT releaseYear FROM Video")
  List<Integer> findAllDistinctReleaseYears();

  // Video.director-related methods

  List<Video> findAllByDirector(String director);

  @Query("SELECT imdbId FROM Video WHERE director = ?1")
  List<String> findAllImdbIdsByDirector(String director);

  @Query("SELECT DISTINCT director FROM Video")
  List<String> findAllDistinctDirectors();

  // Video.rating-related methods

  List<Video> findAllByRating(String rating);

  @Query("SELECT imdbId FROM Video WHERE rating = ?1")
  List<String> findAllImdbIdsByRating(String rating);

  @Query("SELECT DISTINCT rating FROM Video")
  List<String> findAllDistinctRatings();

  // Video.title-related methods

  List<Video> findAllByTitle(String title);

  @Query("SELECT imdbId FROM Video WHERE title = ?1")
  List<String> findAllImdbIdsByTitle(String title);

  @Query("SELECT DISTINCT title FROM Video")
  List<String> findAllDistinctTitles();

}
