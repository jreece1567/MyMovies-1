package com.sandy.mymovies.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

@Log
@Controller
public class MyImageController {

  /**
   * Exception handler if NoSuchElementException is thrown
   *
   * @param ex the thrown exception.
   * @return Error message String.
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(NoSuchElementException.class)
  public String return404(final NoSuchElementException ex) {

    return ex.getMessage();
  }

  @RequestMapping(path = "/image/{imdbId}", method = RequestMethod.GET)
  public ResponseEntity<byte[]> fetchImageForImdbId(@PathVariable("imdbId") String imdbId) {

    final InputStream imageInput = getClass().getResourceAsStream("/db/" + imdbId + ".jpg");
    if (imageInput == null) {
      throw new NoSuchElementException("File " + imdbId + ".jpg not found.");
    }

    final BufferedInputStream imageReader =
        new BufferedInputStream(imageInput);
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    byte[] buffer = new byte[8192];
    int contentLength = 0;
    try {

      int len = 0;
      do {
        len = imageReader.read(buffer);
        contentLength = contentLength + len;
        baos.write(buffer, 0, len);
      } while (len != -1);

    } catch (IOException ex) {
      log.info("Error reading image for imdbId " + imdbId + " - " + ex.getMessage());
      throw new NoSuchElementException("Error reading image for imdbId " + imdbId);
    }

    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentLength(contentLength)
        .body(baos.toByteArray());
  }
}
