package com.mdvns.mdvn.template.repository;

import com.mdvns.mdvn.template.domain.entity.TemplateRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRoleRepository extends JpaRepository<TemplateRole, Long> {

    //查询指定id集合的id和name
    @Query("select t.id, t.name from Template t where t.id in ?1")
    List<Object[]> findIdAndNameById(List<Long> ids);


}
