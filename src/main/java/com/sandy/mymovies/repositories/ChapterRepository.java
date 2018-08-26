package com.sandy.mymovies.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sandy.mymovies.models.domain.Chapter;

/**
 * JPA interface to Chapter table.
 */
public interface ChapterRepository extends CrudRepository<Chapter, String> {

  Optional<Chapter> findByImdbIdAndSeasonAndEpisodeNumber(String imdbId, Integer season,
      Integer episodeNumber);

  // /episodes/{imdbId}
  List<Chapter> findAllByImdbId(String imdbId);

  // /episodes/{imdbId}/{season}
  List<Chapter> findAllByImdbIdAndSeason(String imdbId, Integer season);

  // todo: ??
  // /seasons/{imdbId}
  @Query("SELECT DISTINCT season from Episode where imdbId=?1")
  List<String> findDistinctSeasonsByImdbId(String imdbId);
}
