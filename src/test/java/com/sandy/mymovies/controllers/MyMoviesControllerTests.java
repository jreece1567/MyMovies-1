package com.sandy.mymovies.controllers;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sandy.mymovies.MyMoviesApplication;
import com.sandy.mymovies.repositories.ActorRepository;
import com.sandy.mymovies.repositories.ChapterRepository;
import com.sandy.mymovies.repositories.GenreRepository;
import com.sandy.mymovies.repositories.TagRepository;
import com.sandy.mymovies.repositories.VideoRepository;
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

  /**
   * See if we can fetch a movie.
   */
  @Test
  public void fetchMovie_returnsMovie() {

    try {
      mockMvc.perform(get("/movie/0128442").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("title", is("Rounders")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  /**
   * See if we can fetch an index.
   */
  @Test
  public void fetchIndex_returnsIndex() {

    try {
      mockMvc.perform(get("/index/keys/all").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[6]", is("title")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

}
