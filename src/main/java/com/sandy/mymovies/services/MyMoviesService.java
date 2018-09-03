package com.sandy.mymovies.services;

import com.sandy.mymovies.models.domain.Actor;
import com.sandy.mymovies.models.domain.Chapter;
import com.sandy.mymovies.models.domain.Genre;
import com.sandy.mymovies.models.domain.Tag;
import com.sandy.mymovies.models.domain.Video;
import com.sandy.mymovies.models.dto.Cast;
import com.sandy.mymovies.models.dto.Count;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Provides 'business logic' for the application. Mediates between the 'transport' layer and the
 * 'persistence' layer.
 */
@Service
public class MyMoviesService {

  private final transient VideoRepository videoRepository;
  private final transient ChapterRepository chapterRepository;
  private final transient ActorRepository actorRepository;
  private final transient TagRepository tagRepository;
  private final transient GenreRepository genreRepository;

  /**
   * Construct a new service instance.
   *
   * @param videoRepository JPA interface to the Video table.
   * @param chapterRepository JPA interface to the Chapter table.
   * @param actorRepository JPA interface to the Actor table.
   * @param tagRepository JPA interface to the Tag table.
   * @param genreRepository JPA interface to the Genre table.
   */
  @Autowired
  public MyMoviesService(final VideoRepository videoRepository,
      final ChapterRepository chapterRepository, final ActorRepository actorRepository,
      final TagRepository tagRepository, final GenreRepository genreRepository) {

    this.videoRepository = videoRepository;
    this.chapterRepository = chapterRepository;
    this.actorRepository = actorRepository;
    this.tagRepository = tagRepository;
    this.genreRepository = genreRepository;
  }

  /**
   * Create a movie, if the supplied imdbId does not already exist.
   *
   * @param movie the movie to be created.
   * @return the created movie, or the existing movie if it already exists.
   */
  public Movie createMovie(final Movie movie) {

    final Optional<Video> video = videoRepository.findById(movie.getImdbId());
    if (!video.isPresent()) {
      // create a new movie in the DB

      // save the Cast
      movie.getCast()
          .forEach(actorName -> actorRepository.save(new Actor(actorName, movie.getImdbId())));

      // save the Tags
      movie.getTags().forEach(tag -> tagRepository.save(new Tag(tag, movie.getImdbId())));

      // save the Genres
      movie.getGenres().forEach(genre -> genreRepository.save(new Genre(genre, movie.getImdbId())));

      // save the Video
      videoRepository
          .save(new Video(movie.getImdbId(), movie.getTitle(), movie.getReleaseYear(),
              movie.getDuration(), movie.getRating(), movie.getDirector(),
              movie.getImageUrl(),
              movie.getDescription()));

    }

    // return the movie from the DB
    return readMovie(movie.getImdbId());
  }

  /**
   * Delete a movie, if the supplied imdbId exists.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @throws NoSuchElementException if the supplied imdbId does not exist.
   */
  public void deleteMovie(final String imdbId) {

    final Optional<Video> video = videoRepository.findById(imdbId);
    if (video.isPresent()) {
      // delete a movie from the DB

      actorRepository.deleteActorsByImdbId(imdbId);
      tagRepository.deleteTagsByImdbId(imdbId);
      genreRepository.deleteGenresByImdbId(imdbId);
      chapterRepository.deleteChaptersByImdbId(imdbId);
      videoRepository.deleteVideosByImdbId(imdbId);

    } else {

      throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
    }
  }

  /**
   * Create an episode, if one for the imdbId/season/episodeNumber does not already exist.
   *
   * @param episode the Episode to be created.
   * @return the created episode, or the existing episode if it already exists.
   */
  public Episode createEpisode(final Episode episode) {

    final Optional<Chapter> chapter =
        chapterRepository
            .findByImdbIdAndSeasonAndEpisodeNumber(episode.getImdbId(),
                Integer.parseInt(episode.getSeason()),
                Integer.parseInt(episode.getEpisodeNumber()));
    if (chapter.isPresent()) {
      return new Episode(chapter.get().getImdbId(), String.valueOf(chapter.get().getSeason()),
          String.valueOf(chapter.get().getEpisodeNumber()), chapter.get().getTitle(),
          chapter.get().getDescription());
    }

    final Chapter newChapter =
        chapterRepository.save(
            new Chapter(episode.getImdbId(), Integer.parseInt(episode.getSeason()),
                Integer.parseInt(episode.getEpisodeNumber()),
                episode.getTitle(), episode.getDescription()));

    return new Episode(newChapter.getImdbId(), String.valueOf(newChapter.getSeason()),
        String.valueOf(newChapter.getEpisodeNumber()), newChapter.getTitle(),
        newChapter.getDescription());
  }

  /**
   * Return a Movie given an imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the found movie.
   * @throws NoSuchElementException if the supplied imdbId does not exist.
   */
  public Movie readMovie(final String imdbId) {

    // validate the imdbId, fetch the existing Video data
    final Optional<Video> video = videoRepository.findById(imdbId);
    if (!video.isPresent()) {
      throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
    }

    // construct a Movie for the response
    final Movie movie = new Movie();

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
    final Genres genres = new Genres();
    genres.addAll(genreRepository.findAllByImdbId(movie.getImdbId()));
    movie.setGenres(genres);

    // populate the Movie tags
    final Tags tags = new Tags();
    tags.addAll(tagRepository.findAllByImdbId(movie.getImdbId()));
    movie.setTags(tags);

    // populate the Movie cast
    final Cast cast = new Cast();
    cast.addAll(actorRepository.findAllByImdbId(movie.getImdbId()));
    movie.setCast(cast);

    return movie;
  }

  /**
   * Return a Title given an imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the found title.
   * @throws NoSuchElementException if the supplied imdbId does not exist.
   */
  public Title readTitle(final String imdbId) {

    final Optional<Video> video = videoRepository.findById(imdbId);
    if (!video.isPresent()) {
      throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
    }

    return new Title(video.get().getImdbId(), video.get().getTitle(),
        video.get().getReleaseYear(),
        video.get().getImageUrl());
  }

  /**
   * Return the Cast (list of actor names) given an imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the found cast.
   * @throws NoSuchElementException if the supplied imdbId has no associated actors.
   */
  public Cast readCast(final String imdbId) {

    final Optional<Video> video = videoRepository.findById(imdbId);
    if (!video.isPresent()) {
      throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
    }

    final List<String> actors = actorRepository.findAllByImdbId(imdbId);
    if (actors == null || actors.isEmpty()) {
      throw new NoSuchElementException(String.format("Cast for movie %s not found.", imdbId));
    }

    final Cast cast = new Cast();
    cast.addAll(actors);

    return cast;
  }

  /**
   * Return all Episodes given an imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the list of Episodes found.
   */
  public List<Episode> readEpisodes(final String imdbId) {

    final Optional<Video> video = videoRepository.findById(imdbId);
    if (!video.isPresent()) {
      throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
    }

    final List<Chapter> chapters = chapterRepository.findAllByImdbId(imdbId);
    final List<Episode> episodes = new ArrayList<>();
    chapters.forEach(c -> episodes.add(new Episode(c.getImdbId(), String.valueOf(c.getSeason()),
        String.valueOf(c.getEpisodeNumber()), c.getTitle(), c.getDescription())));

    Collections.sort(episodes, new Comparator<Episode>() {
      @Override
      public int compare(final Episode o1, final Episode o2) {
        final int compareSeasons = o1.getSeason().compareTo(o2.getSeason());
        if (compareSeasons != 0) {
          return compareSeasons;
        }
        return o1.getEpisodeNumber().compareTo(o2.getEpisodeNumber());
      }
    });

    return episodes;
  }

  /**
   * Return all Episodes given an imdbId and season number.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @param seasonNumber the season number.
   * @return the list of Episodes found.
   */
  public List<Episode> readEpisodes(final String imdbId, final Integer seasonNumber) {

    final Optional<Video> video = videoRepository.findById(imdbId);
    if (!video.isPresent()) {
      throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
    }

    final List<Chapter> chapters = chapterRepository
        .findAllByImdbIdAndSeason(imdbId, seasonNumber);
    if (chapters.isEmpty()) {
      throw new NoSuchElementException(
          String.format("No seasons found for movie with id %s.", imdbId));
    }

    final List<Episode> episodes = new ArrayList<>();
    chapters.forEach(chapter -> episodes
        .add(new Episode(chapter.getImdbId(), String.valueOf(chapter.getSeason()),
            String.valueOf(chapter.getEpisodeNumber()), chapter.getTitle(),
            chapter.getDescription())));

    Collections.sort(episodes, new Comparator<Episode>() {
      @Override
      public int compare(final Episode o1, final Episode o2) {
        return o1.getEpisodeNumber().compareTo(o2.getEpisodeNumber());
      }
    });

    return episodes;
  }

  /**
   * Delete all Episodes given an imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   */
  public void deleteEpisodes(final String imdbId) {

    chapterRepository.deleteChaptersByImdbId(imdbId);
  }

  /**
   * Delete all Episodes given an imdbId and season number.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @param seasonNumber the season number.
   */
  public void deleteEpisodes(final String imdbId, final Integer seasonNumber) {

    chapterRepository.deleteChaptersByImdbIdAndSeason(imdbId, seasonNumber);
  }

  /**
   * Delete all Episodes given an imdbId and season number and episode number.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @param seasonNumber the season number.
   * @param episodeNumber the episode number.
   */
  public void deleteEpisodes(final String imdbId, final Integer seasonNumber,
      final Integer episodeNumber) {

    chapterRepository
        .deleteChaptersByImdbIdAndSeasonAndEpisodeNumber(imdbId, seasonNumber, episodeNumber);
  }

  /**
   * Return all seasons (season numbers) given an imdbId.
   *
   * @param imdbId the unique IMDB-id of the movie.
   * @return the list of season numbers.
   */
  public List<String> readSeasons(final String imdbId) {

    final Optional<Video> video = videoRepository.findById(imdbId);
    if (!video.isPresent()) {
      throw new NoSuchElementException(String.format("Movie with id %s not found.", imdbId));
    }

    final List<String> seasons = chapterRepository.findDistinctSeasonsByImdbId(imdbId);

    Collections.sort(seasons);

    return seasons;
  }

  /**
   * Return all distinct keys for a given Index.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @return the list of distinct key values (genres, ratings, tags, etc.).
   */
  public List<String> readIndex(final Index index) {

    final List<String> keys = new ArrayList<>();

    switch (index) {
      case ALL:
        Arrays.asList(Index.values()).forEach(idx -> keys.add(idx.getValue()));
        break;
      case ACTOR:
        keys.addAll(actorRepository.findAllDistinctActors());
        break;
      case DIRECTOR:
        keys.addAll(videoRepository.findAllDistinctDirectors());
        break;
      case GENRE:
        keys.addAll(genreRepository.findAllDistinctGenres());
        break;
      case RATING:
        keys.addAll(videoRepository.findAllDistinctRatings());
        break;
      case TAG:
        keys.addAll(tagRepository.findAllDistinctTags());
        break;
      case TITLE:
        keys.addAll(videoRepository.findAllDistinctTitles());
        break;
      case YEAR:
        videoRepository.findAllDistinctReleaseYears()
            .forEach(year -> keys.add(String.valueOf(year)));
        break;
      default:
        break;
    }

    Collections.sort(keys);

    return keys;
  }

  /**
   * Return all Titles under a given Index and key-value.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @param key the key value (a genre, a rating, a tag, etc.).
   * @return the list of Titles under the given Index and key-value.
   */
  public List<Title> readTitlesByIndexAndKey(final Index index, final String key) {

    final List<Video> videos = new ArrayList<>();

    switch (index) {
      case ACTOR:
        actorRepository.findAllByName(key)
            .forEach(imdbId -> videos.add(videoRepository.findById(imdbId).get()));
        break;
      case DIRECTOR:
        videoRepository.findAllByDirector(key).forEach(video -> videos.add(video));
        break;
      case GENRE:
        genreRepository.findAllByGenre(key)
            .forEach(imdbId -> videos.add(videoRepository.findById(imdbId).get()));
        break;
      case RATING:
        videoRepository.findAllByRating(key).forEach(video -> videos.add(video));
        break;
      case TAG:
        tagRepository.findAllByTag(key)
            .forEach(imdbId -> videos.add(videoRepository.findById(imdbId).get()));
        break;
      case TITLE:
        videoRepository.findAllByTitle(key).forEach(video -> videos.add(video));
        break;
      case YEAR:
        videoRepository.findAllByReleaseYear(Integer.valueOf(key))
            .forEach(video -> videos.add(video));
        break;
      default:
        break;
    }

    final List<Title> titles = new ArrayList<>();
    videos.forEach(video -> titles.add(new Title(video.getImdbId(), video.getTitle(),
        video.getReleaseYear(), video.getImageUrl())));

    Collections.sort(titles, new Comparator<Title>() {
      @Override
      public int compare(final Title o1, final Title o2) {
        return o1.getTitle().compareTo(o2.getTitle());
      }
    });

    return titles;
  }

  /**
   * Return all Key values for a given Index.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @return a list of the distinct values for the index, and the imdbIds associated with each.
   */
  public List<Key> readIdsByIndex(final Index index) {

    final List<Key> keys = new ArrayList<>();

    switch (index) {
      case ACTOR:
        final List<String> actors = actorRepository.findAllDistinctActors();
        actors.forEach(actor -> {
          final Key key = new Key();
          key.setKey(actor);
          final List<String> actorImdbIds = actorRepository.findAllByName(actor);
          key.setIds(actorImdbIds);
          keys.add(key);
        });
        break;
      case DIRECTOR:
        final List<String> directors = videoRepository.findAllDistinctDirectors();
        directors.forEach(director -> {
          final Key key = new Key();
          key.setKey(director);
          key.setIds(videoRepository.findAllImdbIdsByDirector(director));
          keys.add(key);
        });
        break;
      case GENRE:
        final List<String> genres = genreRepository.findAllDistinctGenres();
        genres.forEach(genre -> {
          final Key key = new Key();
          key.setKey(genre);
          key.setIds(genreRepository.findAllByGenre(genre));
          keys.add(key);
        });
        break;
      case RATING:
        final List<String> ratings = videoRepository.findAllDistinctRatings();
        ratings.forEach(rating -> {
          final Key key = new Key();
          key.setKey(rating);
          key.setIds(videoRepository.findAllImdbIdsByRating(rating));
          keys.add(key);
        });
        break;
      case TAG:
        final List<String> tags = tagRepository.findAllDistinctTags();
        tags.forEach(tag -> {
          final Key key = new Key();
          key.setKey(tag);
          key.setIds(tagRepository.findAllByTag(tag));
          keys.add(key);
        });
        break;
      case TITLE:
        final List<String> titles = videoRepository.findAllDistinctTitles();
        titles.forEach(title -> {
          final Key key = new Key();
          key.setKey(title);
          key.setIds(videoRepository.findAllImdbIdsByTitle(title));
          keys.add(key);
        });
        break;
      case YEAR:
        final List<Integer> years = videoRepository.findAllDistinctReleaseYears();
        years.forEach(year -> {
          final Key key = new Key();
          key.setKey(String.valueOf(year));
          key.setIds(videoRepository.findAllImdbIdsByReleaseYear(year));
          keys.add(key);
        });
        break;
      default:
        break;
    }

    Collections.sort(keys, new Comparator<Key>() {
      @Override
      public int compare(final Key o1, final Key o2) {
        return o1.getKey().compareTo(o2.getKey());
      }
    });

    return keys;
  }

  /**
   * Return a 'Key', which is a list of all imdbIds under a given Index and key-value.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @param key the key-value (a genre, a rating, a tag, etc.).
   * @return the Key, containing the key-value and list of associated imdbIds.
   */
  public Key readIdsByIndexAndKey(final Index index, final String key) {

    final Key keyResult = new Key();
    keyResult.setKey(key);

    final List<String> imdbIds = new ArrayList<>();

    switch (index) {
      case ACTOR:
        keyResult.setIds(actorRepository.findAllByName(key));
        break;
      case DIRECTOR:
        videoRepository.findAllByDirector(key).forEach(video -> imdbIds.add(video.getImdbId()));
        keyResult.setIds(imdbIds);
        break;
      case GENRE:
        keyResult.setIds(genreRepository.findAllByGenre(key));
        break;
      case RATING:
        videoRepository.findAllByRating(key).forEach(video -> imdbIds.add(video.getImdbId()));
        keyResult.setIds(imdbIds);
        break;
      case TAG:
        keyResult.setIds(tagRepository.findAllByTag(key));
        break;
      case TITLE:
        videoRepository.findAllByTitle(key).forEach(video -> imdbIds.add(video.getImdbId()));
        keyResult.setIds(imdbIds);
        break;
      case YEAR:
        videoRepository.findAllByReleaseYear(Integer.valueOf(key))
            .forEach(video -> imdbIds.add(video.getImdbId()));
        keyResult.setIds(imdbIds);
        break;
      default:
        break;
    }

    return keyResult;
  }

  /**
   * Search a given Index for a given query value. All matches (partial and complete) are valid.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @param query the query value (a partial/complete genre-name, tag-name, actor-name, etc.).
   * @return the list of matches for the given query on the given index.
   */
  public List<String> searchByIndex(final Index index, final String query) {

    final List<String> results = new ArrayList<>();

    switch (index) {
      case ACTOR:
        results.addAll(actorRepository.searchActors(query));
        break;
      case DIRECTOR:
        results.addAll(videoRepository.searchDirectors(query));
        break;
      case GENRE:
        results.addAll(genreRepository.searchGenres(query));
        break;
      case RATING:
        results.addAll(videoRepository.searchRatings(query));
        break;
      case TAG:
        results.addAll(tagRepository.searchTags(query));
        break;
      case TITLE:
        results.addAll(videoRepository.searchTitles(query));
        break;
      case YEAR:
        videoRepository.searchReleaseYears(Integer.valueOf(query))
            .forEach(year -> results.add(String.valueOf(year)));
        break;
      default:
        break;
    }

    Collections.sort(results);

    return results;
  }

  /**
   * Return the count of a value under a given Index.
   *
   * @param index the index (actor,director,genre,rating,tag,title,year,etc.).
   * @param value the value to be counted.
   * @return a Count instance containing the value, and the count of the value in the index.
   */
  public Count countByIndexAndKey(final Index index, final String value) {

    Integer count = 0;

    switch (index) {
      case ACTOR:
        count = actorRepository.countAllByName(value);
        break;
      case DIRECTOR:
        count = videoRepository.countAllByDirector(value);
        break;
      case GENRE:
        count = genreRepository.countAllByGenre(value);
        break;
      case RATING:
        count = videoRepository.countAllByRating(value);
        break;
      case TAG:
        count = tagRepository.countAllByTag(value);
        break;
      case TITLE:
        count = videoRepository.countAllByTitle(value);
        break;
      case YEAR:
        count = videoRepository.countAllByReleaseYear(Integer.valueOf(value));
        break;
      default:
        break;
    }

    return new Count(value, count);
  }

}
