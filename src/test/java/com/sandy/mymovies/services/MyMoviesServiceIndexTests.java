package com.sandy.mymovies.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.sandy.mymovies.models.dto.Index;
import com.sandy.mymovies.models.dto.Key;
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

    assertThat(keys.size(), is(8));

    assertThat(keys.get(6), is("title"));
  }

  @Test
  public void readIndex_withActor_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.ACTOR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(69718));

    assertThat(keys.get(17), is("A. Philip Randolph"));
  }

  @Test
  public void readIndex_withDirector_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.DIRECTOR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(632));

    assertThat(keys.get(29), is("Andy Mikita"));
  }

  @Test
  public void readIndex_withGenre_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.GENRE);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(25));

    assertThat(keys.get(4), is("Comedy"));
  }

  @Test
  public void readIndex_withRating_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.RATING);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(16));

    assertThat(keys.get(6), is("PASSED"));

  }

  @Test
  public void readIndex_withTag_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.TAG);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(19));

    assertThat(keys.get(14), is("TV"));

  }

  @Test
  public void readIndex_withTitle_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.TITLE);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(1290));

    assertThat(keys.get(35), is("A Bridge Too Far"));

  }

  @Test
  public void readIndex_withYear_returnsKeys() {

    List<String> keys = moviesService.readIndex(Index.YEAR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(88));

    assertThat(keys.get(87), is("2018"));

  }

  @Test
  public void readTitlesWithIndexAndKey_withActor_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.ACTOR, "Matt Damon");

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(20));

    assertThat(titles.get(17).getTitle(), is("The Martian"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withDirector_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.DIRECTOR, "Stanley Kubrick");

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(6));

    assertThat(titles.get(3).getTitle(), is("Full Metal Jacket"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withGenre_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.GENRE, "Family");

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(11));

    assertThat(titles.get(10).getTitle(), is("WALL-E"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withRating_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.RATING, "M");

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(8));

    assertThat(titles.get(3).getTitle(), is("Outrageous Fortune"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withTag_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.TAG, "Science");

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(21));

    assertThat(titles.get(12).getTitle(), is("The Blue Planet"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withTitle_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.TITLE, "Ex Machina");

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(1));

    assertThat(titles.get(0).getTitle(), is("Ex Machina"));
  }

  @Test
  public void readTitlesWithIndexAndKey_withYear_returnsTitles() {

    List<Title> titles = moviesService.readTitlesByIndexAndKey(Index.YEAR, "1960");

    assertThat(titles, is(notNullValue()));

    assertThat(titles.isEmpty(), is(false));

    assertThat(titles.size(), is(6));

    assertThat(titles.get(3).getTitle(), is("Psycho"));
  }

  @Test
  public void readIdsWithIndex_withActor_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.ACTOR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(69718));
  }

  @Test
  public void readIdsWithIndex_withDirector_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.DIRECTOR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(632));
  }

  @Test
  public void readIdsWithIndex_withGenre_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.GENRE);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(25));
  }

  @Test
  public void readIdsWithIndex_withRating_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.RATING);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(16));
  }

  @Test
  public void readIdsWithIndex_withTag_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.TAG);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(19));
  }

  @Test
  public void readIdsWithIndex_withTitle_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.TITLE);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(1290));
  }

  @Test
  public void readIdsWithIndex_withYear_returnsKeys() {

    List<Key> keys = moviesService.readIdsByIndex(Index.YEAR);

    assertThat(keys, is(notNullValue()));

    assertThat(keys.isEmpty(), is(false));

    assertThat(keys.size(), is(88));
  }

  @Test
  public void readIdsWithIndexAndKey_withActor_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.ACTOR, "Matt Damon");

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(20));

    assertThat(key.getKey(), is("Matt Damon"));
  }

  @Test
  public void readIdsWithIndexAndKey_withDirector_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.DIRECTOR, "Stanley Kubrick");

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(6));

    assertThat(key.getKey(), is("Stanley Kubrick"));
  }

  @Test
  public void readIdsWithIndexAndKey_withGenre_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.GENRE, "Family");

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(11));

    assertThat(key.getKey(), is("Family"));
  }

  @Test
  public void readIdsWithIndexAndKey_withRating_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.RATING, "M");

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(8));

    assertThat(key.getKey(), is("M"));
  }

  @Test
  public void readIdsWithIndexAndKey_withTag_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.TAG, "Science");

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(21));

    assertThat(key.getKey(), is("Science"));
  }

  @Test
  public void readIdsWithIndexAndKey_withTitle_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.TITLE, "Ex Machina");

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(1));

    assertThat(key.getKey(), is("Ex Machina"));
  }

  @Test
  public void readIdsWithIndexAndKey_withYear_returnsIds() {

    Key key = moviesService.readIdsByIndexAndKey(Index.YEAR, "1960");

    assertThat(key, is(notNullValue()));

    assertThat(key.getIds().isEmpty(), is(false));

    assertThat(key.getIds().size(), is(6));

    assertThat(key.getKey(), is("1960"));
  }

}