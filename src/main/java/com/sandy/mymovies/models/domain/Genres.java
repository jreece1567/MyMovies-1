package com.sandy.mymovies.models.domain;

import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genres {

    private String genre;

    private String imdbId;
}
