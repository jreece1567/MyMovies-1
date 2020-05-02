package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Chapter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA interface to the Chapter table.
 */
@Repository
public interface ChapterRepository extends CrudRepository<Chapter, String> {

  Optional<Chapter> findByImdbIdAndSeasonAndEpisodeNumber(String imdbId, Integer season,
      Integer episodeNumber);

  List<Chapter> findAllByImdbId(String imdbId);

  List<Chapter> findAllByImdbIdAndSeason(String imdbId, Integer season);

  @Query("SELECT DISTINCT season FROM Chapter WHERE imdbId=?1 ORDER BY season")
  List<Integer> findDistinctSeasonsByImdbId(String imdbId);

  @Query("DELETE FROM Chapter WHERE imdbId=?1")
  void deleteChaptersByImdbId(String imdbId);

  @Query("DELETE FROM Chapter WHERE imdbId=?1 AND season=?2")
  void deleteChaptersByImdbIdAndSeason(String imdbId, Integer season);

  @Query("DELETE FROM Chapter WHERE imdbId=?1 AND season=?2 AND episodeNumber=?3")
  void deleteChaptersByImdbIdAndSeasonAndEpisodeNumber(String imdbId, Integer season,
      Integer episodeNumber);

}
