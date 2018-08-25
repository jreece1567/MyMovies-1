package com.sandy.mymovies.controllers;

import com.sandy.mymovies.models.domain.Episode;
import com.sandy.mymovies.models.domain.Movie;
import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Index;
import com.sandy.mymovies.models.dto.Title;
import com.sandy.mymovies.services.MyMoviesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMoviesController {

    private MyMoviesService service;

    // -- Basic crud endpoints --
    //		/title/{imdbId}
    //		/titles/{index}/{key}
    // 		/movies/{imdbId}
    //		/movies/{index}
    // 		/cast/{imdbId}
    // 		/episodes/{imdbId}/{season}
    // 		/episodes/{imdbId}
    // 		/season/{imdbId}
    // -- Index endpoints --
    //		/index/keys/{name}
    //		/index/{name}
    //		/index/{name}/{key}
    @Autowired
    public MyMoviesController(MyMoviesService service) {
        this.service = service;
    }

    /**
     * @return Individual movie
     */
    @RequestMapping(method = RequestMethod.GET, path = "/movie/{imdbId}")
    @ResponseStatus(HttpStatus.OK)
    public Movie getMovieByImdbId(@PathVariable("imdbId") String imdbId) {
        return service.verifyMovie(imdbId);
    }

    /**
     * @return Individual movie title
     */
    @RequestMapping(method = RequestMethod.GET, path = "/title/{imdbId}")
    @ResponseStatus(HttpStatus.OK)
    public Title getTitleByImdbId(@PathVariable("imdbId") String imdbId) {
        return service.verifyTitle(imdbId);
    }

	/**
	 * @return Titles associated with an index
	 * @param index
	 * @param key
	 */
	@RequestMapping(method = RequestMethod.GET, path = "{/titles/{index}/{key}")
	public List<Title> getTitlesByIndexAndKey(@PathVariable("index") String index, @PathVariable("key") String key)
	{
        Index idx = Index.valueOf(index);   // will throw IllegalArgumentException if the 'index' is not valid
		return service.verifyTitlesByIndexAndKey(idx, key);
	}

	/**
	 * @return Cast associated with a single movie (list of strings)
	 * @param imdbId
	 */
	@RequestMapping(method = RequestMethod.GET, path = "{/cast/{imdbId}}")
	@ResponseStatus(HttpStatus.OK)
	public Cast getCastByImdbId(@PathVariable("imdbId") String imdbId) {
		return service.verifyCast(imdbId);
	}

    /**
     * @return All episodes associated with a show
     */
    @RequestMapping(method = RequestMethod.GET, path = "/episodes/{imdbId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Episode> getEpisodesByImdbId(@PathVariable("imdbId") String imdbId) {
        return service.verifyEpisodes(imdbId);
    }

	/**
	 * @return Episodes associated with a show and season
	 * @param imdbId
	 */
	@RequestMapping(method = RequestMethod.GET, path = "/episodes/{imdbId}/{seasonNumber}")
	@ResponseStatus(HttpStatus.OK)
	public List<Episode> getEpisodesByImdbIdAndSeason(@PathVariable("imdbId") String imdbId, @PathVariable("seasonNumber") Integer seasonNumber) {
		return service.verifyEpisodes(imdbId, seasonNumber);
	}

    /**
     * @return A list of seasons associated with a show
     */
    @RequestMapping(method = RequestMethod.GET, path = "/seasons/{imdbId}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSeasonsByImdbId(@PathVariable("imdbId") String imdbId) {
        return service.verifyImdbAndReturnSeasons(imdbId);
    }

    /**
     * @return A list of keys associated with an index
     */
    @RequestMapping(method = RequestMethod.GET, path = "/index/keys/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getKeysByIndex(@PathVariable("name") String name) {
        Index idx = Index.valueOf(name);
        return service.verifyIndex(idx);
    }

    // -- Remaining endpoints --
    //		/index/keys/{name}
    //		/index/{name}
    //		/index/{name}/{key}
    //		/search/{index}
    //		/count/{index}

}
