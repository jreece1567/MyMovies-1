package com.sandy.mymovies.configs;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

@Configuration
public class MyMoviesRequestLoggingConfig {

  /**
   * Create and configure the request logging filter.
   *
   * @return the request logging filter
   */
  @Bean
  public CommonsRequestLoggingFilter logFilter() {

    final CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter() {
      @Override
      protected void beforeRequest(final HttpServletRequest request, final String message) {
        final String msg = message.replaceAll("Before request", "Request: " + request.getMethod());
        logger.debug(msg);
      }

      @Override
      protected void afterRequest(final HttpServletRequest request, final String message) {
        // do nothing
      }

      @Override
      protected void doFilterInternal(final HttpServletRequest request,
          final HttpServletResponse response,
          final FilterChain filterChain) throws ServletException, IOException {

        //same mechanism as for request caching in superclass
        HttpServletResponse responseToUse = response;
        if (isIncludePayload() && !isAsyncDispatch(request)
            && !(response instanceof ContentCachingResponseWrapper)) {
          responseToUse = new ContentCachingResponseWrapper(response);
        }

        //incoming request is logged in superclass
        super.doFilterInternal(request, responseToUse, filterChain);

        //log outgoing response
        final String message =
            this.createMessage(request, "Response To Request: " + request.getMethod() + " [", "]")
                + getResponseMessage(responseToUse);
        logger.debug(message);

        if (responseToUse instanceof ContentCachingResponseWrapper) {
          ((ContentCachingResponseWrapper) responseToUse).copyBodyToResponse();
        }
      }

      //equivalent to super.createMessage() for request logging
      private String getResponseMessage(final HttpServletResponse rsp) {

        final StringBuilder msg = new StringBuilder();

        final ContentCachingResponseWrapper wrapper =
            WebUtils.getNativeResponse(rsp, ContentCachingResponseWrapper.class);

        if (wrapper != null) {

          if (rsp.getContentType() != null && !rsp.getContentType()
              .startsWith("image") && !rsp.getContentType()
              .startsWith("application/octet-stream")) {
            final byte[] buf = wrapper.getContentAsByteArray();
            if (buf.length > 0) {
              final int length = Math.min(buf.length, getMaxPayloadLength());
              String payload;
              try {
                payload = new String(buf, 0, length, wrapper.getCharacterEncoding());
              } catch (UnsupportedEncodingException ex) {
                payload = "[unknown]";
              }
              msg.append(";payload=[").append(payload).append("]");
            }
          } else {
            msg.append(";payload=")
                .append("[not logging response of media-type: " + rsp.getContentType() + "]");
          }
        }

        return msg.toString();
      }

    };

    filter.setIncludeQueryString(true);
    filter.setIncludePayload(true);
    filter.setMaxPayloadLength(65536);
    filter.setIncludeHeaders(true);
    filter.setIncludeClientInfo(true);

    return filter;
  }
}
