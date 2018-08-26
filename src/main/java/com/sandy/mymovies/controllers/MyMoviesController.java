package com.sandy.mymovies.controllers;

import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Episode;
import com.sandy.mymovies.models.dto.Index;
import com.sandy.mymovies.models.dto.Key;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.models.dto.Title;
import com.sandy.mymovies.services.MyMoviesService;
import java.util.List;
import java.util.NoSuchElementException;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyMoviesController {

    private MyMoviesService service;

    // -- Basic crud endpoints --
    //      /title/{imdbId}
    //      /titles/{index}/{key}
    //      /movies/{imdbId}
    //      /movies/{index}
    //      /cast/{imdbId}
    //      /episodes/{imdbId}/{season}
    //      /episodes/{imdbId}
    //      /season/{imdbId}
    // -- Index endpoints --
    //      /index/keys/{name}
    //      /index/{name}
    //      /index/{name}/{key}

    @Autowired
    public MyMoviesController(MyMoviesService service) {

        this.service = service;
    }

    /**
     * Exception handler if IllegalArgumentException is thrown
     *
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(IllegalArgumentException.class)
    public String return422(IllegalArgumentException ex) {

        return ex.getMessage();
    }

    /**
     * Exception handler if NoSuchElementException is thrown
     *
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return404(NoSuchElementException ex) {

        return ex.getMessage();
    }

    /**
     * Exception handler if NotYetImplementedException is thrown
     *
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(NotYetImplementedException.class)
    public String return501(NotYetImplementedException ex) {

        return ex.getMessage();
    }

    /**
     * @return Individual movie
     */
    @RequestMapping(method = RequestMethod.GET, path = "/movie/{imdbId}")
    @ResponseStatus(HttpStatus.OK)
    public Movie getMovieByImdbId(@PathVariable("imdbId") String imdbId) {

        return service.readMovie(imdbId);
    }

    /**
     * @return Individual movie title
     */
    @RequestMapping(method = RequestMethod.GET, path = "/title/{imdbId}")
    @ResponseStatus(HttpStatus.OK)
    public Title getTitleByImdbId(@PathVariable("imdbId") String imdbId) {

        return service.readTitle(imdbId);
    }

    /**
     * Used where 'key' can be a path param.
     *
     * @return Titles associated with an index and key.
     */
    @RequestMapping(method = RequestMethod.GET, path = "{/titles/{index}/{key}")
    public List<Title> getTitlesByIndexAndKey(@PathVariable("index") String index,
        @PathVariable("key") String key) {

        Index idx = Index
            .valueOf(index);   // will throw IllegalArgumentException if the 'index' is not valid
        return service.readTitlesByIndexAndKey(idx, key);
    }

    /**
     * Used where the 'key' cannot be a path-param and instead is a query-param named 'name'.
     *
     * @return Titles associated with an index and key.
     */
    @RequestMapping(method = RequestMethod.GET, path = "{/titles/{index}")
    public List<Title> getTitlesByIndexAndName(@PathVariable("index") String index,
        @RequestParam("name") String key) {

        Index idx = Index
            .valueOf(index);   // will throw IllegalArgumentException if the 'index' is not valid
        return service.readTitlesByIndexAndKey(idx, key);
    }

    /**
     * @return Cast associated with a single movie (list of strings)
     */
    @RequestMapping(method = RequestMethod.GET, path = "{/cast/{imdbId}}")
    @ResponseStatus(HttpStatus.OK)
    public Cast getCastByImdbId(@PathVariable("imdbId") String imdbId) {

        return service.readCast(imdbId);
    }

    /**
     * @return All episodes associated with a show
     */
    @RequestMapping(method = RequestMethod.GET, path = "/episodes/{imdbId}")
    @ResponseStatus(HttpStatus.OK)
    public List<Episode> getEpisodesByImdbId(@PathVariable("imdbId") String imdbId) {

        return service.readEpisodes(imdbId);
    }

    /**
     * @return Episodes associated with a show and season
     */
    @RequestMapping(method = RequestMethod.GET, path = "/episodes/{imdbId}/{seasonNumber}")
    @ResponseStatus(HttpStatus.OK)
    public List<Episode> getEpisodesByImdbIdAndSeason(@PathVariable("imdbId") String imdbId,
        @PathVariable("seasonNumber") Integer seasonNumber) {

        return service.readEpisodes(imdbId, seasonNumber);
    }

    /**
     * @return A list of seasons associated with a show
     */
    @RequestMapping(method = RequestMethod.GET, path = "/seasons/{imdbId}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getSeasonsByImdbId(@PathVariable("imdbId") String imdbId) {

        return service.readSeasons(imdbId);
    }

    /**
     * @return A list of keys associated with an index-name
     */
    @RequestMapping(method = RequestMethod.GET, path = "/index/keys/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getKeysByIndex(@PathVariable("name") String name) {

        Index idx = Index.valueOf(name);
        return service.readIndex(idx);
    }

    /**
     * @return A list of keys and imdbIds associated with an index-name
     */
    @RequestMapping(method = RequestMethod.GET, path = "/index/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Key> getIdsByIndex(@PathVariable("name") String name) {

        Index idx = Index.valueOf(name);
        return service.readIdsByIndex(idx);
    }

    /**
     * @return A list of titles associated with an index-name and key-value.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/index/{name}/{key}")
    @ResponseStatus(HttpStatus.OK)
    public List<Title> getIdsByIndex(@PathVariable("name") String name,
        @PathVariable("key") String key) {

        Index idx = Index.valueOf(name);
        return service.readTitlesByIndexAndKey(idx, key);
    }

    /**
     * @return A search result for a search on an index.
     */
    @RequestMapping(method = RequestMethod.GET, path = "/search/{index}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> searchByIndex(@PathVariable("index") String index,
        @RequestParam("q") String query) {

        Index idx = Index.valueOf(index);
        return service.searchByIndex(idx, query);
    }

    // -- Remaining endpoints --
    //      /count/{index}

}
