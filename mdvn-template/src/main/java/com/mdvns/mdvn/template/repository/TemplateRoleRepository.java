package com.mdvns.mdvn.template.repository;

import com.mdvns.mdvn.template.domain.entity.TemplateRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateRoleRepository extends JpaRepository<TemplateRole, Long> {
}
