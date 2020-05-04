package com.sandy.mymovies.services;

import static com.sandy.mymovies.MyMoviesTestData.INVALID_IMDB_ID;
import static com.sandy.mymovies.MyMoviesTestData.TEST_ACTOR;
import static com.sandy.mymovies.MyMoviesTestData.TEST_IMDB_ID;
import static com.sandy.mymovies.MyMoviesTestData.TEST_IMDB_ID_SERIES;
import static com.sandy.mymovies.MyMoviesTestData.TEST_IMDB_ID_TITLE;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Episode;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.models.dto.Title;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyMoviesServiceMovieTests {

  @Autowired
  MyMoviesService moviesService;

  @Test
  public void readMovie_withValidImdbId_returnsMovie() {

    Movie movie = moviesService.readMovie(TEST_IMDB_ID);

    assertThat(movie, is(notNullValue()));

    assertThat(movie.getTitle(), is("Rounders"));

  }

  @Test
  public void readMovie_withInvalidImdbId_returnsException() {

    try {

      Movie movie = moviesService.readMovie(INVALID_IMDB_ID);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readTitle_withValidImdbId_returnsTitle() {

    Title title = moviesService.readTitle(TEST_IMDB_ID);

    assertThat(title, is(notNullValue()));

    assertThat(title.getTitle(), is(TEST_IMDB_ID_TITLE));

  }

  @Test
  public void readTitle_withInvalidImdbId_returnsException() {

    try {

      Title title = moviesService.readTitle(INVALID_IMDB_ID);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readCast_withValidImdbId_returnsCast() {

    Cast cast = moviesService.readCast(TEST_IMDB_ID);

    assertThat(cast, is(notNullValue()));

    assertThat(cast.contains(TEST_ACTOR), is(true));

  }

  @Test
  public void readCast_withInvalidImdbId_returnsException() {

    try {

      Title title = moviesService.readTitle(INVALID_IMDB_ID);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readEpisodes_withValidImdbId_returnsEpisodes() {

    List<Episode> episodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES);

    assertThat(episodes, is(notNullValue()));

    assertThat(episodes.isEmpty(), is(false));

    assertThat(episodes.size(), is(80));

  }

  @Test
  public void readEpisodes_withInvalidImdbId_returnsException() {

    try {

      List<Episode> episodes = moviesService.readEpisodes(INVALID_IMDB_ID);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readEpisodes_withValidImdbIdAndSeason_returnsEpisodes() {

    List<Episode> episodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES, 3);

    assertThat(episodes, is(notNullValue()));

    assertThat(episodes.isEmpty(), is(false));

    assertThat(episodes.size(), is(24));

  }

  @Test
  public void readEpisodes_withInvalidImdbIdAndSeason_returnsException() {

    try {

      List<Episode> episodes = moviesService.readEpisodes(INVALID_IMDB_ID, 3);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readEpisodes_withValidImdbIdAndInvalidSeason_returnsException() {

    try {

      List<Episode> episodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES, 6);
      fail("Unknown season should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readSeasons_withValidImdbId_returnsSeasons() {

    List<Integer> seasons = moviesService.readSeasons(TEST_IMDB_ID_SERIES);

    assertThat(seasons, is(notNullValue()));

    assertThat(seasons.isEmpty(), is(false));

    assertThat(seasons.size(), is(3));

  }

  @Test
  public void readSeasons_withInvalidImdbId_returnsException() {

    try {

      List<Integer> seasons = moviesService.readSeasons(INVALID_IMDB_ID);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void deleteMovie_withvalidImdbId_succeeds() {

    Movie movie = moviesService.readMovie(TEST_IMDB_ID);

    moviesService.deleteMovie(TEST_IMDB_ID);

    try {

      moviesService.readMovie(TEST_IMDB_ID);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

    Movie newMovie = moviesService.createMovie(movie);
    Assert.assertEquals(movie, newMovie);
  }

  @Test
  public void deleteMovie_withinvalidImdbId_returnsException() {

    try {

      moviesService.deleteMovie(INVALID_IMDB_ID);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }
  }

  @Test
  public void deleteEpisodes_withvalidImdbId_succeeds() {

    Movie movie = moviesService.readMovie(TEST_IMDB_ID_SERIES);
    List<Episode> episodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES);

    moviesService.deleteEpisodes(TEST_IMDB_ID_SERIES);

    List<Episode> emptyEpisodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES);
    assertEquals(0, emptyEpisodes.size());

    episodes.forEach(episode -> moviesService.createEpisode(episode));
  }

  @Test
  public void deleteEpisodes_withvalidImdbIdandSeason_succeeds() {

    Movie movie = moviesService.readMovie(TEST_IMDB_ID_SERIES);
    List<Episode> episodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES);

    moviesService.deleteEpisodes(TEST_IMDB_ID_SERIES, 1);

    List<Episode> emptyEpisodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES);
    assertEquals("2", emptyEpisodes.get(0).getSeason());

    episodes.forEach(episode -> {
      if (episode.getSeason().equals("1")) {
        moviesService.createEpisode(episode);
      }
    });
  }

  @Test
  public void deleteEpisodes_withvalidImdbIdandSeasonAndEpisode_succeeds() {

    final Movie movie = moviesService.readMovie(TEST_IMDB_ID_SERIES);
    final List<Episode> episodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES);

    moviesService.deleteEpisode(TEST_IMDB_ID_SERIES, 1, 5);

    List<Episode> emptyEpisodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES);
    assertEquals("1", emptyEpisodes.get(0).getSeason());
    emptyEpisodes.forEach(episode -> {
      if (episode.getSeason().equals("1")) {
        assertNotEquals("5", episode.getEpisodeNumber());
      }
    });

    episodes.forEach(episode -> {
      if (episode.getSeason().equals("1") && episode.getEpisodeNumber().equals("5")) {
        moviesService.createEpisode(episode);
      }
    });
  }

  @Test
  public void createMovie_withvalidmovie_succeeds() {
    Movie movie = moviesService.readMovie(TEST_IMDB_ID);

    moviesService.deleteMovie(TEST_IMDB_ID);

    try {

      moviesService.readMovie(TEST_IMDB_ID);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

    Movie newMovie = moviesService.createMovie(movie);
    Assert.assertEquals(movie, newMovie);
  }

  @Test
  public void createMovie_withduplicatemovie_succeeds() {
    Movie movie = moviesService.readMovie(TEST_IMDB_ID);

    Movie newMovie = moviesService.createMovie(movie);
    Assert.assertEquals(movie, newMovie);
  }

  @Test
  public void createEpisode_withvalidepisode_succeeds() {

    List<Episode> episodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES, 2);

    moviesService.deleteEpisode(TEST_IMDB_ID_SERIES, 2, 4);

    final Episode episode = new Episode();
    episode.setImdbId(TEST_IMDB_ID_SERIES);
    episode.setSeason("2");
    episode.setEpisodeNumber("4");
    episode.setTitle("Test Episode");
    episode.setDescription("This is a test episode description.");

    Episode newEpisode = moviesService.createEpisode(episode);

    Assert.assertEquals(episode, newEpisode);

    List<Episode> newEpisodes = moviesService.readEpisodes(TEST_IMDB_ID_SERIES, 2);
    AtomicBoolean found = new AtomicBoolean();
    found.set(false);
    newEpisodes.forEach(e -> {
      if (e.getSeason().equals("2") && e.getEpisodeNumber().equals("4")) {
        assertEquals("Test Episode", e.getTitle());
        found.set(true);
      }
    });

    assertEquals(true, found.get());
  }

}
