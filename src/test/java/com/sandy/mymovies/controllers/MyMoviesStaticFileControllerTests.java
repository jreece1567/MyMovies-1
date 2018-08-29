package com.sandy.mymovies.controllers;

import com.sandy.mymovies.services.MyStaticFileService;
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
