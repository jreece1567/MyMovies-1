//package com.sandy.mymovies.controllers;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import com.sandy.mymovies.MyMoviesApplication;
//import com.sandy.mymovies.repositories.ActorRepository;
//import com.sandy.mymovies.repositories.ChapterRepository;
//import com.sandy.mymovies.repositories.GenreRepository;
//import com.sandy.mymovies.repositories.TagRepository;
//import com.sandy.mymovies.repositories.VideoRepository;
//import junit.framework.TestCase;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = MyMoviesApplication.class)
//@AutoConfigureMockMvc
//public class MyMoviesControllerTests {
//
//  @Autowired
//  private MockMvc mockMvc;
//
//  @MockBean
//  ActorRepository actorRepository;
//
//  @MockBean
//  ChapterRepository chapterRepository;
//
//  @MockBean
//  GenreRepository genreRepository;
//
//  @MockBean
//  TagRepository tagRepository;
//
//  @MockBean
//  VideoRepository videoRepository;
//
//  /**
//   * See if we can fetch a movie.
//   */
//  @Test
//  public void fetchMovie_returnsMovie() {
//
//    try {
//      mockMvc.perform(get("/movie/0128442").contentType(MediaType.APPLICATION_JSON))
//          .andExpect(status().isOk());
//    } catch (Exception ex) {
//      TestCase.fail(ex.getMessage());
//    }
//
//
//  }
//
//}
