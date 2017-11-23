package com.mdvns.mdvn.department.repository;

import com.mdvns.mdvn.department.domain.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
