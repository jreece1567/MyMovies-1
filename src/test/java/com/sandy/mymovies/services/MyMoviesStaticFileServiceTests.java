package com.sandy.mymovies.services;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MyMoviesStaticFileServiceTests {

  @Autowired
  MyStaticFileService staticFileService;

  @Test
  public void fetchPosterImage_withValidImdbId_returnsImage() {

    byte[] imageBytes = staticFileService.fetchPosterImage("0128442");

    assertThat(imageBytes.length, greaterThan(0));
  }

  @Test
  public void fetchPosterImage_withInvalidImdbId_returnsException() {

    try {

      byte[] imageBytes = staticFileService.fetchPosterImage("0999999");
      fail();

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }
}
