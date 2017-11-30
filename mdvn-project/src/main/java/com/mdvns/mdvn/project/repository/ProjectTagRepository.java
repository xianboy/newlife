package com.mdvns.mdvn.project.repository;

import com.mdvns.mdvn.project.domain.entity.ProjectTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectTagRepository extends JpaRepository<ProjectTag, Long> {
}
