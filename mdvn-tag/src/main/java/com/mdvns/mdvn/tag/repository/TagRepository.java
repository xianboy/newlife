package com.mdvns.mdvn.tag.repository;

import com.mdvns.mdvn.tag.domain.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    //查询指定名称的tag
    Tag findByName(String name);

    List<Tag> findByNameContains(String name);

    Page<Tag> findByNameContains(String name, Pageable pageable);

    //查询id的最大值
    @Query(value = "select max(id) from tag", nativeQuery = true)
    Long getMaxId();
}
