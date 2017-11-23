package com.mdvns.mdvn.staff.domain.entity;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Component
public class StaffAuthInfo {

    @Id
    @GeneratedValue
    private Integer id;

    /*项目Id*/
    private String projId;

    /*员工Id*/
    private String staffId;

    /*权限编号*/
    private Integer authCode;

    /*项目模块Id*/
    private String hierarchyId;

    /*权限添加人Id*/
    private String assignerId;

    /*添加时间*/
    private Timestamp createTime;

    /*是否权限已取消*/

    private Integer isDeleted;

    public String getHierarchyId() {
        return hierarchyId;
    }

    public void setHierarchyId(String hierarchyId) {
        this.hierarchyId = hierarchyId;
    }

    public StaffAuthInfo() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjId() {
        return projId;
    }

    public void setProjId(String projId) {
        this.projId = projId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public Integer getAuthCode() {
        return authCode;
    }

    public void setAuthCode(Integer authCode) {
        this.authCode = authCode;
    }

    public String getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(String assignerId) {
        this.assignerId = assignerId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StaffAuthInfo)) return false;

        StaffAuthInfo that = (StaffAuthInfo) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getProjId() != null ? !getProjId().equals(that.getProjId()) : that.getProjId() != null) return false;
        if (getStaffId() != null ? !getStaffId().equals(that.getStaffId()) : that.getStaffId() != null) return false;
        if (getAuthCode() != null ? !getAuthCode().equals(that.getAuthCode()) : that.getAuthCode() != null)
            return false;
        if (getHierarchyId() != null ? !getHierarchyId().equals(that.getHierarchyId()) : that.getHierarchyId() != null)
            return false;
        if (getAssignerId() != null ? !getAssignerId().equals(that.getAssignerId()) : that.getAssignerId() != null)
            return false;
        if (getCreateTime() != null ? !getCreateTime().equals(that.getCreateTime()) : that.getCreateTime() != null)
            return false;
        return getIsDeleted() != null ? getIsDeleted().equals(that.getIsDeleted()) : that.getIsDeleted() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getProjId() != null ? getProjId().hashCode() : 0);
        result = 31 * result + (getStaffId() != null ? getStaffId().hashCode() : 0);
        result = 31 * result + (getAuthCode() != null ? getAuthCode().hashCode() : 0);
        result = 31 * result + (getHierarchyId() != null ? getHierarchyId().hashCode() : 0);
        result = 31 * result + (getAssignerId() != null ? getAssignerId().hashCode() : 0);
        result = 31 * result + (getCreateTime() != null ? getCreateTime().hashCode() : 0);
        result = 31 * result + (getIsDeleted() != null ? getIsDeleted().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StaffAuthInfo{" +
                "id=" + id +
                ", projId='" + projId + '\'' +
                ", staffId='" + staffId + '\'' +
                ", authCode=" + authCode +
                ", hierarchyId='" + hierarchyId + '\'' +
                ", assignerId='" + assignerId + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
