package com.sandy.mymovies.configs;

import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;

@Configuration
public class MyMoviesCacheConfig {

  @Value("${cache.clientCacheMaxAge:604800}")
  private transient Long cacheMaxAge;

  /**
   * Configure and return the CacheControl instance to be used for JSON and JPG responses. The
   * following Cache-Control header values are set:
   * <ul>
   * <li>max-age</li>
   * <li>s-maxage</li>
   * <li>stale-if-error</li>
   * <li>stale-while-revalidate</li>
   * <li>public</li>
   * </ul>
   *
   * @return The configured CacheControl instance.
   */
  @Bean
  public CacheControl cacheControl() {
    return CacheControl
        .maxAge(cacheMaxAge, TimeUnit.SECONDS)
        .sMaxAge(cacheMaxAge, TimeUnit.SECONDS)
        .staleIfError(cacheMaxAge, TimeUnit.SECONDS)
        .staleWhileRevalidate(cacheMaxAge, TimeUnit.SECONDS)
        .cachePublic();
  }

}
