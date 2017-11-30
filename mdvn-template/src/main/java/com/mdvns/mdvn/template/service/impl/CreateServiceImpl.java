package com.mdvns.mdvn.template.service.impl;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.template.domain.CreateLabelRequest;
import com.mdvns.mdvn.template.domain.CreateTemplateRequest;
import com.mdvns.mdvn.template.domain.entity.FunctionLabel;
import com.mdvns.mdvn.template.domain.entity.Template;
import com.mdvns.mdvn.template.domain.entity.TemplateLabel;
import com.mdvns.mdvn.template.repository.LabelRepository;
import com.mdvns.mdvn.template.repository.TemplateRepository;
import com.mdvns.mdvn.template.service.CreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateServiceImpl implements CreateService {


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
        //根据name查询模板
        Template template = this.tmplRepository.findByName(createRequest.getName());
        //指定name的模板如果存在，抛出异常
        MdvnCommonUtil.exists(template, "name", createRequest.getName());
        //保存template
        template = createByRequest(createRequest);
        //保存functionLabel
        createLabelByRequest(createRequest.getCreatorId(), createRequest.getLabels());



        return null;
    }

    private void createLabelByRequest(Long creatorId, List<FunctionLabel> labels) {
        List<FunctionLabel> labelList = new ArrayList<>();
        //遍历lables
        for (FunctionLabel lbel:labels) {
            //根据name查询FunctionLabel
            FunctionLabel label = this.labelRepository.findByName(lbel.getName());
            //如果label不存在, 新建
            if (null == label) {
                lbel.setCreatorId(creatorId);
                label = this.labelRepository.saveAndFlush(lbel);
            }
            labelList.add(label);
            //设置创建者id
            label.setCreatorId(creatorId);
            //保存label


            if (!(null == label.getSubLabelNames()||label.getSubLabelNames().isEmpty())) {

            }

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
        template.setSerialNum(buildSerialNo4Tmpl());

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
        return MdvnConstant.CONSTANT_T + maxId;
    }
}
