package com.mdvns.mdvn.department.service.impl;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.department.domain.CreateDeptRequest;
import com.mdvns.mdvn.department.domain.DepartmentDetail;
import com.mdvns.mdvn.department.domain.entity.Department;
import com.mdvns.mdvn.department.domain.entity.Position;
import com.mdvns.mdvn.department.repository.DeptRepository;
import com.mdvns.mdvn.department.repository.PositionRepository;
import com.mdvns.mdvn.department.service.CreateService;
import com.mdvns.mdvn.department.uitil.DepartmentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class CreateServiceImpl implements CreateService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateServiceImpl.class);

    /*注入部门repository*/
    @Autowired
    private DeptRepository deptRepository;
    /*注入职位repository*/
    @Autowired
    private PositionRepository positionRepository;

    /**
     * 新建部门
     *
     * @param createRequest
     * @return
     */
    @Override
    public RestResponse<?> create(CreateDeptRequest createRequest) throws BusinessException {
        String name = createRequest.getName();
        Department dept;
        //根据name查询
        dept = this.deptRepository.findByName(name);
        //如果给定name的数据已存在,抛出异常
        MdvnCommonUtil.exists(dept, "name", name);
        //saveAndBuildDetail(createRequest);
        //根据request构建department
        dept = buildDepartmentByRequest(createRequest);
        //构建部门职位
        if (!(createRequest.getPositions().isEmpty())) {
            StringBuilder stringBuilder = buildDeptPosition(createRequest.getCreatorId(), createRequest.getPositions());
            dept.setPositions(stringBuilder.toString());
        }
        //保存dept
        dept = this.deptRepository.saveAndFlush(dept);
        //构建response
        return RestResponseUtil.success(DepartmentUtil.buildDetailByDepartment(dept, this.positionRepository));
    }

    /**
     * 构建部门职位id字符串
     * @param pNames
     * @return
     */
    private StringBuilder buildDeptPosition(Long creatorId, List<String> pNames) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String pName : pNames) {
            Position position = this.positionRepository.findByName(pName);
            //如果Position不存在，保存
            if (null == position) {
                position = new Position();
                //设置creatorId
                position.setCreatorId(creatorId);
                //设置name
                position.setName(pName);
                //设置创建时间
                position.setCreateTime(new Timestamp(System.currentTimeMillis()));
                //是否已删除
                position.setIsDeleted(MdvnConstant.ZERO);
                //新建position
                position = this.positionRepository.saveAndFlush(position);
                stringBuilder.append(position.getId()).append(",");
            }
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - MdvnConstant.ONE);
        return stringBuilder;
    }

    /**
     * 根据createRequest构建department对象
     *
     * @param createRequest
     * @return
     */
    private Department buildDepartmentByRequest(CreateDeptRequest createRequest) {
        Department dept = new Department();
        //构建部门编号
        dept.setSerialNum(buildSerialNum4Dept());
        //名称
        dept.setName(createRequest.getName());
        //创建人
        dept.setCreatorId(createRequest.getCreatorId());
        //创建时间
        dept.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //是否已删除
        dept.setIsDeleted(MdvnConstant.ZERO);

        return dept;
    }

    /**
     * 构建部门编号
     * @return
     */
    private String buildSerialNum4Dept() {
        //查询表中的最大id  maxId
        Long maxId = this.deptRepository.getMaxId();
        //如果表中没有数据，则给maxId赋值为0
        if (maxId == null) {
            maxId = 0L;
        }
        maxId += MdvnConstant.ONE;
        return MdvnConstant.CONSTANT_D + maxId;
    }

}
