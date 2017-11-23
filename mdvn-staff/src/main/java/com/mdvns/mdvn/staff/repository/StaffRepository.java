package com.mdvns.mdvn.staff.repository;

import com.mdvns.mdvn.staff.domain.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    //根据account查询staff
    Staff findByAccount(String account);
    //查询id的最大值
    @Query(value = "select max(id) from project", nativeQuery = true)
    Long getMaxId();
/*
    Page<Staff> findAll(Pageable pageable);

    Staff findByStaffId(String staffId);

    Page<Staff> findByNameStartingWith(String pswd, Pageable pageable);

    @Query(value="SELECT DISTINCT COUNT(*) FROM staff ", nativeQuery = true)
    Long getStaffCount();

    Page<Staff> findByNameLike(String name, Pageable pageable);


    Staff findByAccountAndPassword(String account, String password);

    Staff findFirstByName(String name);
    Staff findFirstByAccount(String account);

    List<Staff> findAllByAccountIsNot(String account);

    Page<Staff> findAllByAccountIsNot(String account, Pageable pageable);

//    @Query(value = "select * from staff where name like %?1", nativeQuery = true)
    List<Staff> findByNameLike(String name);


    List<Staff> findByNameStartingWith(String startingStr);
*/


}
