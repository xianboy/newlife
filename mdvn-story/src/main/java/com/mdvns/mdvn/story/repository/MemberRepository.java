package com.mdvns.mdvn.story.repository;

import com.mdvns.mdvn.story.domain.entity.StoryMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<StoryMember, Long> {

}
