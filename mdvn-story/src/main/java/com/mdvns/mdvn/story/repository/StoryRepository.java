package com.mdvns.mdvn.story.repository;

import com.mdvns.mdvn.story.domain.entity.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoryRepository extends JpaRepository<Story, Long> {
    /*query*/
    @Query(value = "select max(id) from story", nativeQuery = true)
    Long getMaxId();
}
