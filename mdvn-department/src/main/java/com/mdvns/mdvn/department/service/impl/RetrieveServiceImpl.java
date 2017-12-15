package com.mdvns.mdvn.department.service.impl;

import com.mdvns.mdvn.common.bean.PageableCriteria;
import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.PageableQueryUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.department.domain.entity.Department;
import com.mdvns.mdvn.department.repository.DeptRepository;
import com.mdvns.mdvn.department.repository.PositionRepository;
import com.mdvns.mdvn.department.service.RetrieveService;
import com.mdvns.mdvn.department.uitil.DepartmentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetrieveServiceImpl implements RetrieveService {

    private static final Logger LOG = LoggerFactory.getLogger(RetrieveServiceImpl.class);

    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private PositionRepository positionRepository;


    /**
     * 查询所有数据：支持分页
     * @return
     */
    @Override
    @Transactional
    public RestResponse<?> retrieveAll(PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest) {
        //获取分页参数对象
        PageableCriteria pageableCriteria = pageableQueryWithoutArgRequest.getPageableCriteria();
        //构建PageRequest
        PageRequest pageRequest;
        if (null == pageableCriteria) {
            LOG.info("用户[{}]没有填写分页参数，故使用默认分页.", pageableQueryWithoutArgRequest.getStaffId());
            pageRequest = PageableQueryUtil.defaultPageReqestBuilder();
        } else {
            pageRequest = PageableQueryUtil.pageRequestBuilder(pageableCriteria);
        }
        //分页查询
        Page<Department> deptPage = this.deptRepository.findAll(pageRequest);
        //返回结果
        return RestResponseUtil.success(deptPage);
    }
    /**
     * 获取指定name的部门详情
     * @param retrieveDetailRequest
     * @return
     */
    @Override
    @Transactional
    public RestResponse<?> retrieveDetailByName(SingleCriterionRequest retrieveDetailRequest) throws BusinessException {
        //获取request中的name
        String name = retrieveDetailRequest.getCriterion();
        //根据name查询部门
        Department dept = this.deptRepository.findByName(name);
        //如果查询数据不存在，抛异常
        MdvnCommonUtil.notExists(dept,"name", name);
        //返回结果
        return RestResponseUtil.success(DepartmentUtil.buildDetailByDepartment(dept, this.positionRepository));
    }

    /**
     * 获取指定id的部门详情
     * @param retrieveDetailRequest
     * @return
     */
    @Override
    @Transactional
    public RestResponse<?> retrieveDetailById(SingleCriterionRequest retrieveDetailRequest) throws BusinessException {
        //获取request中的id
        Long id = Long.valueOf(retrieveDetailRequest.getCriterion());
        //根据id查询
        Department dept = this.deptRepository.findOne(id);
        //数据不存在，抛异常
        MdvnCommonUtil.notExists(dept,"id", retrieveDetailRequest.getCriterion());
        //返回结果
        return RestResponseUtil.success(DepartmentUtil.buildDetailByDepartment(dept, this.positionRepository));
    }
}
