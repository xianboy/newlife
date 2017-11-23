package com.mdvns.mdvn.department.service.impl;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.department.domain.CreateDeptRequest;
import com.mdvns.mdvn.department.domain.entity.Department;
import com.mdvns.mdvn.department.repository.DeptRepository;
import com.mdvns.mdvn.department.service.CreateService;
import com.mdvns.mdvn.department.uitil.DepartmentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CreateServiceImpl implements CreateService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateServiceImpl.class);

    @Autowired
    private DeptRepository deptRepository;


    /**
     * 新建部门
     *
     * @param createRequest
     * @return
     */
    @Override
    public RestResponse<?> create(CreateDeptRequest createRequest) throws BusinessException {
        if (nameExists(createRequest.getName())) {
            LOG.error("名称为：[ {} ] 的部门已存在", createRequest.getName());
            throw new BusinessException(ErrorEnum.EXISTED, "名称为：[ "+createRequest.getName()+" ] 的部门已存在");
        }
        //构建department对象并保存
        Department dept = this.deptRepository.save(buildDepartmentByRequest(createRequest));
        //构建response
        return RestResponseUtil.success2(DepartmentUtil.buildDetailByDepartment(dept));
    }

    /**
     * 指定名称的部门是否已存在
     *
     * @param name
     * @return
     */
    private Boolean nameExists(String name) {
        Department dept = this.deptRepository.findByName(name);
        if (null == dept) {
            return false;
        }
        return true;
    }

    /**
     * 保存position信息
     * @param positionNames
     *
     *
    private void savePosition(List<String> positionNames) {
    for (String positionName:positionNames) {
    Position p = this.positionRepository.findByName(positionName);
    Position position = new Position(positionName);
    positions.add(position);
    }
    positions = this.positionRepository.save(positions);

    }*/


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
        //职位名称
        List<String> positionNames = createRequest.getPositions();
        if (!(null == positionNames || positionNames.isEmpty())) {
            /*StringBuilder strBuilder = new StringBuilder();
            Iterator<String> its = positionNames.iterator();
            */
            dept.setPositions(createRequest.getPositions().toString());
        }
        return dept;
    }

    /**
     * 构建部门编号
     *
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
