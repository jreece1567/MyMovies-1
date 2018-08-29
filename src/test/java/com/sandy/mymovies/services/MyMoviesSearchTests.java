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
public class MyMoviesSearchTests {

  @Autowired
  MyMoviesService moviesService;

  @Test
  public void searchActor_returnsValues() {

    List<String> results = moviesService.searchByIndex(Index.ACTOR, "Matt");

    assertThat(results, is(notNullValue()));

    assertThat(results.isEmpty(), is(false));

  }

  @Test
  public void searchDirector_returnsValues() {

    List<String> results = moviesService.searchByIndex(Index.DIRECTOR, "Stan");

    assertThat(results, is(notNullValue()));

    assertThat(results.isEmpty(), is(false));

  }

  @Test
  public void searchGenre_returnsValues() {

    List<String> results = moviesService.searchByIndex(Index.GENRE, "Fa");

    assertThat(results, is(notNullValue()));

    assertThat(results.isEmpty(), is(false));

  }

  @Test
  public void searchRating_returnsValues() {

    List<String> results = moviesService.searchByIndex(Index.RATING, "PG");

    assertThat(results, is(notNullValue()));

    assertThat(results.isEmpty(), is(false));

  }

  @Test
  public void searchTag_returnsValues() {

    List<String> results = moviesService.searchByIndex(Index.TAG, "Star");

    assertThat(results, is(notNullValue()));

    assertThat(results.isEmpty(), is(false));

  }

  @Test
  public void searchTitle_returnsValues() {

    List<String> results = moviesService.searchByIndex(Index.TITLE, "Jur");

    assertThat(results, is(notNullValue()));

    assertThat(results.isEmpty(), is(false));

  }

  @Test
  public void searchYear_returnsValues() {

    List<String> results = moviesService.searchByIndex(Index.YEAR, "1960");

    assertThat(results, is(notNullValue()));

    assertThat(results.isEmpty(), is(false));

  }

}
