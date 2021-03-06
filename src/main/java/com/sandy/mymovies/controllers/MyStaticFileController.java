package com.sandy.mymovies.controllers;

import com.sandy.mymovies.configs.MyMoviesCacheConfig;
import com.sandy.mymovies.services.MyStaticFileService;
import com.sandy.mymovies.services.util.MimeTypes;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Provides HTTP 'transport layer' support for the application. Any type of static file can be
 * served from this class (JPG, CSS, JS, etc.).
 */
@RestController
public class MyStaticFileController {

  private final MyStaticFileService staticFileService;
  private final MyMoviesCacheConfig cacheConfig;

  /**
   * Construct a new controller instance.
   *
   * @param staticFileService the service.
   * @param cacheConfig the cache-control.
   */
  @Autowired
  public MyStaticFileController(final MyStaticFileService staticFileService,
      final MyMoviesCacheConfig cacheConfig) {

    this.staticFileService = staticFileService;
    this.cacheConfig = cacheConfig;
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
  public ResponseEntity<byte[]> fetchImageForImdbId(@PathVariable("imdbId") final String imdbId) {

    final byte[] imageBytes = staticFileService.fetchPosterImage(imdbId);

    return ResponseEntity.ok().cacheControl(cacheConfig.cacheControl())
        .contentType(MediaType.IMAGE_JPEG)
        .contentLength(imageBytes.length)
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

    final byte[] imageBytes = staticFileService.fetchFavicon();

    return ResponseEntity.ok().cacheControl(cacheConfig.cacheControl())
        .contentType(new MediaType("image", "x-icon"))
        .contentLength(imageBytes.length)
        .body(imageBytes);
  }

  /**
   * Fetch any static asset.
   *
   * @return the asset, with appropriate content-type and content-length headers.
   */
  @RequestMapping(path = "/static/**", method = RequestMethod.GET)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<byte[]> fetchStaticAsset() {

    final ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();

    // get the asset filepath/filename.ext
    final String ext = builder.removePathExtension();
    final String filename = builder.scheme(null).host(null).port(null).build()
        .toUriString().replaceFirst("/static","");
    final String fileExtension = ext != null ? "." + ext : "";

    // get the MIME type
    final MimeType mimeType = MimeTypeUtils.parseMimeType(MimeTypes.MIME.getMimeType(ext));
    final MediaType mediaType = new MediaType(mimeType.getType(),mimeType.getSubtype());

    final byte[] assetBytes = staticFileService.fetchStaticAsset(filename + fileExtension);

    return ResponseEntity.ok().cacheControl(cacheConfig.cacheControl())
        .contentType(mediaType)
        .contentLength(assetBytes.length)
        .body(assetBytes);
  }

}
