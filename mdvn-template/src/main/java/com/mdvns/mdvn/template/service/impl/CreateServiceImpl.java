package com.mdvns.mdvn.template.service.impl;

import com.mdvns.mdvn.common.bean.CustomFunctionLabelRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.template.domain.CreateTemplateRequest;
import com.mdvns.mdvn.template.domain.entity.Deliverable;
import com.mdvns.mdvn.template.domain.entity.FunctionLabel;
import com.mdvns.mdvn.template.domain.entity.IterationTemplate;
import com.mdvns.mdvn.template.domain.entity.Template;
import com.mdvns.mdvn.template.repository.LabelRepository;
import com.mdvns.mdvn.template.repository.TemplateRepository;
import com.mdvns.mdvn.template.service.CreateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateServiceImpl implements CreateService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateServiceImpl.class);

    @Autowired
    private TemplateRepository tmplRepository;

    @Autowired
    private LabelRepository labelRepository;


    /**
     * 新建模板
     * @param createRequest 新建request
     * @return 新建模板
     */
    @Override
    @Transactional
    public RestResponse<?> create(CreateTemplateRequest createRequest) throws BusinessException {
        Long creatorId =  createRequest.getCreatorId();
        //根据name查询模板
        Template template = this.tmplRepository.findByName(createRequest.getName());
        //指定name的模板如果存在，抛出异常
        MdvnCommonUtil.existingError(template, "name", createRequest.getName());
        //保存template
        template = createByRequest(createRequest);
        //处理functionLabel
        createLabels(creatorId, template.getSerialNo(), createRequest.getLabels());
        //处理roles
        handleRoles(creatorId, template.getId(), createRequest.getRoleNames());
        //处理迭代模板itTemplates
        handleItTemplates(creatorId, template.getId(), createRequest.getItTemplates());
        ////处理deliverables
        handleDeliverables(creatorId, template.getId(), createRequest.getDeliverables());

        return RestResponseUtil.success(template);
    }

    /**
     * 保存自定义过程方法模块
     * @param customRequest request
     * @return restResponse
     */
    @Override
    public RestResponse<?> create(CustomFunctionLabelRequest customRequest) {
        LOG.info("即将保存自定义过程方法模块...");
        FunctionLabel label = new FunctionLabel();
        //设置creatorId
        label.setCreatorId(customRequest.getCreatorId());
        //设置name
        label.setName(customRequest.getName());
        //保存
        label = this.labelRepository.saveAndFlush(label);
        LOG.info("保存自定义过程方法模块成功...");
        return RestResponseUtil.success(label);
    }

    /**
     * 创建交付件
     * @param creatorId
     * @param hostId
     * @param deliverables
     */
    private void handleDeliverables(Long creatorId, Long hostId, List<Deliverable> deliverables) {

    }

    /**
     *
     * @param creatorId
     * @param templateId
     * @param itTemplates
     */
    private void handleItTemplates(Long creatorId, Long templateId, List<IterationTemplate> itTemplates) {
        for (IterationTemplate itTemplate: itTemplates) {
            itTemplate.setCreatorId(creatorId);
            itTemplate.setTemplateId(templateId);
        }
    }

    private void handleRoles(Long creatorId, Long id, List<String> roles) {


    }

    /**
     * 创建过程方法
     * @param creatorId 创建人id
     * @param hostSerialNo  上层对象编号
     * @param labels    模板集合
     */
    private void createLabels(Long creatorId, String hostSerialNo, List<FunctionLabel> labels) throws BusinessException {
        //遍历labels
        for (FunctionLabel label:labels) {
            //根据name查询FunctionLabel
           /*FunctionLabel label = this.labelRepository.findByName(lbel.getName());
            //如果label不存在, 新建
            if (null == label) {
                lbel.setCreatorId(creatorId);
                lbel.setHostId(tmplId);
                label = this.labelRepository.saveAndFlush(lbel);
            }*/

           //构建并保存label
            label.setCreatorId(creatorId);
            label.setHostSerialNo(hostSerialNo);
            FunctionLabel lbel = this.labelRepository.saveAndFlush(label);
            List<String> subLabels = label.getSubLabels();
            //如果子过程方法存在
            if ((null == subLabels||subLabels.isEmpty())) {
                LOG.error("子过程方法不能为空...");
                throw new BusinessException(ErrorEnum.SUB_LABEL_IS_NULL, "新建模板时, 子过程方法不能为空...");
            }
            //保存子过程方法
            createSubLabels(creatorId, lbel.getId().toString(), label.getSubLabels());

        }

    }

    /**
     * 保存子过程方法模块
     * @param creatorId
     * @param hostSerialNo
     * @param subLabels
     */
    private void createSubLabels(Long creatorId, String hostSerialNo, List<String> subLabels) {
        //遍历subLabels
        for (String name : subLabels) {
            FunctionLabel label = new FunctionLabel();
            label.setName(name);
            label.setCreatorId(creatorId);
            label.setHostSerialNo(hostSerialNo);
            this.labelRepository.saveAndFlush(label);
        }

    }

    /**
     * 根据request保存template
     * @param createRequest request
     * @return 新建模板
     */
    private Template createByRequest(CreateTemplateRequest createRequest) {
        Template template = new Template();
        template.setName(createRequest.getName());
        template.setCreatorId(createRequest.getCreatorId());
        template.setIndustryId(createRequest.getIndustryId());
        template.setStyle(createRequest.getStyle());
        template.setSerialNo(buildSerialNo4Tmpl());

        return this.tmplRepository.saveAndFlush(template);
    }

    /**
     * 构建编号
     * @return 编号
     */
    private String buildSerialNo4Tmpl() {
        //查询表中的最大id  maxId
        Long maxId = this.tmplRepository.getMaxId();
        //如果表中没有数据，则给maxId赋值为0
        if (maxId == null) {
            maxId = Long.valueOf(MdvnConstant.ZERO);
        }
        maxId += MdvnConstant.ONE;
        return MdvnConstant.T + maxId;
    }
}
