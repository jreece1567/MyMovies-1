package com.sandy.mymovies.repositories;

import com.sandy.mymovies.models.domain.Tags;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<Tags,String> {

    @Query("SELECT imdbId FROM Tags WHERE tag = ?1")
    List<String> findAllByTag(String tag);

    @Query("SELECT DISTINCT tag FROM Tags")
    List<String> findAllDistinctTags();

}
