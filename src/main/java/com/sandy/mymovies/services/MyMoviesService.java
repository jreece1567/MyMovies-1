package com.sandy.mymovies.services;

import com.sandy.mymovies.models.domain.Actor;
import com.sandy.mymovies.models.domain.Chapter;
import com.sandy.mymovies.models.domain.Genre;
import com.sandy.mymovies.models.domain.Tag;
import com.sandy.mymovies.models.domain.Video;
import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Episode;
import com.sandy.mymovies.models.dto.Genres;
import com.sandy.mymovies.models.dto.Index;
import com.sandy.mymovies.models.dto.Key;
import com.sandy.mymovies.models.dto.Movie;
import com.sandy.mymovies.models.dto.Tags;
import com.sandy.mymovies.models.dto.Title;
import com.sandy.mymovies.repositories.ActorRepository;
import com.sandy.mymovies.repositories.ChapterRepository;
import com.sandy.mymovies.repositories.GenreRepository;
import com.sandy.mymovies.repositories.TagRepository;
import com.sandy.mymovies.repositories.VideoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyMoviesService {

    private VideoRepository videoRepository;
    private ChapterRepository chapterRepository;
    private ActorRepository actorRepository;
    private TagRepository tagRepository;
    private GenreRepository genreRepository;

    @Autowired
    public MyMoviesService(VideoRepository videoRepository, ChapterRepository chapterRepository,
        ActorRepository actorRepository, TagRepository tagRepository,
        GenreRepository genreRepository) {

        this.videoRepository = videoRepository;
        this.chapterRepository = chapterRepository;
        this.actorRepository = actorRepository;
        this.tagRepository = tagRepository;
        this.genreRepository = genreRepository;
    }

    /**
     * Creates a Movie.
     *
     * @return the created movie, if one was created, or the existing movie if it already exists.
     */
    public Movie createMovie(Movie movie) {

        Optional<Video> video = videoRepository.findById(movie.getImdbId());
        if (!video.isPresent()) {
            // create a new movie in the DB

            // save the Cast
            for (String actorName : movie.getCast()) {
                actorRepository.save(new Actor(actorName, movie.getImdbId()));
            }
            // save the Tags
            for (String tag : movie.getTag()) {
                tagRepository.save(new Tag(tag, movie.getImdbId()));
            }
            // save the Genres
            for (String genre : movie.getGenre()) {
                genreRepository.save(new Genre(genre, movie.getImdbId()));
            }
            // save the Video
            videoRepository.save(
                new Video(movie.getImdbId(), movie.getTitle(), movie.getReleaseYear(),
                    movie.getDuration(),
                    movie.getRating(), movie.getDirector(), movie.getImageUrl(),
                    movie.getDescription()));

        }

        // return the movie from the DB
        return readMovie(movie.getImdbId());
    }

    /**
     * Creates an Episode, if one with provided imdbId/season/episodeNumber does not exist
     *
     * @return created episode
     */
    public Episode createEpisode(String imdbId, Integer season,
        Integer episodeNumber, String title, String description) {

        Optional<Chapter> chapter = chapterRepository
            .findByImdbIdAndSeasonAndEpisodeNumber(imdbId, season, episodeNumber);
        if (chapter.isPresent()) {
            return new Episode(chapter.get().getImdbId(), chapter.get().getSeason(),
                chapter.get().getEpisodeNumber(), chapter.get().getTitle(),
                chapter.get().getDescription());
        }

        Chapter newChapter = chapterRepository.save(
            new Chapter(imdbId, season, episodeNumber, title, description)
        );

        return new Episode(newChapter.getImdbId(), newChapter.getSeason(),
            newChapter.getEpisodeNumber(), newChapter.getTitle(), newChapter.getDescription());
    }

    /**
     * Verify and return a Movie given a imdbId
     *
     * @return the found movie
     */
    public Movie readMovie(String imdbId) {

        // validate the imdbId, fetch the existing Video data
        Optional<Video> video = videoRepository.findById(imdbId);
        if (!video.isPresent()) {
            throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
        }

        // construct a Movie for the response
        Movie movie = new Movie();

        // populate the Movie with the data from the Video
        movie.setImdbId(video.get().getImdbId());
        movie.setTitle(video.get().getTitle());
        movie.setReleaseYear(video.get().getReleaseYear());
        movie.setDuration(video.get().getDuration());
        movie.setRating(video.get().getRating());
        movie.setDirector(video.get().getDirector());
        movie.setImageUrl(video.get().getImageUrl());
        movie.setDescription(video.get().getDescription());

        // populate the Movie genres
        Genres genres = new Genres();
        genres.addAll(genreRepository.findAllByImdbId(movie.getImdbId()));
        movie.setGenre(genres);

        // populate the Movie tags
        Tags tags = new Tags();
        tags.addAll(tagRepository.findAllByImdbId(movie.getImdbId()));
        movie.setTag(tags);

        // populate the Movie cast
        Cast cast = new Cast();
        cast.addAll(actorRepository.findAllByImdbId(movie.getImdbId()));
        movie.setCast(cast);

        return movie;
    }

    /**
     *
     * @param imdbId
     * @return
     */
    public Title readTitle(String imdbId) {

        Optional<Video> video = videoRepository.findById(imdbId);
        if (!video.isPresent()) {
            throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
        }
        return new Title(video.get().getImdbId(), video.get().getTitle(),
            video.get().getReleaseYear(), video.get().getImageUrl());
    }

    /**
     *
     * @param imdbId
     * @return
     */
    public Cast readCast(String imdbId) {
        List<String> actors = actorRepository.findAllByImdbId(imdbId);
        if (actors == null || actors.isEmpty()) {
            throw new NoSuchElementException(String.format("Cast for movie %s not found.", imdbId));
        }
        Cast cast = new Cast();
        cast.addAll(actors);
        return cast;
    }

    /**
     *
     * @param imdbId
     * @return
     */
    public List<Episode> readEpisodes(String imdbId) {

        final List<Chapter> chapters = chapterRepository.findAllByImdbId(imdbId);
        final List<Episode> episodes = new ArrayList<>();
        chapters.forEach(c -> episodes.add(
            new Episode(c.getImdbId(), c.getSeason(), c.getEpisodeNumber(), c.getTitle(),
                c.getDescription())));
        return episodes;
    }

    /**
     *
     * @param imdbId
     * @param seasonNumber
     * @return
     */
    public List<Episode> readEpisodes(String imdbId, Integer seasonNumber) {

        final List<Chapter> chapters = chapterRepository
            .findAllByImdbIdAndSeason(imdbId, seasonNumber);
        final List<Episode> episodes = new ArrayList<>();
        chapters.forEach(chapter -> episodes.add(
            new Episode(chapter.getImdbId(), chapter.getSeason(), chapter.getEpisodeNumber(),
                chapter.getTitle(),
                chapter.getDescription())));
        return episodes;
    }

    /**
     *
     * @param imdbId
     * @return
     */
    public List<String> readSeasons(String imdbId) {

        return chapterRepository.findDistinctSeasonsByImdbId(imdbId);
    }

    /**
     *
     * @param index
     * @return
     */
    public List<String> readIndex(Index index) {

        List<String> keys = new ArrayList<>();

        switch (index) {
            case ALL:
                for (Index idx : Index.values()) {
                    keys.add(idx.toString());
                }
                break;
            case TAG:
                keys.addAll(tagRepository.findAllDistinctTags());
                break;
            case ACTOR:
                keys.addAll(actorRepository.findAllDistinctActors());
                break;
            case GENRE:
                keys.addAll(genreRepository.findAllDistinctGenres());
                break;
            case DIRECTOR:
                keys = videoRepository.findAllDistinctDirectors();
                break;
            case RATING:
                keys = videoRepository.findAllDistinctRatings();
                break;
            case TITLE:
                keys = videoRepository.findAllDistinctTitles();
                break;
            case YEAR:
                List<Integer> years = videoRepository.findAllDistinctReleaseYears();
                for (Integer year : years) {
                    keys.add(String.valueOf(year));
                }
                break;
            default:
                break;
        }

        return keys;
    }

    /**
     *
     * @param index
     * @param key
     * @return
     */
    public List<Title> readTitlesByIndexAndKey(Index index, String key) {

        List<Video> videos = new ArrayList<>();

        switch (index) {
            case TAG:
                for (String imdbId : tagRepository.findAllByTag(key)) {
                    videos.add(videoRepository.findById(imdbId).get());
                }
                break;
            case ACTOR:
                for (String imdbId : actorRepository.findAllByName(key)) {
                    videos.add(videoRepository.findById(imdbId).get());
                }
                break;
            case GENRE:
                for (String imdbId : genreRepository.findAllByGenre(key)) {
                    videos.add(videoRepository.findById(imdbId).get());
                }
                break;
            case DIRECTOR:
                videos = videoRepository.findAllByDirector(key);
                break;
            case RATING:
                videos = videoRepository.findAllByRating(key);
                break;
            case TITLE:
                videos = videoRepository.findAllByTitle(key);
                break;
            case YEAR:
                videos = videoRepository.findAllByReleaseYear(Integer.valueOf(key));
                break;
            default:
                break;
        }

        final List<Title> titles = new ArrayList<>();
        videos.forEach(video -> titles
            .add(new Title(video.getImdbId(), video.getTitle(), video.getReleaseYear(),
                video.getImageUrl())));

        return titles;
    }

    /**
     * @param index the index (tag,actor,genre,director,rating,etc.)
     * @return a list of the distinct values for the index, and the imdbIds associated with each
     * distinct value.
     */
    public List<Key> readIdsByIndex(Index index) {

        final List<Key> keys = new ArrayList<>();

        switch (index) {
            case TAG:
                List<String> tags = tagRepository.findAllDistinctTags();
                tags.forEach(tag -> {
                    Key key = new Key();
                    key.setKey(tag);
                    key.setIds(tagRepository.findAllByTag(tag));
                    keys.add(key);
                });
                break;
            case ACTOR:
                List<String> actors = actorRepository.findAllDistinctActors();
                actors.forEach(actor -> {
                    Key key = new Key();
                    key.setKey(actor);
                    List<String> actorImdbIds = actorRepository.findAllByName(actor);
                    key.setIds(actorImdbIds);
                    keys.add(key);
                });
                break;
            case GENRE:
                List<String> genres = genreRepository.findAllDistinctGenres();
                genres.forEach(genre -> {
                    Key key = new Key();
                    key.setKey(genre);
                    key.setIds(genreRepository.findAllByGenre(genre));
                    keys.add(key);
                });
                break;
            case DIRECTOR:
                List<String> directors = videoRepository.findAllDistinctDirectors();
                directors.forEach(director -> {
                    Key key = new Key();
                    key.setKey(director);
                    key.setIds(videoRepository.findAllImdbIdsByDirector(director));
                    keys.add(key);
                });
                break;
            case RATING:
                List<String> ratings = videoRepository.findAllDistinctRatings();
                ratings.forEach(rating -> {
                    Key key = new Key();
                    key.setKey(rating);
                    key.setIds(videoRepository.findAllImdbIdsByRating(rating));
                    keys.add(key);
                });
                break;
            case TITLE:
                List<String> titles = videoRepository.findAllDistinctTitles();
                titles.forEach(title -> {
                    Key key = new Key();
                    key.setKey(title);
                    key.setIds(videoRepository.findAllImdbIdsByTitle(title));
                    keys.add(key);
                });
                break;
            case YEAR:
                List<Integer> years = videoRepository.findAllDistinctReleaseYears();
                years.forEach(year -> {
                    Key key = new Key();
                    key.setKey(String.valueOf(year));
                    key.setIds(videoRepository.findAllImdbIdsByReleaseYear(year));
                    keys.add(key);
                });
                break;
            default:
                break;
        }

        return keys;
    }

    /**
     *
     * @param index
     * @param query
     * @return
     */
    public List<String> searchByIndex(Index index, String query) {

        List<String> results = new ArrayList<>();

        switch (index) {
            case ACTOR:
                break;
            case DIRECTOR:
                break;
            case GENRE:
                break;
            case RATING:
                break;
            case TAG:
                break;
            case TITLE:
                break;
            case YEAR:
                break;
            default:
                break;
        }

        throw new NotYetImplementedException("Searching is not yet implemented.");
        // return results;
    }
}
