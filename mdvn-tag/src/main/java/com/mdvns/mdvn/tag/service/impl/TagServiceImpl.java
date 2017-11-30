package com.mdvns.mdvn.tag.service.impl;

import com.mdvns.mdvn.common.bean.PageableCriteria;
import com.mdvns.mdvn.common.bean.PageableQueryWithoutArgRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.PageableQueryUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.tag.domain.entity.Tag;
import com.mdvns.mdvn.tag.repository.TagRepository;
import com.mdvns.mdvn.tag.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    private static final Logger LOG = LoggerFactory.getLogger(TagServiceImpl.class);

    @Autowired
    private TagRepository tagRepository;

    /**
     * 新建tag
     * @param tag 新建数据
     * @return  新建成功的tag
     * @throws BusinessException tag name已存在
     */
    @Transactional
    public RestResponse<?> create(Tag tag) throws BusinessException {
        //将名称包含的所有空格去掉
        String name = MdvnCommonUtil.trimAllSpace(tag.getName());
        Tag tg;
        //根据name查询
        tg = this.tagRepository.findByName(name);
        //如果给定name的数据已存在,抛出异常
        MdvnCommonUtil.exists(tg, "name", name);
        //设置name为去除空格后的name
        tag.setName(name);
        //设置编号
        tag.setSerialNo(buildSerialNo4Tag());
        //保存tag
        tg = this.tagRepository.saveAndFlush(tag);
        //构建response
        return RestResponseUtil.success(tg);
    }

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
        Page<Tag> deptPage = this.tagRepository.findAll(pageRequest);
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
        String name = MdvnCommonUtil.trimAllSpace(retrieveDetailRequest.getCriterion());
        //查询name包含指定字符串的所有tag
        List<Tag> tags = this.tagRepository.findByNameContains(name);
        //如果查询数据不存在，返回空数组
        if (tags.isEmpty()) {
            tags = new ArrayList<>();
        }
        //返回结果
        return RestResponseUtil.success(tags);
    }

    /**
     * 构建编号
     * @return
     */
    private String buildSerialNo4Tag() {
        //查询表中的最大id  maxId
        Long maxId = this.tagRepository.getMaxId();
        //如果表中没有数据，则给maxId赋值为0
        if (maxId == null) {
            maxId = Long.valueOf(MdvnConstant.ZERO);
        }
        maxId += MdvnConstant.ONE;
        return MdvnConstant.CONSTANT_T + maxId;
    }
}
