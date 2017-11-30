package com.mdvns.mdvn.project.util;

import com.mdvns.mdvn.common.bean.StaffDetail;
import com.mdvns.mdvn.project.repository.ProjectStaffRepository;

import java.util.List;

public class ProjectUtil {



    public static List<StaffDetail> buildProjectLeaders(Long projId, ProjectStaffRepository projectStaffRepository) {
        List<Long> leaders = projectStaffRepository.findStaffIdByProjId(projId);

        return null;
    }
}
