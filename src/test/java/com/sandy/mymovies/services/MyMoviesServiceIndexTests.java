package com.sandy.mymovies.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.sandy.mymovies.models.dto.Index;
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

}
