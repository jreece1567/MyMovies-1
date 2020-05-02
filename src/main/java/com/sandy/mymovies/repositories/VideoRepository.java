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

  @Query("SELECT COUNT(imdbId) FROM Video WHERE releaseYear = ?1")
  Integer countAllByReleaseYear(Integer releaseYear);

  @Query("SELECT DISTINCT releaseYear FROM Video ORDER BY releaseYear")
  List<Integer> findAllDistinctReleaseYears();

  @Query("SELECT releaseYear FROM Video WHERE releaseYear = ?1")
  List<Integer> searchReleaseYears(Integer searchArg);

  // Video.director-related methods

  List<Video> findAllByDirector(String director);

  @Query("SELECT imdbId FROM Video WHERE director = ?1")
  List<String> findAllImdbIdsByDirector(String director);

  @Query("SELECT COUNT(imdbId) FROM Video WHERE director = ?1")
  Integer countAllByDirector(String director);

  @Query("SELECT DISTINCT director FROM Video ORDER BY director")
  List<String> findAllDistinctDirectors();

  @Query("SELECT DISTINCT director FROM Video WHERE director LIKE %?1%")
  List<String> searchDirectors(String searchArg);

  // Video.rating-related methods

  List<Video> findAllByRating(String rating);

  @Query("SELECT imdbId FROM Video WHERE rating = ?1")
  List<String> findAllImdbIdsByRating(String rating);

  @Query("SELECT COUNT(imdbId) FROM Video WHERE rating = ?1")
  Integer countAllByRating(String rating);

  @Query("SELECT DISTINCT rating FROM Video ORDER BY rating")
  List<String> findAllDistinctRatings();

  @Query("SELECT DISTINCT rating FROM Video WHERE rating LIKE %?1%")
  List<String> searchRatings(String searchArg);

  // Video.title-related methods

  List<Video> findAllByTitle(String title);

  @Query("SELECT imdbId FROM Video WHERE title = ?1")
  List<String> findAllImdbIdsByTitle(String title);

  @Query("SELECT COUNT(imdbId) FROM Video WHERE title = ?1")
  Integer countAllByTitle(String title);

  @Query("SELECT DISTINCT title FROM Video ORDER BY title")
  List<String> findAllDistinctTitles();

  @Query("SELECT DISTINCT title FROM Video WHERE title LIKE %?1%")
  List<String> searchTitles(String searchArg);

  @Query("DELETE FROM Video WHERE imdbId=?1")
  void deleteVideosByImdbId(String imdbId);

}
