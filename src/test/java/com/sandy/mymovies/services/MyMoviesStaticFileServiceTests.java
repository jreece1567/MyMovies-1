package com.sandy.mymovies.services;

import static com.sandy.mymovies.MyMoviesTestData.INVALID_IMDB_ID;
import static com.sandy.mymovies.MyMoviesTestData.TEST_IMDB_ID;
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

    byte[] imageBytes = staticFileService.fetchPosterImage(TEST_IMDB_ID);

    assertThat(imageBytes.length, greaterThan(0));
  }

  @Test
  public void fetchPosterImage_withInvalidImdbId_returnsException() {

    try {

      byte[] imageBytes = staticFileService.fetchPosterImage(INVALID_IMDB_ID);
      fail();

    } catch (NoSuchElementException ex) {
      assertThat(ex, instanceOf(NoSuchElementException.class));
    }

  }

  @Test
  public void fetchFaviconImage_returnsImage() {

    byte[] imageBytes = staticFileService.fetchFavicon();

    assertThat(imageBytes.length, greaterThan(0));
  }

}
