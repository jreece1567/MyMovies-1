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

  @Test
  public void fetchMovie_withvalidmovieid_returnsMovie() {

    try {
      mockMvc.perform(get("/movie/0128442").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("title", is("Rounders")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchMovie_withinvalidmovieid_returns404() {

    try {
      mockMvc.perform(get("/movie/9999999").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndexKeys_withvalidkey_returnsIndexKeys() {

    try {
      mockMvc.perform(get("/index/keys/all").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[6]", is("title")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndexKeys_withinvalidkey_returns422() {

    try {
      mockMvc.perform(get("/index/keys/foo").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnprocessableEntity());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndex_withvalidkey_returnsIndexKeys() {

    try {
      mockMvc.perform(get("/index/keys/genre").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[6]", is("Documentary")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndex_withinvalidkey_returns422() {

    try {
      mockMvc.perform(get("/index/keys/genrez").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnprocessableEntity());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndexEntry_withvalidindex_returnsIndexEntries() {

    try {
      mockMvc.perform(get("/titles/genre/Documentary").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[6].imdbId", is("0277457")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIndexEntry_withinvalidindex_returnsEmptyArray() {

    try {
      mockMvc.perform(get("/titles/genre/Documentaryz").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void searchIndexEntry_withvalidquery_returnsIndexEntries() {

    try {
      mockMvc.perform(get("/search/genre?q=Doc").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0]", is("Documentary")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void searchIndexEntry_withinvalidquery_returnsEmptyArray() {

    try {
      mockMvc.perform(get("/search/genre?q=zzzz").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

}
