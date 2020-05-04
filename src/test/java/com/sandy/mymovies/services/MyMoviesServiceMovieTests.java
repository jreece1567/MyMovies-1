package com.sandy.mymovies.services;

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

    Movie movie = moviesService.readMovie("0128442");

    assertThat(movie, is(notNullValue()));

    assertThat(movie.getTitle(), is("Rounders"));

  }

  @Test
  public void readMovie_withInvalidImdbId_returnsException() {

    try {

      Movie movie = moviesService.readMovie("0999999");
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readTitle_withValidImdbId_returnsTitle() {

    Title title = moviesService.readTitle("0128442");

    assertThat(title, is(notNullValue()));

    assertThat(title.getTitle(), is("Rounders"));

  }

  @Test
  public void readTitle_withInvalidImdbId_returnsException() {

    try {

      Title title = moviesService.readTitle("0999999");
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readCast_withValidImdbId_returnsCast() {

    Cast cast = moviesService.readCast("0128442");

    assertThat(cast, is(notNullValue()));

    assertThat(cast.contains("Matt Damon"), is(true));

  }

  @Test
  public void readCast_withInvalidImdbId_returnsException() {

    try {

      Title title = moviesService.readTitle("0999999");
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readEpisodes_withValidImdbId_returnsEpisodes() {

    List<Episode> episodes = moviesService.readEpisodes("0060028");

    assertThat(episodes, is(notNullValue()));

    assertThat(episodes.isEmpty(), is(false));

    assertThat(episodes.size(), is(80));

  }

  @Test
  public void readEpisodes_withInvalidImdbId_returnsException() {

    try {

      List<Episode> episodes = moviesService.readEpisodes("0999999");
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readEpisodes_withValidImdbIdAndSeason_returnsEpisodes() {

    List<Episode> episodes = moviesService.readEpisodes("0060028", 3);

    assertThat(episodes, is(notNullValue()));

    assertThat(episodes.isEmpty(), is(false));

    assertThat(episodes.size(), is(24));

  }

  @Test
  public void readEpisodes_withInvalidImdbIdAndSeason_returnsException() {

    try {

      List<Episode> episodes = moviesService.readEpisodes("0999999", 3);
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readEpisodes_withValidImdbIdAndInvalidSeason_returnsException() {

    try {

      List<Episode> episodes = moviesService.readEpisodes("0060028", 6);
      fail("Unknown season should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void readSeasons_withValidImdbId_returnsSeasons() {

    List<Integer> seasons = moviesService.readSeasons("0060028");

    assertThat(seasons, is(notNullValue()));

    assertThat(seasons.isEmpty(), is(false));

    assertThat(seasons.size(), is(3));

  }

  @Test
  public void readSeasons_withInvalidImdbId_returnsException() {

    try {

      List<Integer> seasons = moviesService.readSeasons("0999999");
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void deleteMovie_withvalidImdbId_succeeds() {

    Movie movie = moviesService.readMovie("5774060");

    moviesService.deleteMovie("5774060");

    try {

      moviesService.readMovie("5774060");
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

      moviesService.deleteMovie("0999999");
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }
  }

  @Test
  public void deleteEpisodes_withvalidImdbId_succeeds() {

    Movie movie = moviesService.readMovie("0060028");
    List<Episode> episodes = moviesService.readEpisodes("0060028");

    moviesService.deleteEpisodes("0060028");

    List<Episode> emptyEpisodes = moviesService.readEpisodes("0060028");
    assertEquals(0, emptyEpisodes.size());

    episodes.forEach(episode -> moviesService.createEpisode(episode));
  }

  @Test
  public void deleteEpisodes_withvalidImdbIdandSeason_succeeds() {

    Movie movie = moviesService.readMovie("0060028");
    List<Episode> episodes = moviesService.readEpisodes("0060028");

    moviesService.deleteEpisodes("0060028", 1);

    List<Episode> emptyEpisodes = moviesService.readEpisodes("0060028");
    assertEquals("2", emptyEpisodes.get(0).getSeason());

    episodes.forEach(episode -> {
      if (episode.getSeason().equals("1")) {
        moviesService.createEpisode(episode);
      }
    });
  }

  @Test
  public void deleteEpisodes_withvalidImdbIdandSeasonAndEpisode_succeeds() {

    final Movie movie = moviesService.readMovie("0060028");
    final List<Episode> episodes = moviesService.readEpisodes("0060028");

    moviesService.deleteEpisode("0060028", 1, 5);

    List<Episode> emptyEpisodes = moviesService.readEpisodes("0060028");
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
    Movie movie = moviesService.readMovie("5774060");

    moviesService.deleteMovie("5774060");

    try {

      moviesService.readMovie("5774060");
      fail("Unknown imdbId should throw NoSuchElementException");

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

    Movie newMovie = moviesService.createMovie(movie);
    Assert.assertEquals(movie, newMovie);
  }

  @Test
  public void createEpisode_withvalidepisode_succeeds() {

    List<Episode> episodes = moviesService.readEpisodes("0060028", 2);

    moviesService.deleteEpisode("0060028", 2, 4);

    final Episode episode = new Episode();
    episode.setImdbId("0060028");
    episode.setSeason("2");
    episode.setEpisodeNumber("4");
    episode.setTitle("Test Episode");
    episode.setDescription("This is a test episode description.");

    Episode newEpisode = moviesService.createEpisode(episode);

    Assert.assertEquals(episode, newEpisode);

    List<Episode> newEpisodes = moviesService.readEpisodes("0060028", 2);
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
