package com.sandy.mymovies.models.dto;

import java.util.List;

import lombok.Data;

/**
 * An index-key value, and a list of all associated imdbIds.
 */
@Data
public class Key {

  private String key;

  private List<String> ids;
}
