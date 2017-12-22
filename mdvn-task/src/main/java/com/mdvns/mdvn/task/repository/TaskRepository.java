package com.mdvns.mdvn.task.repository;

import com.mdvns.mdvn.task.domain.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {
    /*query*/
    @Query(value = "select max(id) from Task", nativeQuery = true)
    Long getMaxId();
}
