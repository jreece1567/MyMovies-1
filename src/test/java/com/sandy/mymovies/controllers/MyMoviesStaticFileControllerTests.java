package com.sandy.mymovies.controllers;

import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.models.dto.Movie.MovieBuilder;
import com.sandy.mymovies.services.MyStaticFileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class)
//@WebMvcTest(MyStaticFileController.class)
public class MyMoviesStaticFileControllerTests {

  //@Autowired
  private MockMvc mockMvc;

  //@MockBean
  MyStaticFileService staticFileService;

  //@Test
  public void fetchPosterImageWithValidImdbId_returnsImage() {

  }

  //@Test
  public void fetchPosterImageWithInvalidImdbId_returnsError() {

  }

}
