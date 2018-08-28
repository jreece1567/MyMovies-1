package com.sandy.mymovies.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.sandy.mymovies.models.dto.Movie;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyMoviesServiceTest {

  @Autowired
  MyMoviesService moviesService;

  @Test
  public void whenFindByImdbId_thenReturnMovie() {

    Movie movie = moviesService.readMovie("0128442");

    assertThat(movie, is(notNullValue()));

    assertThat(movie.getTitle(), is("Rounders"));

  }
}
