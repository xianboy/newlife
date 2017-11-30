package com.mdvns.mdvn.staff.service.impl;

import com.mdvns.mdvn.common.bean.PageableCriteria;
import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.PageableQueryUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.staff.domain.entity.Staff;
import com.mdvns.mdvn.staff.repository.StaffRepository;
import com.mdvns.mdvn.staff.service.RetrieveService;
import com.mdvns.mdvn.staff.util.StaffUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RetrieveServiceImpl implements RetrieveService {

    private static final Logger LOG = LoggerFactory.getLogger(RetrieveServiceImpl.class);

    @Autowired
    private StaffRepository staffRepository;

    /**
     * 获取指定id的staff详情
     * @param singleCriterionRequest
     * @return
     */
    @Override
    public RestResponse<?> retrieveDetailById(SingleCriterionRequest singleCriterionRequest) throws BusinessException {
        //根据request获取id
        Long id = Long.valueOf(singleCriterionRequest.getCriterion());
        //根据id查询
        Staff staff = this.staffRepository.findOne(id);
        //数据不存在，抛异常
        MdvnCommonUtil.notExists(staff, "id", singleCriterionRequest.getCriterion());
        //返回结果
        return RestResponseUtil.success(StaffUtil.buildDetailByStaff(staff));
    }

    /**
     * 获取staff列表
     * @param pageableQueryWithoutArgRequest
     * @return
     */
    @Override
    public RestResponse<?> retrieveAll(PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest) {
        //获取分页参数对象
        PageableCriteria pageableCriteria = pageableQueryWithoutArgRequest.getPageableCriteria();
        //构建PageRequest
        PageRequest pageRequest;
        if (null == pageableCriteria) {
            pageRequest = PageableQueryUtil.defaultPageReqestBuilder();
        }else{
            pageRequest = PageableQueryUtil.pageRequestBuilder(pageableCriteria);
        }
        //分页查询
        Page<Staff> staffPage = this.staffRepository.findAll(pageRequest);
        //返回结果
        return RestResponseUtil.success(staffPage);
    }


}
