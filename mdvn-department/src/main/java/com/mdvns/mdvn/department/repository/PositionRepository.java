package com.mdvns.mdvn.department.repository;

import com.mdvns.mdvn.department.domain.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long>{
    //根据名称查询position
    Position findByName(String positionName);
}
