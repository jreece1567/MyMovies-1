package com.sandy.mymovies.services;

import com.sandy.mymovies.models.domain.Actor;
import com.sandy.mymovies.models.domain.Episode;
import com.sandy.mymovies.models.domain.Genres;
import com.sandy.mymovies.models.domain.Movie;
import com.sandy.mymovies.models.domain.Tags;
import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Index;
import com.sandy.mymovies.models.dto.Title;
import com.sandy.mymovies.models.dto.Video;
import com.sandy.mymovies.repositories.ActorRepository;
import com.sandy.mymovies.repositories.EpisodeRepository;
import com.sandy.mymovies.repositories.GenreRepository;
import com.sandy.mymovies.repositories.MovieRepository;
import com.sandy.mymovies.repositories.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyMoviesService {

    private MovieRepository movieRepository;
    private EpisodeRepository episodeRepository;
    private ActorRepository actorRepository;
    private TagRepository tagRepository;
    private GenreRepository genreRepository;

    @Autowired
    public MyMoviesService(MovieRepository movieRepository, EpisodeRepository episodeRepository,
            ActorRepository actorRepository, TagRepository tagRepository,
            GenreRepository genreRepository) {
        this.movieRepository = movieRepository;
        this.episodeRepository = episodeRepository;
        this.actorRepository = actorRepository;
        this.tagRepository = tagRepository;
        this.genreRepository = genreRepository;
    }

    /**
     * Creates a Movie, if one with provided imdbId does not exist
     *
     * @return created movie
     */
    public Video createMovie(Video video) {
        Optional<Movie> movie = movieRepository.findById(video.getImdbId());
        if (movie.isPresent()) {
            return video;
        }
        for (String actorName : video.getCast()) {
            actorRepository.save(new Actor(actorName, video.getImdbId()));
        }
        for (String tag : video.getTag()) {
            tagRepository.save(new Tags(tag, video.getImdbId()));
        }
        for (String genre : video.getGenre()) {
            genreRepository.save(new Genres(genre, video.getImdbId()));
        }
        movieRepository.save(
                new Movie(video.getImdbId(), video.getTitle(), video.getReleaseYear(),
                        video.getDuration(),
                        video.getRating(), video.getDirector(), video.getImageUrl(),
                        video.getDescription()));
        return video;
    }

    /**
     * Creates an Episode, if one with provided imdbId does not exist
     *
     * @return created episode
     */
    public Episode createEpisode(String imdbId, Integer season,
            Integer episodeNumber, String title, String description) {
//		@todo- use episodeRepo to see if imdbId,season,episode already exists
//		if (episode.isPresent()) {
//    	return episode;
//		}
        return episodeRepository.save(
                new Episode(imdbId, season, episodeNumber, title, description)
        );
    }

    /**
     * Verify and return a Movie given a imdbId
     *
     * @return the found movie
     */
    public Movie verifyMovie(String imdbId) {
        Optional<Movie> movie = movieRepository.findById(imdbId);
        if (!movie.isPresent()) {
            // throw exception?
        }
        return movie.get();
    }

    public Title verifyTitle(String imdbId) {

        Optional<Movie> movie = movieRepository.findById(imdbId);
        if (!movie.isPresent()) {
            // throw exception?
        }
        return new Title(movie.get().getImdbId(), movie.get().getTitle(),
                movie.get().getReleaseYear(), movie.get().getImageUrl());
    }

    public Cast verifyCast(String imdbId) {
        List<Actor> actors = actorRepository.findAllByImdbId(imdbId);
        if (actors == null || actors.isEmpty()) {
            // throw exception?
        }
        Cast cast = new Cast();
        for (Actor actor : actors) {
            cast.add(actor.getName());
        }
        return cast;
    }

    public List<Episode> verifyEpisodes(String imdbId) {
        return episodeRepository.findAllByImdbId(imdbId);
    }

    public List<Episode> verifyEpisodes(String imdbId, Integer seasonNumber) {
        return episodeRepository.findAllByImdbIdAndSeason(imdbId, seasonNumber);
    }

    public List<String> verifyImdbAndReturnSeasons(String imdbId) {
        return episodeRepository.findDistinctSeasonsByImdbId(imdbId);
    }

    public List<String> verifyIndex(Index index) {

        List<String> keys = new ArrayList<>();

        switch (index) {
            case ALL:
                for (Index idx : Index.values()) {
                    keys.add(idx.toString());
                }
                break;
            case TAG:
                break;
            case ACTOR:
                List<Actor> actors = actorRepository.findAllDistinctActors();
                for (Actor actor : actors) {
                    keys.add(actor.getName());
                }
                break;
            case GENRE:
                break;
            case DIRECTOR:
                keys = movieRepository.findAllDistinctDirectors();
                break;
            case RATING:
                keys = movieRepository.findAllDistinctRatings();
                break;
            case TITLE:
                keys = movieRepository.findAllDistinctTitles();
                break;
            case YEAR:
                List<Integer> years = movieRepository.findAllDistinctReleaseYears();
                for (Integer year : years) {
                    keys.add(String.valueOf(year));
                }
                break;
            default:
                break;
        }

        return keys;
    }

    public List<Title> verifyTitlesByIndexAndKey(Index index, String key) {

        List<Movie> movies = new ArrayList<>();

        switch (index) {
            case TAG:
                break;
            case ACTOR:
                List<Actor> imdbIds = actorRepository.findAllByName(key);
                for (Actor actor : imdbIds) {
                    movies.add(movieRepository.findById(actor.getImdbId()).get());
                }
                break;
            case GENRE:
                break;
            case DIRECTOR:
                movies = movieRepository.findAllByDirector(key);
                break;
            case RATING:
                movies = movieRepository.findAllByRating(key);
                break;
            case TITLE:
                movies = movieRepository.findAllByTitle(key);
                break;
            case YEAR:
                movies = movieRepository.findAllByReleaseYear(Integer.valueOf(key));
                break;
            default:
                break;
        }

        List<Title> titles = new ArrayList<>();
        for (Movie movie : movies) {
            titles.add(new Title(movie.getImdbId(), movie.getTitle(), movie.getReleaseYear(),
                    movie.getImageUrl()));
        }

        return titles;
    }

}
