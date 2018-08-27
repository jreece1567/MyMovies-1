package com.sandy.mymovies;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandy.mymovies.models.dto.Episode;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.services.MyMoviesService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log
@SpringBootApplication
public class MyMoviesApplication implements CommandLineRunner {

  @Autowired
  MyMoviesService movieService;

  public static void main(final String[] args) {
    SpringApplication.run(MyMoviesApplication.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {

    // Load files, create movie beans
    loadAllMovies();
    // Load files, create Episode beans
    loadAllEpisodes();

    log.info("MyMovies service ready for use !!!");
  }

  /**
   * Load all 'Movies' from the filesystem-based DB into our DB.
   */
  private void loadAllMovies() {

    final SimpleTimer timer = new SimpleTimer().start();

    // load up the list of imdbid's in the filesystem-based DB
    BufferedReader indexReader = new BufferedReader(
        new InputStreamReader(getClass().getResourceAsStream("/db/index_id.json")));
    Map<String, List<String>> map = new HashMap<>();
    try {
      map = new ObjectMapper()
          .readValue(indexReader, new TypeReference<HashMap<String, List<String>>>() {
          });
    } catch (JsonMappingException | JsonParseException e) {
      log.log(Level.ALL, "Error reading index_id.json", e);
      return;
    } catch (IOException e) {
      log.log(Level.ALL, "Error reading index_id.json", e);
      return;
    }

    log.info("Loading " + map.keySet().size() + " movies ...");
    final Map<String, Integer> moviesLoaded = new HashMap<>();

    // walk the list of imdbid's, loading each 'Movie' JSON file and storing it in our DB
    map.keySet().forEach(imdbId -> {
      final BufferedReader movieReader = new BufferedReader(
          new InputStreamReader(getClass().getResourceAsStream("/db/" + imdbId + ".json")));
      Movie movie = null;
      try {
        movie = new ObjectMapper()
            .readValue(movieReader, new TypeReference<Movie>() {
            });
        moviesLoaded
            .put(imdbId, 1);  // do this so that we can keep a count from inside this closure.
      } catch (JsonMappingException | JsonParseException e) {
        log.info("Error reading " + imdbId + ".json - " + e.getMessage());
      } catch (IOException e) {
        log.info("Error reading " + imdbId + ".json - " + e.getMessage());
      }
      if (movie != null) {
        movieService.createMovie(movie);
      }
    });

    log.info("Loaded " + moviesLoaded.size() + " movies in " + timer.stop().duration() + "ms.");
  }

  /**
   * Load all 'Episodes' from the filesystem-based DB into our DB.
   */
  private void loadAllEpisodes() {

    final SimpleTimer timer = new SimpleTimer().start();

    // load up the list of imdbid's-with-episodes in the filesystem-based DB
    final BufferedReader indexReader = new BufferedReader(
        new InputStreamReader(getClass().getResourceAsStream("/db/index_episode.json")));
    Map<String, List<Episode>> map = new HashMap<>();
    try {
      map = new ObjectMapper()
          .readValue(indexReader, new TypeReference<HashMap<String, List<Episode>>>() {
          });
    } catch (JsonMappingException | JsonParseException e) {
      log.log(Level.ALL, "Error reading index_episode.json", e);
      return;
    } catch (IOException e) {
      log.log(Level.ALL, "Error reading index_episode.json", e);
      return;
    }

    log.info("Loading episodes for " + map.size() + " titles ...");
    int episodesLoaded = 0;

    // walk the list of imdbid's, load the episodes associated with each imdbId,
    // and save the episodes in our DB.
    Iterator<String> iterator = map.keySet().iterator();
    while (iterator.hasNext()) {
      String imdbId = iterator.next();
      List<Episode> episodes = map.get(imdbId);
      episodesLoaded = episodesLoaded + episodes.size();
      episodes.forEach(episode -> {
        movieService.createEpisode(new Episode(episode.getImdbId(), episode.getSeason(),
            episode.getEpisodeNumber(), episode.getTitle(), episode.getDescription()));
      });
    }

    log.info(
        "Loaded " + episodesLoaded + " episodes for " + map.size() + " titles in " + timer.stop()
            .duration() + "ms.");
  }

  /**
   * Simple reusable execution-time timer.
   */
  class SimpleTimer {

    private long start;
    private long stop;

    /**
     * Create a new SimpleTimer instance.
     */
    public SimpleTimer() {
      this.start = new Date().getTime();
      this.stop = this.start;
    }

    /**
     * Start the timer.
     *
     * @return this SimpleTimer instance.
     */
    public SimpleTimer start() {
      this.start = new Date().getTime();
      this.stop = this.start;
      return this;
    }

    /**
     * Stop the timer.
     *
     * @return this SimpleTimer instance.
     */
    public SimpleTimer stop() {
      this.stop = new Date().getTime();
      return this;
    }

    /**
     * Return the duration recorded by the timer.
     *
     * @return the duration in milliseconds.
     */
    public long duration() {
      return this.stop - this.start;
    }
  }
}
