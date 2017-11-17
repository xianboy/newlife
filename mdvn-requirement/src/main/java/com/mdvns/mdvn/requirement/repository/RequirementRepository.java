package com.mdvns.mdvn.requirement.repository;

import com.mdvns.mdvn.requirement.domain.Requirement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequirementRepository extends JpaRepository<Requirement, Long>{

    /*查询projId为指定projId的requirement总数*/
    Long countRequirementByProjId(Long projId);

    Page<Requirement> findByProjId(Long id, Pageable pageable);
}
