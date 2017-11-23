package com.mdvns.mdvn.department.uitil;

import com.mdvns.mdvn.common.bean.DepartmentDetail;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.department.domain.entity.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.Arrays;

public class DepartmentUtil {

    private static final Logger LOG = LoggerFactory.getLogger(DepartmentUtil.class);
    /**
     * 构建部门详情
     * @param dept
     * @return
     */
    public static DepartmentDetail buildDetailByDepartment(Department dept) throws BusinessException {
        //如果dept为null抛异常
        if (null==dept) {
            LOG.error("数据不存在");
            throw new BusinessException(ErrorEnum.DEPT_NOT_EXIST);
        }
        DepartmentDetail departmentDetail = new DepartmentDetail();
        //id
        departmentDetail.setId(dept.getId());
        //名称
        departmentDetail.setName(dept.getName());
        //编号
        if (!StringUtils.isEmpty(dept.getSerialNum())) {
            departmentDetail.setSerialNum(dept.getSerialNum());
        }
        //创建时间
        if (!StringUtils.isEmpty(dept.getCreateTime())) {
            departmentDetail.setCreateTime(dept.getCreateTime().getTime());
        }
        //职位
        if (!StringUtils.isEmpty(dept.getPositions())) {
            String[] names = StringUtils.split(dept.getPositions(), ",");
            departmentDetail.setPositions(Arrays.asList(names));
        }
        return departmentDetail;
    }
}
