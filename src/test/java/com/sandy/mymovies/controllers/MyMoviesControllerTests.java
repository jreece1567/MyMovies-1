package com.sandy.mymovies.controllers;

import static com.sandy.mymovies.MyMoviesTestData.INVALID_GENRE_KEY;
import static com.sandy.mymovies.MyMoviesTestData.INVALID_IMDB_ID;
import static com.sandy.mymovies.MyMoviesTestData.INVALID_INDEX_KEY;
import static com.sandy.mymovies.MyMoviesTestData.TEST_GENRE;
import static com.sandy.mymovies.MyMoviesTestData.TEST_GENRE_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TEST_GENRE_KEY;
import static com.sandy.mymovies.MyMoviesTestData.TEST_IMDB_ID;
import static com.sandy.mymovies.MyMoviesTestData.TEST_IMDB_ID_DELETE;
import static com.sandy.mymovies.MyMoviesTestData.TEST_IMDB_ID_SERIES;
import static com.sandy.mymovies.MyMoviesTestData.TEST_IMDB_ID_TITLE;
import static com.sandy.mymovies.MyMoviesTestData.TEST_INDEX_KEY;
import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandy.mymovies.MyMoviesApplication;
import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Episode;
import com.sandy.mymovies.models.dto.Genres;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.models.dto.Tags;
import com.sandy.mymovies.repositories.ActorRepository;
import com.sandy.mymovies.repositories.ChapterRepository;
import com.sandy.mymovies.repositories.GenreRepository;
import com.sandy.mymovies.repositories.TagRepository;
import com.sandy.mymovies.repositories.VideoRepository;
import java.util.Arrays;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyMoviesApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
    locations = "classpath:application-integrationTest.properties")
public class MyMoviesControllerTests {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  ActorRepository actorRepository;

  @Autowired
  ChapterRepository chapterRepository;

  @Autowired
  GenreRepository genreRepository;

  @Autowired
  TagRepository tagRepository;

  @Autowired
  VideoRepository videoRepository;

  @Test
  public void fetchMovie_withvalidmovieid_returnsMovie() {

    try {
      mockMvc.perform(
          get("/movie/" + TEST_IMDB_ID)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("title", is(TEST_IMDB_ID_TITLE)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchMovie_withinvalidmovieid_returns404() {

    try {
      mockMvc.perform(
          get("/movie/" + INVALID_IMDB_ID)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchTitle_withvalidmovieid_returnsTitle() {

    try {
      mockMvc.perform(
          get("/title/" + TEST_IMDB_ID)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("title", is(TEST_IMDB_ID_TITLE)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchTitle_withinvalidmovieid_returns404() {

    try {
      mockMvc.perform(
          get("/title/" + INVALID_IMDB_ID)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchCast_withvalidmovieid_returnsCast() {

    try {
      mockMvc.perform(
          get("/cast/" + TEST_IMDB_ID)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0]", is(notNullValue())));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchCast_withinvalidmovieid_returns404() {

    try {
      mockMvc.perform(
          get("/cast/" + INVALID_IMDB_ID)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndex_withvalidkey_returnsIndexKeys() {

    try {
      mockMvc.perform(
          get("/index/keys/" + TEST_INDEX_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[6]", is(TEST_GENRE_KEY)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndex_withinvalidkey_returns422() {

    try {
      mockMvc.perform(
          get("/index/keys/" + INVALID_INDEX_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnprocessableEntity());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIds_withvalidindex_returnsIds() {

    try {
      mockMvc.perform(
          get("/index/" + TEST_INDEX_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].key", is("Action")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIds_withinvalidindex_returns422() {

    try {
      mockMvc.perform(
          get("/index/" + INVALID_INDEX_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnprocessableEntity());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIds_withvalidindexandkey_returnsIds() {

    try {
      mockMvc.perform(
          get("/index/" + TEST_INDEX_KEY + "/" + TEST_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("key", is(TEST_GENRE_KEY)))
          .andExpect(jsonPath("ids[0]", is(notNullValue())));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIds_withinvalidindexkey_returnsEmptyArray() {

    try {
      mockMvc.perform(
          get("/index/" + TEST_INDEX_KEY + "/" + INVALID_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("key", is("zzz")))
          .andExpect(jsonPath("ids", IsEmptyCollection.empty()));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndexEntry_withvalidindex_returnsIndexEntries() {

    try {
      mockMvc.perform(
          get("/titles/" + TEST_INDEX_KEY + "/" + TEST_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[9].imdbId", is("0277457")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndexEntry_withinvalidindex_returnsEmptyArray() {

    try {
      mockMvc.perform(
          get("/titles/" + TEST_INDEX_KEY + "/" + INVALID_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndexEntry_withvalidindexqp_returnsIndexEntries() {

    try {
      mockMvc.perform(
          get("/titles/" + TEST_INDEX_KEY + "?name=" + TEST_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[9].imdbId", is("0277457")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndexEntry_withinvalidindexqp_returnsEmptyArray() {

    try {
      mockMvc.perform(
          get("/titles/" + TEST_INDEX_KEY + "?name=" + INVALID_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchMovies_withvalidindex_returnsIndexEntries() {

    try {
      mockMvc.perform(
          get("/movies/" + TEST_INDEX_KEY + "/" + TEST_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[9].imdbId", is("0277457")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchMovies_withinvalidindex_returnsEmptyArray() {

    try {
      mockMvc.perform(
          get("/movies/" + TEST_INDEX_KEY + "/" + INVALID_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchMovies_withvalidindexqp_returnsIndexEntries() {

    try {
      mockMvc.perform(
          get("/movies/" + TEST_INDEX_KEY + "?name=" + TEST_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[9].imdbId", is("0277457")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchMovies_withinvalidindexqp_returnsEmptyArray() {

    try {
      mockMvc.perform(
          get("/movies/" + TEST_INDEX_KEY + "?name=" + INVALID_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchSeasons_withvalidquery_returnsSeasons() {

    try {
      mockMvc.perform(
          get("/seasons/" + TEST_IMDB_ID_SERIES)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0]", is(1)))
          .andExpect(jsonPath("$[1]]", is(2)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchSeasons_withinvalidquery_returnsEmptyArray() {

    try {
      mockMvc.perform(
          get("/seasons/" + TEST_IMDB_ID)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchEpisodes_withvalidimdbId_returnsEpisodes() {
    try {
      mockMvc.perform(
          get("/episodes/" + TEST_IMDB_ID_SERIES)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].imdbId", is(TEST_IMDB_ID_SERIES)))
          .andExpect(jsonPath("$[0].season", is("1")))
          .andExpect(jsonPath("$[0].episodeNumber", is("1")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }

  @Test
  public void fetchEpisodes_withvalidseason_returnsEpisodes() {
    try {
      mockMvc.perform(
          get("/episodes/" + TEST_IMDB_ID_SERIES + "/2")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].imdbId", is(TEST_IMDB_ID_SERIES)))
          .andExpect(jsonPath("$[0].season", is("2")))
          .andExpect(jsonPath("$[0].episodeNumber", is("1")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }

  @Test
  public void fetchEpisodes_withinvalidseason_returns404() {
    try {
      mockMvc.perform(
          get("/episodes/" + TEST_IMDB_ID_SERIES + "/9")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }

  @Test
  public void searchIndexEntry_withvalidquery_returnsIndexEntries() {

    try {
      mockMvc.perform(
          get("/search/genre?q=cum")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0]", is(TEST_GENRE_KEY)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void searchIndexEntry_withinvalidquery_returnsEmptyArray() {

    try {
      mockMvc.perform(
          get("/search/genre?q=zzzz")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void countIndexEntry_withvalidquery_returnsCount() {

    try {
      mockMvc.perform(
          get("/count/" + TEST_INDEX_KEY + "?name=" + TEST_GENRE)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.value", is(TEST_GENRE)))
          .andExpect(jsonPath("$.count", is(TEST_GENRE_COUNT)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void countIndexEntry_withinvalidquery_returnsZero() {

    try {
      mockMvc.perform(
          get("/count/" + TEST_INDEX_KEY + "?name=" + INVALID_GENRE_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.value", is(INVALID_GENRE_KEY)))
          .andExpect(jsonPath("$.count", is(0)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void leaderboard_withvalidquery_returnsCounts() {

    try {
      mockMvc.perform(
          get("/leaderboard/" + TEST_INDEX_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].count", not(0)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void leaderboard_withinvalidquery_returns422() {

    try {
      mockMvc.perform(
          get("/leaderboard/" + INVALID_INDEX_KEY)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnprocessableEntity())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void deleteMovie_withvalidmovieid_returns204() {

    try {
      mockMvc.perform(
          delete("/movie/" + TEST_IMDB_ID_DELETE)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void deleteMovie_withinvalidmovieid_returns404() {

    try {
      mockMvc.perform(
          get("/movie/" + INVALID_IMDB_ID)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void deleteEpisodes_withvalidmovieid_returns204() {

    try {
      mockMvc.perform(
          delete("/episodes/" + TEST_IMDB_ID_SERIES)
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void deleteEpisodes_withvalidmovieidandseason_returns204() {

    try {
      mockMvc.perform(
          delete("/episodes/" + TEST_IMDB_ID_SERIES + "/2")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void deleteEpisodes_withvalidmovieidandseasonandepisode_returns204() {

    try {
      mockMvc.perform(
          delete("/episodes/" + TEST_IMDB_ID_SERIES + "/2/3")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void createMovie_withvalidmovie_returns200() throws JsonProcessingException {

    Movie movie = new Movie();
    movie.setImdbId("1234567");
    movie.setCast(new Cast(Arrays.asList("me", "myself")));
    movie.setDescription("A movie about a test.");
    movie.setDirector("Q.A. Tester");
    movie.setDuration("1:00");
    movie.setGenres(new Genres(Arrays.asList(TEST_GENRE)));
    movie.setImageUrl("test_image.jpg");
    movie.setRating("GP");
    movie.setReleaseYear(2020);
    movie.setTags(new Tags(Arrays.asList("TV")));
    movie.setTitle("Test Movie");

    String requestBody = null;
    try {
      requestBody = objectMapper.writeValueAsString(movie);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw e;
    }

    try {
      mockMvc.perform(
          post("/movie")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isOk());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

    try {
      mockMvc.perform(
          delete("/movie/1234567")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNoContent());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void createMovie_withinvalidmovie_returns422() throws JsonProcessingException {

    Movie movie = new Movie();
    movie.setImdbId("1234567");
    movie.setCast(new Cast(Arrays.asList("me", "myself")));
    movie.setDescription("A movie about a test.");
    movie.setDirector("Q.A. Tester");
    movie.setDuration("1:00");
    movie.setGenres(new Genres(Arrays.asList(TEST_GENRE)));
    movie.setImageUrl("test_image.jpg");
    movie.setRating("GP");
    movie.setReleaseYear(null); // <<--- invalid value
    movie.setTags(new Tags(Arrays.asList("TV")));
    movie.setTitle("Test Movie");

    String requestBody = null;
    try {
      requestBody = objectMapper.writeValueAsString(movie);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw e;
    }

    try {
      mockMvc.perform(
          post("/movie")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isUnprocessableEntity());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void createEpisode_withvalidmovie_returns200() throws JsonProcessingException {

    Episode episode = new Episode();
    episode.setImdbId(TEST_IMDB_ID_SERIES);
    episode.setSeason("2");
    episode.setEpisodeNumber("4");
    episode.setTitle("Test Episode");
    episode.setDescription("This is a test episode description.");

    String requestBody = null;
    try {
      requestBody = objectMapper.writeValueAsString(episode);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw e;
    }

    try {
      mockMvc.perform(
          post("/episodes")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isOk());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void createEpisode_withinvalidmovie_returns404() throws JsonProcessingException {

    Episode episode = new Episode();
    episode.setImdbId(INVALID_IMDB_ID);
    episode.setSeason("2");
    episode.setEpisodeNumber("4");
    episode.setTitle("Test Episode");
    episode.setDescription("This is a test episode description.");

    String requestBody = null;
    try {
      requestBody = objectMapper.writeValueAsString(episode);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw e;
    }

    try {
      mockMvc.perform(
          post("/episodes")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isNotFound());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void createEpisode_withinvalidepisode_returns422() throws JsonProcessingException {

    Episode episode = new Episode();
    episode.setImdbId(TEST_IMDB_ID_SERIES);
    episode.setSeason(null);  // <<<---- invalid value
    episode.setEpisodeNumber("4");
    episode.setTitle("Test Episode");
    episode.setDescription("This is a test episode description.");

    String requestBody = null;
    try {
      requestBody = objectMapper.writeValueAsString(episode);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw e;
    }

    try {
      mockMvc.perform(
          post("/episodes")
              .contentType(MediaType.APPLICATION_JSON)
              .content(requestBody))
          .andExpect(status().isUnprocessableEntity());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void notImplemented_succeeds() {

    try {
      mockMvc.perform(
          get("/movie/notimplemented")
              .contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotImplemented());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }
}
