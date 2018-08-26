package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Chapter;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA interface to Chapter table.
 */
@Repository
public interface ChapterRepository extends CrudRepository<Chapter, String> {

  Optional<Chapter> findByImdbIdAndSeasonAndEpisodeNumber(String imdbId, Integer season,
      Integer episodeNumber);

  List<Chapter> findAllByImdbId(String imdbId);

  List<Chapter> findAllByImdbIdAndSeason(String imdbId, Integer season);

  @Query("SELECT DISTINCT season FROM Chapter WHERE imdbId=?1")
  List<String> findDistinctSeasonsByImdbId(String imdbId);
}
