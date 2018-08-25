package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Episode;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EpisodeRepository extends CrudRepository<Episode, String> {

	// /episodes/{imdbId}
	List<Episode> findAllByImdbId(String imdbId);

	// /episodes/{imdbId}/{season}
	List<Episode> findAllByImdbIdAndSeason(String imdbId, Integer season);

	// todo: ??
	// /seasons/{imdbId}
	@Query("SELECT DISTINCT season from Episode where imdbId=?1")
	List<String> findDistinctSeasonsByImdbId(String imdbId);
}
