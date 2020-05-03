package com.sandy.mymovies.controllers;

import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Count;
import com.sandy.mymovies.models.dto.Episode;
import com.sandy.mymovies.models.dto.Index;
import com.sandy.mymovies.models.dto.Key;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.models.dto.Title;
import com.sandy.mymovies.services.MyMoviesService;
import com.sandy.mymovies.validators.MyMoviesValidator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provides HTTP/JSON 'transport layer' support for the application.
 */
@RestController
public class MyMoviesController {

  private final MyMoviesService service;
  private final MyMoviesValidator validator;

  /**
   * Construct a new controller instance.
   *
   * @param service the 'service' layer
   * @param validator the JSR-303 bean-validator
   */
  @Autowired
  public MyMoviesController(final MyMoviesService service, final MyMoviesValidator validator) {

    this.service = service;
    this.validator = validator;
  }

  /**
   * Exception handler if IllegalArgumentException is thrown.
   *
   * @param ex the thrown exception.
   * @return Error message String.
   */
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  @ExceptionHandler(IllegalArgumentException.class)
  public String return422(final IllegalArgumentException ex) {

    return ex.getMessage();
  }

  /**
   * Exception handler if NoSuchElementException is thrown.
   *
   * @param ex the thrown exception.
   * @return Error message String.
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public String return404(final NoSuchElementException ex) {

    return ex.getMessage();
  }

  /**
   * Exception handler if NotYetImplementedException is thrown.
   *
   * @param ex the thrown exception.
   * @return Error message String.
   */
  @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
  @ExceptionHandler(NotYetImplementedException.class)
  public String return501(final NotYetImplementedException ex) {

    return ex.getMessage();
  }

  /**
   * Create a Movie.
   *
   * @param movie the movie to be created.
   * @return The created movie.
   */
  @RequestMapping(method = RequestMethod.POST, path = "/movie")
  @ResponseStatus(HttpStatus.OK)
  public Movie postMovie(@RequestBody() final Movie movie) {

    final Errors errors = new BeanPropertyBindingResult(movie, movie.getClass().getName());
    validator.validate(movie, errors);
    MyMoviesValidator.processErrors(errors);

    return service.createMovie(movie);
  }

  /**
   * Fetch a Movie.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the found movie.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/movie/{imdbId}")
  @ResponseStatus(HttpStatus.OK)
  public Movie getMovieByImdbId(@PathVariable("imdbId") final String imdbId) {

    return service.readMovie(imdbId);
  }

  /**
   * Delete a Movie. Note that if this movie has Episodes, those will also be deleted.
   *
   * @param imdbId the unique IMDB-id of the movie.
   */
  @RequestMapping(method = RequestMethod.DELETE, path = "/movie/{imdbId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteMovieByImdbId(@PathVariable("imdbId") final String imdbId) {

    service.deleteMovie(imdbId);
  }

  /**
   * Fetch a Title.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the found title.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/title/{imdbId}")
  @ResponseStatus(HttpStatus.OK)
  public Title getTitleByImdbId(@PathVariable("imdbId") final String imdbId) {

    return service.readTitle(imdbId);
  }

  /**
   * Fetch a list of Movies associated with a given Index and key-value. Used where 'key' can be a
   * path param.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @param key the key value (a genre, a rating, a tag, etc.).
   * @return Titles associated with an index and key.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/movies/{index}/{key}")
  public List<Movie> getMoviesByIndexAndKey(@PathVariable("index") final String index,
      @PathVariable("key") final String key) {

    return service.readMoviesByIndexAndKey(Index.fromValue(index), key);
  }

  /**
   * Fetch a list of Movies associated with a given Index and key-value. Used where 'key' can be a
   * path param.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @param key the key value (a genre, a rating, a tag, etc.).
   * @return Titles associated with an index and key.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/movies/{index}")
  public List<Movie> getMoviesByIndexAndName(@PathVariable("index") final String index,
      @RequestParam("name") final String key) {

    return service.readMoviesByIndexAndKey(Index.fromValue(index), key);
  }

  /**
   * Fetch a list of Titles associated with a given Index and key-value. Used where 'key' can be a
   * path param.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @param key the key value (a genre, a rating, a tag, etc.).
   * @return Titles associated with an index and key.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/titles/{index}/{key}")
  public List<Title> getTitlesByIndexAndKey(@PathVariable("index") final String index,
      @PathVariable("key") final String key) {

    return service.readTitlesByIndexAndKey(Index.fromValue(index), key);
  }

  /**
   * Fetch a list of Titles associated with a given Index and key-value. Used where the 'key' cannot
   * be a path-param and instead is a query-param named 'name'.
   *
   * @param index the index name (actor,director,genre,rating,tag,title,year,etc.).
   * @param key the key value (a genre, a rating, a tag, etc.).
   * @return Titles associated with an index and key.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/titles/{index}")
  public List<Title> getTitlesByIndexAndName(@PathVariable("index") final String index,
      @RequestParam("name") final String key) {

    return service.readTitlesByIndexAndKey(Index.fromValue(index), key);
  }

  /**
   * Fetch the cast (list of actor-names) for a given imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return Cast associated with a single movie (list of strings)
   */
  @RequestMapping(method = RequestMethod.GET, path = "/cast/{imdbId}")
  @ResponseStatus(HttpStatus.OK)
  public Cast getCastByImdbId(@PathVariable("imdbId") final String imdbId) {

    return service.readCast(imdbId);
  }

  /**
   * Create an Episode.
   *
   * @param episode the Episode to be created.
   * @return The created episode.
   */
  @RequestMapping(method = RequestMethod.POST, path = "/episodes")
  @ResponseStatus(HttpStatus.OK)
  public Episode postEpisode(@RequestBody() final Episode episode) {

    final Errors errors = new BeanPropertyBindingResult(episode, episode.getClass().getName());
    validator.validate(episode, errors);
    MyMoviesValidator.processErrors(errors);

    return service.createEpisode(episode);
  }

  /**
   * Fetch all Episodes associated with a given imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return All episodes associated with a show.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/episodes/{imdbId}")
  @ResponseStatus(HttpStatus.OK)
  public List<Episode> getEpisodesByImdbId(@PathVariable("imdbId") final String imdbId) {

    return service.readEpisodes(imdbId);
  }

  /**
   * Delete all Episodes associated with a given imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   */
  @RequestMapping(method = RequestMethod.DELETE, path = "/episodes/{imdbId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEpisodesByImdbId(@PathVariable("imdbId") final String imdbId) {

    service.deleteEpisodes(imdbId);
  }

  /**
   * Fetch all Episodes associated with a given imdbId and season.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @param seasonNumber the season number.
   * @return Episodes associated with a show and season.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/episodes/{imdbId}/{seasonNumber}")
  @ResponseStatus(HttpStatus.OK)
  public List<Episode> getEpisodesByImdbIdAndSeason(@PathVariable("imdbId") final String imdbId,
      @PathVariable("seasonNumber") final Integer seasonNumber) {

    return service.readEpisodes(imdbId, seasonNumber);
  }

  /**
   * Delete all Episodes associated with a given imdbId and season.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @param seasonNumber the season number.
   */
  @RequestMapping(method = RequestMethod.DELETE, path = "/episodes/{imdbId}/{seasonNumber}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEpisodesByImdbIdAndSeason(@PathVariable("imdbId") final String imdbId,
      @PathVariable("seasonNumber") final Integer seasonNumber) {

    service.deleteEpisodes(imdbId, seasonNumber);
  }

  /**
   * Delete all Episodes associated with a given imdbId and season and episodeNumber.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @param seasonNumber the season number.
   */
  @RequestMapping(method = RequestMethod.DELETE,
      path = "/episodes/{imdbId}/{seasonNumber}/{episodeNumber}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEpisodesByImdbIdAndSeasonAndEpisodeNumber(
      @PathVariable("imdbId") final String imdbId,
      @PathVariable("seasonNumber") final Integer seasonNumber,
      @PathVariable("episodeNumber") final Integer episodeNumber) {

    service.deleteEpisodes(imdbId, seasonNumber, episodeNumber);
  }

  /**
   * Fetch all seasons (list of season numbers) associated with a given imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return A list of seasons associated with a show.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/seasons/{imdbId}")
  @ResponseStatus(HttpStatus.OK)
  public List<Integer> getSeasonsByImdbId(@PathVariable("imdbId") final String imdbId) {

    return service.readSeasons(imdbId);
  }

  /**
   * Fetch all distinct keys for a given Index.
   *
   * @param index the index name (actor,director,genre,rating,tag,title,year,etc.).
   * @return the list of distinct key values (genres, ratings, tags, etc.).
   */
  @RequestMapping(method = RequestMethod.GET, path = "/index/keys/{index}")
  @ResponseStatus(HttpStatus.OK)
  public List<String> getKeysByIndex(@PathVariable("index") final String index) {

    return service.readIndex(Index.fromValue(index));
  }

  /**
   * Fetch all Key values for a given Index.
   *
   * @param index the index name (actor,director,genre,rating,tag,title,year,etc.).
   * @return a list of the distinct values for the index, and the imdbIds associated with each.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/index/{index}")
  @ResponseStatus(HttpStatus.OK)
  public List<Key> getIdsByIndex(@PathVariable("index") final String index) {

    return service.readIdsByIndex(Index.fromValue(index));
  }

  /**
   * Fetch a 'Key', which is a list of all imdbIds under a given Index and key-value.
   *
   * @param index the index name (actor,director,genre,rating,tag,title,year,etc.).
   * @param key the key-value (a genre, a rating, a tag, etc.).
   * @return the Key, containing the key-value and list of associated imdbIds.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/index/{index}/{key}")
  @ResponseStatus(HttpStatus.OK)
  public Key getIdsByIndex(@PathVariable("index") final String index,
      @PathVariable("key") final String key) {

    return service.readIdsByIndexAndKey(Index.fromValue(index), key);
  }

  /**
   * Search a given Index for a given query value. All matches (partial and complete) are valid.
   *
   * @param index the index name (actor,director,genre,rating,tag,title,year,etc.).
   * @param query the query value (a partial/complete genre-name, tag-name, actor-name, etc.).
   * @return the list of matches for the given query on the given index.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/search/{index}")
  @ResponseStatus(HttpStatus.OK)
  public List<String> searchByIndex(@PathVariable("index") final String index,
      @RequestParam("q") final String query) {

    return service.searchByIndex(Index.fromValue(index), query);
  }

  /**
   * Fetch the count of titles associated with a given Index and key-value.
   *
   * @param index the index name (actor,director,genre,rating,tag,title,year,etc.).
   * @param key the key value (a genre, a rating, a tag, etc.).
   * @return The Count of titles associated with an index and key.
   */
  @RequestMapping(method = RequestMethod.GET, path = "/count/{index}")
  public Count getCountByIndexAndName(@PathVariable("index") final String index,
      @RequestParam("name") final String key) {

    return service.countByIndexAndKey(Index.fromValue(index), key);
  }

}
