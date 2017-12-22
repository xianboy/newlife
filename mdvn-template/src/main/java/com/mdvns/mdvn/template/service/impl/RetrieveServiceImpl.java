package com.mdvns.mdvn.template.service.impl;

import com.mdvns.mdvn.common.bean.*;
import com.mdvns.mdvn.common.bean.model.PageableCriteria;
import com.mdvns.mdvn.common.bean.model.TerseInfo;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.ConvertObjectUtil;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.PageableQueryUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.template.domain.entity.FunctionLabel;
import com.mdvns.mdvn.template.domain.entity.Template;
import com.mdvns.mdvn.template.repository.LabelRepository;
import com.mdvns.mdvn.template.repository.TemplateRepository;
import com.mdvns.mdvn.template.repository.TemplateRoleRepository;
import com.mdvns.mdvn.template.service.RetrieveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RetrieveServiceImpl implements RetrieveService {

    private static final Logger LOG = LoggerFactory.getLogger(RetrieveServiceImpl.class);

    @Resource
    private TemplateRepository templateRepository;

    @Resource
    private TemplateRoleRepository roleRepository;

    @Resource
    private LabelRepository labelRepository;

    /**
     * 根据industryId查询模板
     * @param criterionRequest request
     * @return RestResponse
     */
    @Override
    public RestResponse<?> retrieveByIndustryId(SingleCriterionRequest criterionRequest) {
        //根据request获取industryId
        Long industryId = Long.valueOf(criterionRequest.getCriterion());
        //查询
        List<Object[]> resultSet = this.templateRepository.findByIndustryId(industryId);
        List<TerseInfo> templates = ConvertObjectUtil.convertObjectArray2TerseInfo(resultSet);
        return RestResponseUtil.success(templates);
    }

    /**
     * 获取全部模板:支持分页
     * @param pageableQueryWithoutArgRequest request
     * @return restResponse
     */
    @Override
    public RestResponse<?> retrieveAll(PageableQueryWithoutArgRequest pageableQueryWithoutArgRequest) {
        //获取分页参数对象
        PageableCriteria pageableCriteria = pageableQueryWithoutArgRequest.getPageableCriteria();
        //构建PageRequest
        PageRequest pageRequest;
        if (null == pageableCriteria) {
            LOG.info("id为[{}]的用户没有填写分页参数，故使用默认分页.", pageableQueryWithoutArgRequest.getStaffId());
            pageRequest = PageableQueryUtil.defaultPageReqestBuilder();
        } else {
            pageRequest = PageableQueryUtil.pageRequestBuilder(pageableCriteria);
        }
        //分页查询
        Page<Template> templatePage = this.templateRepository.findAll(pageRequest);
        //返回结果
        return RestResponseUtil.success(templatePage);
    }

    /**
     * 根据指定id集合查询基本信息
     * @param retrieveBaseInfoRequest retrieveBaseInfoRequest
     * @return restResponse
     */
    @Override
    public RestResponse<?> retrieveBaseInfo(RetrieveBaseInfoRequest retrieveBaseInfoRequest) {
        //根据request获取id集合
        List<Long> ids = retrieveBaseInfoRequest.getIds();
        List<Object[]> resultSet = this.templateRepository.findTerseInfoById(ids);
        List<TerseInfo> templates = ConvertObjectUtil.convertObjectArray2TerseInfo(resultSet);
        return RestResponseUtil.success(templates);
    }

    /**
     * 根据指定id集合查询TemplateRole基本信息
     * @param retrieveBaseInfoRequest retrieveBaseInfoRequest
     * @return restResponse
     */
    @Override
    public RestResponse<?> retrieveRoleBaseInfo(RetrieveBaseInfoRequest retrieveBaseInfoRequest) {
        LOG.info("根据指定的id集合【{}】查询id和name开始...", retrieveBaseInfoRequest.getIds().toString());
        //根据request获取id集合
        List<Long> ids = retrieveBaseInfoRequest.getIds();
        List<Object[]> resultSet = this.roleRepository.findTerseInfoById(ids);
        List<TerseInfo> tmplRoles = ConvertObjectUtil.convertObjectArray2TerseInfo(resultSet);
        LOG.info("根据指定的id集合查询id和name成功.角色有【{}】.", tmplRoles.toString());
        return RestResponseUtil.success(tmplRoles);
    }

    /**
     * 根据id集合查询 FunctionLabel
     * @param retrieveBaseInfoRequest retrieveBaseInfoRequest
     * @return restResponse
     */
    @Override
    public RestResponse<?> retrieveLabel(RetrieveBaseInfoRequest retrieveBaseInfoRequest) throws BusinessException {
        //根据request获取id集合
        List<Long> ids = retrieveBaseInfoRequest.getIds();
        //查询id和name
        List<Object[]> resultSet = this.labelRepository.findTerseInfoById(ids);
        MdvnCommonUtil.emptyList(resultSet, ErrorEnum.FUNCTION_LABEL_NOT_EXISTS, "id为【"+ids.toString()+"】的functionLabel不存在...");
        //结果集转换
        List<TerseInfo> labels = ConvertObjectUtil.convertObjectArray2TerseInfo(resultSet);
        return RestResponseUtil.success(labels);
    }

    /**
     * 根据name和hostSerialNo查询过程方法
     * @param retrieveRequest request
     * @return restResponse
     */
    @Override
    public RestResponse<?> retrieveByNameAndHost(RetrieveByNameAndHostRequest retrieveRequest) {
        List<FunctionLabel> functionLabels = this.labelRepository.findByHostSerialNoAndIsDeleted(retrieveRequest.getHostSerialNo(), MdvnConstant.ZERO);
        return RestResponseUtil.success(functionLabels.get(MdvnConstant.ZERO));
    }


}
