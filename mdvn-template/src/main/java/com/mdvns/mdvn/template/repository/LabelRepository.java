package com.mdvns.mdvn.template.repository;

import com.mdvns.mdvn.template.domain.entity.FunctionLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<FunctionLabel, Long> {
    //根据name查询
    FunctionLabel findByName(String name);
}
