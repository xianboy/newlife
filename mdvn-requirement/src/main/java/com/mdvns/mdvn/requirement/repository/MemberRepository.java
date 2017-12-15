package com.mdvns.mdvn.requirement.repository;

import com.mdvns.mdvn.requirement.domain.entity.RequirementMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberRepository extends JpaRepository<RequirementMember, Long> {
    //根据requirementId 查询 roleId
    List<Long> findRoleIdDistinctByRequirementId(Long requirementId);

    //查询指定requirement中指定角色的成员
    List<Long> findMemberIdByRoleIdAndRequirementId(Long requirementId, Long roleId);

    //修改成员映射
    @Query("UPDATE RequirementMember rm set rm.isDeleted = ?4 where rm.requirementId=?1 and rm.roleId = ?2 and rm.memberId in ?3")
    void updateIsDeleted(Long requirementId, Long roleId, List<Long> removeList, Integer isDeleted);

    //根据requirementId、roleId、memberId查数据
    RequirementMember findByRequirementIdAndRoleIdAndMemberId(Long requirementId, Long roleId, Long memberId);
}
