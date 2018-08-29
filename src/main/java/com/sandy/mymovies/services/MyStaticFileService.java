package com.sandy.mymovies.services;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

/**
 * Provides access to static-file resources.
 */
@Log
@Service
public class MyStaticFileService {

  /**
   * Read and return the poster-image bytes for given imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the image bytes.
   */
  public byte[] fetchPosterImage(String imdbId) {

    final String filename = "/db/" + imdbId + ".jpg";
    final InputStream imageInput = getClass().getResourceAsStream(filename);
    if (imageInput == null) {
      throw new NoSuchElementException(filename + " not found.");
    }

    byte[] imageBytes;
    try {

      imageBytes = fetchImageBytes(imageInput);

    } catch (IOException ex) {
      log.info("Error reading image from " + filename + " - " + ex.getMessage());
      throw new NoSuchElementException("Error reading image from " + filename);
    }

    return imageBytes;
  }

  /**
   * Read and return the favicon-image bytes.
   *
   * @return the image bytes.
   */
  public byte[] fetchFavicon() {

    final String filename = "/images/" + "favicon.ico";
    final InputStream imageInput = getClass().getResourceAsStream(filename);
    if (imageInput == null) {
      throw new NoSuchElementException(filename + " not found.");
    }

    byte[] imageBytes;
    try {

      imageBytes = fetchImageBytes(imageInput);

    } catch (IOException ex) {
      log.info("Error reading image from " + filename + " - " + ex.getMessage());
      throw new NoSuchElementException("Error reading image from " + filename);
    }

    return imageBytes;
  }

  /**
   * Read image bytes from a stream, return as a byte array.
   *
   * @param imageInput the input stream.
   * @return a byte array containing the image bytes.
   */
  private byte[] fetchImageBytes(InputStream imageInput) throws IOException {

    final BufferedInputStream imageReader = new BufferedInputStream(imageInput);

    final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    byte[] buffer = new byte[8192];

    int len = 0;
    do {
      len = imageReader.read(buffer);
      if (len != -1) {
        baos.write(buffer, 0, len);
      }
    } while (len != -1);

    return baos.toByteArray();
  }
}
