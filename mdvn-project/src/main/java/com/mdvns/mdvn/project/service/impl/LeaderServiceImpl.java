package com.mdvns.mdvn.project.service.impl;

import com.mdvns.mdvn.common.bean.model.AddOrRemoveById;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.project.domain.entity.ProjectStaff;
import com.mdvns.mdvn.project.repository.LeaderRepository;
import com.mdvns.mdvn.project.service.LeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class LeaderServiceImpl implements LeaderService {
    private static final Logger LOG = LoggerFactory.getLogger(TagServiceImpl.class);
    @Autowired
    private LeaderRepository projectStaffRepository;

    /**
     * 新建ProjectStaff
     *
     * @param projId
     * @param leaders
     * @param creatorId
     * @return
     */
    @Override
    public List<ProjectStaff> createProjectStaff(Long creatorId, Long projId, List<Long> leaders) {
        LOG.info("id为：[{}]的staff，准备创建id为：[{}] 的项目的Staff id为：[{}]的映射信息.", creatorId, projId, leaders.toString());
        List<ProjectStaff> projectStaffList = new ArrayList<>();
        //遍历leaders构建ProjectStaff
        for (Long staffId : leaders) {
            ProjectStaff projectStaff = new ProjectStaff();
            projectStaff.setCreatorId(creatorId);
            projectStaff.setStaffId(staffId);
            projectStaff.setProjId(projId);
            projectStaff.setCreateTime(new Timestamp(System.currentTimeMillis()));
            projectStaff.setIsDeleted(MdvnConstant.ZERO);
            projectStaff = this.projectStaffRepository.save(projectStaff);
            projectStaffList.add(projectStaff);
        }
        LOG.info("保存映射信息完成");
        return projectStaffList;
    }


    /**
     * 更新项目标签映射
     * @param staffId 当前用户id
     * @param projId 当前项目id
     * @param leaders 需要更新的标签
     */
    @Override
    public void updateProjectLeader(Long staffId, Long projId, AddOrRemoveById leaders) {
        //删除标签映射
        if (null != leaders.getRemoveList()) {
            updateIsDeleted(staffId, projId, leaders.getRemoveList(), MdvnConstant.ONE);
        }
        //添加新增标签映射
        if (null != leaders.getAddList()) {
            List<Long> addLeaders = new ArrayList<>();
            List<Long> updateLeaders = new ArrayList<>();
            for (Long id : leaders.getAddList()) {
                //如果标签映射不存在添加id到addTags,已存在则添加id到removeTags,
                ProjectStaff ps = this.projectStaffRepository.findByProjIdAndStaffId(projId, id);
                if (null == ps) {
                    addLeaders.add(id);
                } else {
                    updateLeaders.add(id);
                }
            }
            //更新已存在映射的isDeleted为0
            updateIsDeleted(staffId, projId, updateLeaders, MdvnConstant.ZERO);
            //添加新映射
            createProjectStaff(staffId, projId, addLeaders);
        }
    }

    /**
     * 移除标签映射
     * @param staffId 当前用户id
     * @param projId 当前项目id
     * @param leaders 需要移除的负责人id
     * @return number
     */
    @Modifying
    public Integer updateIsDeleted(Long staffId, Long projId, List<Long> leaders, Integer isDeleted) {
        LOG.info("id为：[{}]的staff，准备去掉id为：[{}] 的项目的id为：[{}]的标签映射信息.", staffId, projId, leaders.toString());
        Integer number = this.projectStaffRepository.updateIsDeleted(isDeleted, projId, leaders);
        return number;
    }
}
