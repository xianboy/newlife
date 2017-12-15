package com.mdvns.mdvn.story.repository;

import com.mdvns.mdvn.story.domain.entity.StoryTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<StoryTag, Long> {

}
