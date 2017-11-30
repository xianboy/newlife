package com.mdvns.mdvn.project.repository;

import com.mdvns.mdvn.project.domain.entity.ProjectStaff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectStaffRepository extends JpaRepository<ProjectStaff, Long> {
    //根据projId查询
    List<ProjectStaff> findByProjId(Long projId);
    //根据projId查询对应的staffId
    @Query("select ps.staffId from ProjectStaff ps where ps.projId = ?1 and ps.isDeleted = 0")
    List<Long> findStaffIdByProjId(Long projId);
}
