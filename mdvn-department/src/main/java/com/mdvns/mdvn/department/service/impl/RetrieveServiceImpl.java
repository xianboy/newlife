package com.mdvns.mdvn.department.service.impl;

import com.mdvns.mdvn.common.bean.PageableCriteria;
import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.RetrieveDetailRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.PageableQueryUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.department.domain.entity.Department;
import com.mdvns.mdvn.department.repository.DepartmentRepository;
import com.mdvns.mdvn.department.service.RetrieveService;
import com.mdvns.mdvn.department.uitil.DepartmentUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetrieveServiceImpl implements RetrieveService {

    private static final Logger LOG = LoggerFactory.getLogger(RetrieveServiceImpl.class);

    @Autowired
    private DepartmentRepository deptRepository;


    /**
     * 查询所有数据：支持分页
     *
     * @return
     */
    @Override
    @Transactional
    public RestResponse<?> findAll(PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest) {
        //获取分页参数对象
        PageableCriteria pageableCriteria = pageableQueryWithoutArgRequest.getPageableCriteria();
        if (null == pageableCriteria) {
            pageableCriteria = new PageableCriteria();
        }
        //分页查询
        Page<Department> deptPage = this.deptRepository.findAll(PageableQueryUtil.pageRequestBuilder(pageableCriteria));
        //返回结果
        return RestResponseUtil.success2(deptPage);
    }

    /**
     * 获取指定id的部门详情
     *
     * @param retrieveDetailRequest
     * @return
     */
    @Override
    @Transactional
    public RestResponse<?> retrieveDetail(RetrieveDetailRequest retrieveDetailRequest) throws BusinessException {
        //根据id查询
        Department dept = this.deptRepository.findOne(retrieveDetailRequest.getId());
        //返回结果
        return RestResponseUtil.success2(DepartmentUtil.buildDetailByDepartment(dept));
    }
}
