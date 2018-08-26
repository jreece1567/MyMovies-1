package com.sandy.mymovies;

import com.sandy.mymovies.models.domain.Chapter;
import com.sandy.mymovies.models.domain.Video;
import com.sandy.mymovies.models.dto.Episode;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.repositories.ChapterRepository;
import com.sandy.mymovies.repositories.VideoRepository;
import com.sandy.mymovies.services.MyMoviesService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;

@Log
@SpringBootApplication
public class MyMoviesApplication implements CommandLineRunner {

  // remove these repository references along with the 'testRepos' method below when repo-testing is complete.
  @Autowired
  VideoRepository videoRepository;
  @Autowired
  ChapterRepository chapterRepository;

  @Autowired
  MyMoviesService movieService;

  public static void main(final String[] args) {
    SpringApplication.run(MyMoviesApplication.class, args);
  }

  @Override
  public void run(final String... args) throws Exception {

    // test repository setup - remove this when repos are validated
    testRepos();

    // un-comment the 'loadAllxxx()' calls below when ready to load everything
    // Load files, create movie beans
    //loadAllMovies();
    // Load files, create Episode beans
    //loadAllEpisodes();
  }

  /**
   * Temporary method to test low-level repo functionality - remove this before loading real data.
   */
  private void testRepos() {

    // test 'Video' (Movie) repo
    videoRepository.save(new Video("0128442", "Rounders", 1998, "2:01", "R", "John Dahl",
        "0128442.jpg",
        "A young man is a reformed gambler who must return to playing big stakes poker to help a friend pay off loan sharks."));

    final Optional<Video> video = videoRepository.findById("0128442");
    if (video.isPresent()) {
      log.info("Found: " + video.get().toString());
    } else {
      log.info("Unable to load movie with id: " + "0128442");
    }

    // test 'Chapter' (Episode) repo
    chapterRepository.save(new Chapter("4052886", 1, 1, "Pilot",
        "Lucifer has left Hell to take up a life on Earth. When a friend of his is murdered Lucifer joins forces with the good side of the law to discover who the perpetrators are and to give them what they rightfully deserve."));

    final Optional<Chapter> chapter = chapterRepository
        .findByImdbIdAndSeasonAndEpisodeNumber("4052886", 1, 1);
    if (chapter.isPresent()) {
      log.info("Found: " + chapter.get().toString());
    } else {
      log.info(
          "Unable to load episode with id: " + "4052886" + " season " + "1" + " episode " + "1");
    }
  }

  /**
   * Load all 'Movies' from the filesystem-based DB into our DB.
   */
  private void loadAllMovies() {

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

    log.info("Loading " + map.size() + " movies ...");
    final Map<String, Integer> moviesLoaded = new HashMap<>();  // use a map so that we can update it from within a closure.

    // walk the list of imdbid's, loading each 'Movie' JSON file and storing it in our DB
    map.keySet().forEach(imdbId -> {
      BufferedReader movieReader = new BufferedReader(
          new InputStreamReader(getClass().getResourceAsStream("/db/" + imdbId + ".json")));
      Movie movie = null;
      try {
        movie = new ObjectMapper()
            .readValue(movieReader, new TypeReference<Movie>() {
            });
        moviesLoaded
            .put(imdbId, 1);  // do this so that we can keep a count from inside this closure.
      } catch (JsonMappingException | JsonParseException e) {
        log.log(Level.ALL, "Error reading " + imdbId + ".json", e);
      } catch (IOException e) {
        log.log(Level.ALL, "Error reading " + imdbId + ".json", e);
      }
      if (movie != null) {
        movieService.createMovie(movie);
      }
    });

    log.info("Loaded " + moviesLoaded.size() + " movies ...");
  }

  /**
   * Load all 'Episodes' from the filesystem-based DB into our DB.
   */
  private void loadAllEpisodes() {

    // load up the list of imdbid's-with-episodes in the filesystem-based DB
    BufferedReader indexReader = new BufferedReader(
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

    // walk the list of imdbid's, loading the episodes associated with each imdbId, and saving them in our DB.
    Iterator<String> iterator = map.keySet().iterator();
    while (iterator.hasNext()) {
      String imdbId = iterator.next();
      List<Episode> episodes = map.get(imdbId);
      episodesLoaded = episodesLoaded + episodes.size();
      episodes.forEach(episode -> {
        movieService.createEpisode(episode.getImdbId(), episode.getSeason(),
            episode.getEpisodeNumber(), episode.getTitle(), episode.getDescription());
      });
    }

    log.info("Loaded " + episodesLoaded + " episodes for " + map.size() + " titles.");
  }

}
