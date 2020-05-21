package com.sandy.mymovies.services;

import static com.sandy.mymovies.MyMoviesTestData.ACTOR_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.ALL_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.DIRECTOR_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.GENRE_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.RATING_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TAG_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TEST_ACTOR;
import static com.sandy.mymovies.MyMoviesTestData.TEST_ACTOR_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TEST_DIRECTOR;
import static com.sandy.mymovies.MyMoviesTestData.TEST_DIRECTOR_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TEST_GENRE;
import static com.sandy.mymovies.MyMoviesTestData.TEST_GENRE_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TEST_RATING;
import static com.sandy.mymovies.MyMoviesTestData.TEST_RATING_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TEST_TAG;
import static com.sandy.mymovies.MyMoviesTestData.TEST_TAG_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TEST_TITLE;
import static com.sandy.mymovies.MyMoviesTestData.TEST_TITLE_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TEST_YEAR;
import static com.sandy.mymovies.MyMoviesTestData.TEST_YEAR_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.TITLE_COUNT;
import static com.sandy.mymovies.MyMoviesTestData.YEAR_COUNT;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.sandy.mymovies.models.dto.Count;
import com.sandy.mymovies.models.dto.Index;
import com.sandy.mymovies.models.dto.Key;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.models.dto.Title;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyMoviesServiceIndexTests {

  @Autowired
  MyMoviesService moviesService;

  @Test
  public void readIndex_withAll_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.ALL);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(ALL_COUNT));

    assertThat(keys.get(6), is("title"));
  }

  @Test
  public void readIndex_withActor_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.ACTOR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(ACTOR_COUNT));

    assertThat(keys.get(20), is("A. Philip Randolph"));
  }

  @Test
  public void readIndex_withDirector_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.DIRECTOR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(DIRECTOR_COUNT));

    assertThat(keys.get(37), is("Andy Mikita"));
  }

  @Test
  public void readIndex_withGenre_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.GENRE);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(GENRE_COUNT));

    assertThat(keys.get(4), is("Comedy"));
  }

  @Test
  public void readIndex_withRating_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.RATING);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(RATING_COUNT));

    assertThat(keys.get(6), is("PASSED"));

  }

  @Test
  public void readIndex_withTag_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.TAG);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(TAG_COUNT));

    assertThat(keys.get(17), is("TV"));

  }

  @Test
  public void readIndex_withTitle_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.TITLE);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(TITLE_COUNT));

    assertThat(keys.get(21), is("300"));

  }

  @Test
  public void readIndex_withYear_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.YEAR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(YEAR_COUNT));

    assertThat(keys.get(88), is("2018"));

  }

  @Test
  public void readTitlesWithIndexAndKey_withActor_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.ACTOR, TEST_ACTOR);

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(TEST_ACTOR_COUNT));

    assertThat(titles.get(21).getTitle(), is("The Martian"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withDirector_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.DIRECTOR, TEST_DIRECTOR);

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(TEST_DIRECTOR_COUNT));

    assertThat(titles.get(3).getTitle(), is("Full Metal Jacket"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withGenre_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.GENRE, TEST_GENRE);

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(TEST_GENRE_COUNT));

    assertThat(titles.get(17).getTitle(), is("WALL-E"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withRating_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.RATING, TEST_RATING);

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(TEST_RATING_COUNT));

    assertThat(titles.get(3).getTitle(), is("Outrageous Fortune"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withTag_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.TAG, TEST_TAG);

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(TEST_TAG_COUNT));

    assertThat(titles.get(28).getTitle(), is("The Blue Planet"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withTitle_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.TITLE, TEST_TITLE);

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(TEST_TITLE_COUNT));

    assertThat(titles.get(0).getTitle(), is(TEST_TITLE));
  }

  @Test
  public void readTitlesWithIndexAndKey_withYear_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.YEAR, TEST_YEAR);

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(TEST_YEAR_COUNT));

    assertThat(titles.get(3).getTitle(), is("Psycho"));
  }

  @Test
  public void readMoviesWithIndexAndKey_withActor_returnsMovies() {

    List<Movie> movies = moviesService.readMoviesByIndexAndKey(Index.ACTOR, TEST_ACTOR);

    assertThat(movies, is(notNullValue()));

    assertThat(movies.isEmpty(), is(false));

    assertThat(movies.size(), is(TEST_ACTOR_COUNT));

    assertThat(movies.get(21).getTitle(), is("The Martian"));
  }

  @Test
  public void readMoviesWithIndexAndKey_withDirector_returnsMovies() {

    List<Movie> movies = moviesService.readMoviesByIndexAndKey(Index.DIRECTOR, TEST_DIRECTOR);

    assertThat(movies, is(notNullValue()));

    assertThat(movies.isEmpty(), is(false));

    assertThat(movies.size(), is(TEST_DIRECTOR_COUNT));

    assertThat(movies.get(3).getTitle(), is("Full Metal Jacket"));
  }

  @Test
  public void readMoviesWithIndexAndKey_withGenre_returnsMovies() {

    List<Movie> movies = moviesService.readMoviesByIndexAndKey(Index.GENRE, TEST_GENRE);

    assertThat(movies, is(notNullValue()));

    assertThat(movies.isEmpty(), is(false));

    assertThat(movies.size(), is(TEST_GENRE_COUNT));

    assertThat(movies.get(17).getTitle(), is("WALL-E"));
  }

  @Test
  public void readMoviesWithIndexAndKey_withRating_returnsMovies() {

    List<Movie> movies = moviesService.readMoviesByIndexAndKey(Index.RATING, TEST_RATING);

    assertThat(movies, is(notNullValue()));

    assertThat(movies.isEmpty(), is(false));

    assertThat(movies.size(), is(TEST_RATING_COUNT));

    assertThat(movies.get(3).getTitle(), is("Outrageous Fortune"));
  }

  @Test
  public void readMoviesWithIndexAndKey_withTag_returnsMovies() {

    List<Movie> movies = moviesService.readMoviesByIndexAndKey(Index.TAG, TEST_TAG);

    assertThat(movies, is(notNullValue()));

    assertThat(movies.isEmpty(), is(false));

    assertThat(movies.size(), is(TEST_TAG_COUNT));

    assertThat(movies.get(28).getTitle(), is("The Blue Planet"));
  }

  @Test
  public void readMoviesWithIndexAndKey_withTitle_returnsMovies() {

    List<Movie> movies = moviesService.readMoviesByIndexAndKey(Index.TITLE, TEST_TITLE);

    assertThat(movies, is(notNullValue()));

    assertThat(movies.isEmpty(), is(false));

    assertThat(movies.size(), is(TEST_TITLE_COUNT));

    assertThat(movies.get(0).getTitle(), is(TEST_TITLE));
  }

  @Test
  public void readMoviesWithIndexAndKey_withYear_returnsMovies() {

    List<Movie> movies = moviesService.readMoviesByIndexAndKey(Index.YEAR, TEST_YEAR);

    assertThat(movies, is(notNullValue()));

    assertThat(movies.isEmpty(), is(false));

    assertThat(movies.size(), is(TEST_YEAR_COUNT));

    assertThat(movies.get(3).getTitle(), is("Psycho"));
  }

  @Test
  public void readIdsWithIndex_withActor_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.ACTOR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(ACTOR_COUNT));
  }

  @Test
  public void readIdsWithIndex_withDirector_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.DIRECTOR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(DIRECTOR_COUNT));
  }

  @Test
  public void readIdsWithIndex_withGenre_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.GENRE);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(GENRE_COUNT));
  }

  @Test
  public void readIdsWithIndex_withRating_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.RATING);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(RATING_COUNT));
  }

  @Test
  public void readIdsWithIndex_withTag_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.TAG);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(TAG_COUNT));
  }

  @Test
  public void readIdsWithIndex_withTitle_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.TITLE);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(TITLE_COUNT));
  }

  @Test
  public void readIdsWithIndex_withYear_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.YEAR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(YEAR_COUNT));
  }

  @Test
  public void readIdsWithIndexAndKey_withActor_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.ACTOR, TEST_ACTOR);

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(TEST_ACTOR_COUNT));

    assertThat(key.getKey(), is(TEST_ACTOR));
  }

  @Test
  public void readIdsWithIndexAndKey_withDirector_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.DIRECTOR, TEST_DIRECTOR);

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(TEST_DIRECTOR_COUNT));

    assertThat(key.getKey(), is(TEST_DIRECTOR));
  }

  @Test
  public void readIdsWithIndexAndKey_withGenre_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.GENRE, TEST_GENRE);

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(TEST_GENRE_COUNT));

    assertThat(key.getKey(), is(TEST_GENRE));
  }

  @Test
  public void readIdsWithIndexAndKey_withRating_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.RATING, TEST_RATING);

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(TEST_RATING_COUNT));

    assertThat(key.getKey(), is(TEST_RATING));
  }

  @Test
  public void readIdsWithIndexAndKey_withTag_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.TAG, TEST_TAG);

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(TEST_TAG_COUNT));

    assertThat(key.getKey(), is(TEST_TAG));
  }

  @Test
  public void readIdsWithIndexAndKey_withTitle_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.TITLE, TEST_TITLE);

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(TEST_TITLE_COUNT));

    assertThat(key.getKey(), is(TEST_TITLE));
  }

  @Test
  public void readIdsWithIndexAndKey_withYear_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.YEAR, TEST_YEAR);

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(TEST_YEAR_COUNT));

    assertThat(key.getKey(), is(TEST_YEAR));
  }

  @Test
  public void countIdsWithIndexAndKey_withActor_returnsCount() {

    Count count = moviesService.countByIndexAndKey(Index.ACTOR, TEST_ACTOR);

    assertThat(count, is(notNullValue()));

    assertThat(count.getCount(), is(TEST_ACTOR_COUNT));

    assertThat(count.getValue(), is(TEST_ACTOR));
  }

  @Test
  public void countIdsWithIndexAndKey_withDirector_returnsCount() {

    Count count = moviesService.countByIndexAndKey(Index.DIRECTOR, TEST_DIRECTOR);

    assertThat(count, is(notNullValue()));

    assertThat(count.getCount(), is(TEST_DIRECTOR_COUNT));

    assertThat(count.getValue(), is(TEST_DIRECTOR));
  }

  @Test
  public void countIdsWithIndexAndKey_withGenre_returnsCount() {

    Count count = moviesService.countByIndexAndKey(Index.GENRE, TEST_GENRE);

    assertThat(count, is(notNullValue()));

    assertThat(count.getCount(), is(TEST_GENRE_COUNT));

    assertThat(count.getValue(), is(TEST_GENRE));
  }

  @Test
  public void countIdsWithIndexAndKey_withRating_returnsCount() {

    Count count = moviesService.countByIndexAndKey(Index.RATING, TEST_RATING);

    assertThat(count, is(notNullValue()));

    assertThat(count.getCount(), is(TEST_RATING_COUNT));

    assertThat(count.getValue(), is(TEST_RATING));
  }

  @Test
  public void countIdsWithIndexAndKey_withTag_returnsCount() {

    Count count = moviesService.countByIndexAndKey(Index.TAG, TEST_TAG);

    assertThat(count, is(notNullValue()));

    assertThat(count.getCount(), is(TEST_TAG_COUNT));

    assertThat(count.getValue(), is(TEST_TAG));
  }

  @Test
  public void countIdsWithIndexAndKey_withTitle_returnsCount() {

    Count count = moviesService.countByIndexAndKey(Index.TITLE, TEST_TITLE);

    assertThat(count, is(notNullValue()));

    assertThat(count.getCount(), is(TEST_TITLE_COUNT));

    assertThat(count.getValue(), is(TEST_TITLE));
  }

  @Test
  public void countIdsWithIndexAndKey_withYear_returnsCount() {

    Count count = moviesService.countByIndexAndKey(Index.YEAR, TEST_YEAR);

    assertThat(count, is(notNullValue()));

    assertThat(count.getCount(), is(TEST_YEAR_COUNT));

    assertThat(count.getValue(), is(TEST_YEAR));
  }

  @Test
  public void leaderboard_withActor_returnsCount() {

    List<Count> counts = moviesService.leaderboardByIndex(Index.ACTOR);

    assertThat(counts, is(notNullValue()));

    assertThat(counts.isEmpty(), is(false));
  }

  @Test
  public void leaderboard_withDirector_returnsCount() {

    List<Count> counts = moviesService.leaderboardByIndex(Index.DIRECTOR);

    assertThat(counts, is(notNullValue()));

    assertThat(counts.isEmpty(), is(false));
  }

  @Test
  public void leaderboard_withGenre_returnsCount() {

    List<Count> counts = moviesService.leaderboardByIndex(Index.GENRE);

    assertThat(counts, is(notNullValue()));

    assertThat(counts.isEmpty(), is(false));
  }

  @Test
  public void leaderboard_withRating_returnsCount() {

    List<Count> counts = moviesService.leaderboardByIndex(Index.RATING);

    assertThat(counts, is(notNullValue()));

    assertThat(counts.isEmpty(), is(false));
  }

  @Test
  public void leaderboard_withTag_returnsCount() {

    List<Count> counts = moviesService.leaderboardByIndex(Index.TAG);

    assertThat(counts, is(notNullValue()));

    assertThat(counts.isEmpty(), is(false));
  }

  @Test
  public void leaderboard_withTitle_returnsCount() {

    List<Count> counts = moviesService.leaderboardByIndex(Index.TITLE);

    assertThat(counts, is(notNullValue()));

    assertThat(counts.isEmpty(), is(false));
  }

  @Test
  public void leaderboard_withYear_returnsCount() {

    List<Count> counts = moviesService.leaderboardByIndex(Index.YEAR);

    assertThat(counts, is(notNullValue()));

    assertThat(counts.isEmpty(), is(false));
  }

}
