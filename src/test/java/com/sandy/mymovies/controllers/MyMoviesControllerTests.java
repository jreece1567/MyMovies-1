package com.sandy.mymovies.controllers;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Genres;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.models.dto.Tags;
import com.sandy.mymovies.services.MyMoviesService;
import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
//@WebMvcTest(MyMoviesController.class)
public class MyMoviesControllerTests {

  //@MockBean
  MyMoviesService moviesService;
  //@Autowired
  private MockMvc mockMvc;

  //@Test
  public void fetchMovie_returnsMovie() {

    Movie.MovieBuilder builder = Movie.builder();
    builder.imdbId("0128442").releaseYear(1998).duration("2:01").rating("R").director("John Dahl")
        .title("Rounders").imageUrl("0128442.jpg").description(
        "A young man is a reformed gambler who must return to"
            + "playing big stakes poker to help a friend pay off loan sharks.");

    Genres genres = new Genres();
    genres.addAll(Arrays.asList("Crime", "Drama"));
    builder.genres(genres);

    builder.tags(new Tags());

    Cast cast = new Cast();
    cast.addAll(Arrays.asList("Matt Damon", "Gretchen Mol", "John Malkovich"));
    builder.cast(cast);

    Movie movie = builder.build();
    try {
      mockMvc.perform(get("/movies/0128442").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    } catch (Exception ex) {
      fail();
    }
  }

}
