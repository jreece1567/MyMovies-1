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
public final class MyStaticFileService {

  private static final String ERROR_MSG = "Error reading image from ";

  /**
   * Read and return the poster-image bytes for given imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the image bytes.
   */
  public byte[] fetchPosterImage(final String imdbId) {

    return fetchBytesFromResource("/db/" + imdbId + ".jpg");
  }

  /**
   * Read and return the favicon-image bytes.
   *
   * @return the image bytes.
   */
  public byte[] fetchFavicon() {

    return fetchBytesFromResource("/images/" + "favicon.ico");
  }

  /**
   * Read and return a static asset.
   *
   * @return the asset bytes.
   */
  public byte[] fetchStaticAsset(String filename) {

    return fetchBytesFromResource(filename);
  }

  private byte[] fetchBytesFromResource(String filename) {

    byte[] resourceBytes;

    try (final InputStream resourceInput = getClass().getResourceAsStream(filename)) {

      if (resourceInput == null) {
        throw new NoSuchElementException(filename + " not found.");
      }

      final BufferedInputStream resourceReader = new BufferedInputStream(resourceInput);
      final ByteArrayOutputStream baos = new ByteArrayOutputStream();
      final byte[] buffer = new byte[8192];

      int len = 0;
      do {
        len = resourceReader.read(buffer);
        if (len != -1) {
          baos.write(buffer, 0, len);
        }
      } while (len != -1);

      resourceBytes = baos.toByteArray();

    } catch (IOException ex) {
      log.info(ERROR_MSG + filename + " - " + ex.getMessage());
      throw new NoSuchElementException(ERROR_MSG + filename);
    }

    return resourceBytes;
  }

}
