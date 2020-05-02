package com.sandy.mymovies.controllers;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
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
import org.hamcrest.collection.IsEmptyCollection;
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
  public void fetchTitle_withvalidmovieid_returnsTitle() {

    try {
      mockMvc.perform(get("/title/0128442").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("title", is("Rounders")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchTitle_withinvalidmovieid_returns404() {

    try {
      mockMvc.perform(get("/title/9999999").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchCast_withvalidmovieid_returnsCast() {

    try {
      mockMvc.perform(get("/cast/0128442").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0]", is(notNullValue())));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchCast_withinvalidmovieid_returns404() {

    try {
      mockMvc.perform(get("/cast/9999999").contentType(MediaType.APPLICATION_JSON))
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
  public void fetchIds_withvalidindex_returnsIds() {

    try {
      mockMvc.perform(get("/index/genre").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].key", is("Action")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIds_withinvalidindex_returns422() {

    try {
      mockMvc.perform(get("/index/genrez").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isUnprocessableEntity());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIds_withvalidindexandkey_returnsIds() {

    try {
      mockMvc.perform(get("/index/genre/Documentary").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("key", is("Documentary")))
          .andExpect(jsonPath("ids[0]", is(notNullValue())));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchIds_withinvalidindexkey_returnsEmptyArray() {

    try {
      mockMvc.perform(get("/index/genre/zzz").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("key", is("zzz")))
          .andExpect(jsonPath("ids", IsEmptyCollection.empty()));
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
  public void fetchSeasons_withvalidquery_returnsSeasons() {

    try {
      mockMvc.perform(get("/seasons/5651844").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0]", is(1)))
          .andExpect(jsonPath("$[1]]", is(2)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchSeasons_withinvalidquery_returnsEmptyArray() {

    try {
      mockMvc.perform(get("/seasons/0128442").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(content().string("[]"));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void fetchEpisodes_withvalidimdbId_returnsEpisodes() {
    try {
      mockMvc.perform(get("/episodes/5651844").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].imdbId", is("5651844")))
          .andExpect(jsonPath("$[0].season", is("1")))
          .andExpect(jsonPath("$[0].episodeNumber", is("1")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }

  @Test
  public void fetchEpisodes_withvalidseason_returnsEpisodes() {
    try {
      mockMvc.perform(get("/episodes/5651844/2").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$[0].imdbId", is("5651844")))
          .andExpect(jsonPath("$[0].season", is("2")))
          .andExpect(jsonPath("$[0].episodeNumber", is("1")));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }

  @Test
  public void fetchEpisodes_withinvalidseason_returns404() {
    try {
      mockMvc.perform(get("/episodes/5651844/9").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isNotFound())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }

  @Test
  public void searchIndexEntry_withvalidquery_returnsIndexEntries() {

    try {
      mockMvc.perform(get("/search/genre?q=cum").contentType(MediaType.APPLICATION_JSON))
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

  @Test
  public void countIndexEntry_withvalidquery_returnsCount() {

    try {
      mockMvc.perform(get("/count/genre?name=Family").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.value", is("Family")))
          .andExpect(jsonPath("$.count", is(18)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

  @Test
  public void countIndexEntry_withinvalidquery_returnsZero() {

    try {
      mockMvc.perform(get("/count/genre?name=zzzz").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
          .andExpect(jsonPath("$.value", is("zzzz")))
          .andExpect(jsonPath("$.count", is(0)));
    } catch (Exception ex) {
      fail(ex.getMessage());
    }

  }

}
