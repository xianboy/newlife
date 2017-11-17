package com.mdvns.mdvn.project.repository;

import com.mdvns.mdvn.project.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long>{
    @Query(value = "select max(id) from project", nativeQuery = true)
    Long getMaxId();

    @Modifying
    @Query("update Project p set p.status = ?1 where p.id = ?2")
    Integer updaetStatus(Integer status, Integer id);
}
