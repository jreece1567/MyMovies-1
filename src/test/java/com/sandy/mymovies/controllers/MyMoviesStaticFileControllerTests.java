package com.sandy.mymovies.controllers;

import static junit.framework.TestCase.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sandy.mymovies.MyMoviesApplication;
import com.sandy.mymovies.services.MyStaticFileService;
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
public class MyMoviesStaticFileControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  MyStaticFileService staticFileService;

  @Test
  public void fetchPosterImageWithValidImdbId_returnsImage() {
    try {
      mockMvc.perform(get("/image/0128442").contentType(MediaType.IMAGE_JPEG))
          .andExpect(status().isOk());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }

  @Test
  public void fetchPosterImageWithInvalidImdbId_returnsError() {
    try {
      mockMvc.perform(get("/image/0999999").contentType(MediaType.IMAGE_JPEG))
          .andExpect(status().isNotFound());
    } catch (Exception ex) {
      fail(ex.getMessage());
    }
  }

}
