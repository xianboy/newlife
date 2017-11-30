package com.mdvns.mdvn.template.repository;

import com.mdvns.mdvn.template.domain.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    //查询id的最大值
    @Query(value = "select max(id) from tag", nativeQuery = true)
    Long getMaxId();

    //查询指定name的template
    Template findByName(String name);
}
