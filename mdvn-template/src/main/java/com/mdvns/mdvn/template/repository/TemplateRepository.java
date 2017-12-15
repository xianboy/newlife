package com.mdvns.mdvn.template.repository;

import com.mdvns.mdvn.template.domain.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    //查询id的最大值
    @Query(value = "select max(id) from tag", nativeQuery = true)
    Long getMaxId();

    //查询指定name的template
    Template findByName(String name);

    //根据industryId查询
    @Query("select t.id, t.name from Template t where industryId = ?1")
    List<Object[]> findByIndustryId(Long industryId);

//    List<Object[]> findIdAndNameByIndustryId(Long industryId);

    @Query("select t.id, t.name from Template t where t.id in ?1")
    List<Object[]> findIdAndNameById(List<Long> ids);

}
