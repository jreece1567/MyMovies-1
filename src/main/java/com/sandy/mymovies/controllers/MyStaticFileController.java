package com.sandy.mymovies.controllers;

import com.sandy.mymovies.services.MyStaticFileService;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Provides HTTP 'transport layer' support for the application. Any type of static file can be
 * served from this class (JPG, CSS, JS, etc.).
 */
@Controller
public class MyStaticFileController {

  MyStaticFileService staticFileService;

  @Autowired
  public MyStaticFileController(MyStaticFileService staticFileService) {

    this.staticFileService = staticFileService;
  }

  /**
   * Exception handler if NoSuchElementException is thrown.
   *
   * @param ex the thrown exception.
   * @return Error message String.
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public String return404(final NoSuchElementException ex) {

    return ex.getMessage();
  }

  /**
   * Fetch the poster-image bytes for a given imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the image-bytes, with appropriate content-type and content-length headers.
   */
  @RequestMapping(path = "/image/{imdbId}", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<byte[]> fetchImageForImdbId(@PathVariable("imdbId") String imdbId) {

    byte[] imageBytes = staticFileService.fetchPosterImage(imdbId);

    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentLength(imageBytes.length)
        .body(imageBytes);
  }

  /**
   * Fetch the favicon.ico.
   *
   * @return the image-bytes, with appropriate content-type and content-length headers.
   */
  @RequestMapping(path = "/favicon.ico", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<byte[]> fetchFavicon() {

    byte[] imageBytes = staticFileService.fetchFavicon();

    return ResponseEntity.ok().contentType(new MediaType("image", "x-icon"))
        .contentLength(imageBytes.length)
        .body(imageBytes);
  }

}
