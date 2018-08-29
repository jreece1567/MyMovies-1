package com.sandy.mymovies.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MyMoviesCorsConfig implements WebMvcConfigurer {

  /**
   * The CORS pre-flight-request maxAge, in seconds. Defaults to 3600, meaning 1 hour.
   */
  @Value("${cors.maxAge:3600}")
  Long maxAge;

  /**
   * The CORS allowed-origins, as a list of hostnames (e.g. "http://myhost.com,http://yourhost.com").
   * Defaults to '*', meaning 'all hosts'.
   */
  @Value("${cors.allowedOrigins:*}")
  String[] allowedOrigins;

  @Override
  public void addCorsMappings(CorsRegistry registry) {

    // apply these CORS rules to all paths.
    final CorsRegistration registration = registry.addMapping("/**");

    registration.allowedOrigins(allowedOrigins);

    registration.maxAge(maxAge);

    // allow CORS for all methods.
    registration.allowedMethods(RequestMethod.GET.name(), RequestMethod.POST.name(),
        RequestMethod.PATCH.name(), RequestMethod.PUT.name(), RequestMethod.DELETE.name(),
        RequestMethod.OPTIONS.name(),
        RequestMethod.HEAD.name());

  }

}
