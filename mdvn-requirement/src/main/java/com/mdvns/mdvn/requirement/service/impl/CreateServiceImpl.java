package com.mdvns.mdvn.requirement.service.impl;

import com.mdvns.mdvn.common.bean.CustomFunctionLabelRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.model.FunctionLabelModel;
import com.mdvns.mdvn.common.bean.model.TerseInfo;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.common.util.RestTemplateUtil;
import com.mdvns.mdvn.requirement.config.WebConfig;
import com.mdvns.mdvn.requirement.domain.CreateRequirementRequest;
import com.mdvns.mdvn.requirement.domain.entity.Requirement;
import com.mdvns.mdvn.requirement.repository.RequirementRepository;
import com.mdvns.mdvn.requirement.service.CreateService;
import com.mdvns.mdvn.requirement.service.MemberService;
import com.mdvns.mdvn.requirement.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateServiceImpl implements CreateService {

    private static final Logger LOG = LoggerFactory.getLogger(CreateServiceImpl.class);

    @Resource
    private RequirementRepository repository;

    @Resource
    private MemberService memberService;

    @Resource
    private TagService tagService;

    @Resource
    private WebConfig webConfig;


    /**
     * 新建requirement
     *
     * @param createRequest createRequest
     * @return restResponse
     */
    @Override
    @Transactional
    public RestResponse<?> create(CreateRequirementRequest createRequest) throws BusinessException {
        //根据request构建requirement对象
        Requirement requirement = buildRequirementByRequest(createRequest);
        //调用repository保存requirement
        requirement = this.repository.saveAndFlush(requirement);
        //需求保存成功,保存成员映射
        Integer memberAmount = MdvnConstant.ZERO;
        if (!(null == createRequest.getMembers() || createRequest.getMembers().isEmpty())) {
            memberAmount = this.memberService.handleMembers(createRequest.getCreatorId(), requirement.getId(), createRequest.getMembers());
        }
        //设置成员数量
        requirement.setMemberAmount(memberAmount);
        //需求保存成功,保存需求标签映射
        if (!(null == createRequest.getTags() || createRequest.getTags().isEmpty())) {
            this.tagService.handleTags(createRequest.getCreatorId(), requirement.getId(), createRequest.getTags());
        }
        requirement.setCreator(buildCreator(createRequest.getCreatorId()));
        LOG.debug("id为【{}】的需求创建成功...", requirement.getId());
        return RestResponseUtil.success(requirement);
    }

    /**
     * 根据创建人id构建创建人
     *
     * @param creatorId creatorId
     * @return terseInfo
     * @throws BusinessException exception
     */
    private TerseInfo buildCreator(Long creatorId) throws BusinessException {
        List<Long> ids = new ArrayList<>();
        ids.add(creatorId);
        //调用Staff 模块查询id为creatorId的Staff
        String retrieveMembersUrl = webConfig.getRetrieveMembersUrl();
        List<TerseInfo> list = RestTemplateUtil.retrieveBasicInfo(creatorId, ids, retrieveMembersUrl);

        if (list.size() <= MdvnConstant.ZERO) {
            LOG.error("id为【{}】的用户不存在...", creatorId);
            throw new BusinessException(ErrorEnum.STAFF_NOT_EXISTS, "id为【" + creatorId + "】的用户不存在.");
        }

        return list.get(MdvnConstant.ZERO);
    }

    /**
     * 根据request构建requirement对象
     *
     * @param request request
     * @return requirement
     */
    private Requirement buildRequirementByRequest(CreateRequirementRequest request) throws BusinessException {
        Requirement reqmnt = new Requirement();
        //设置creatorId
        reqmnt.setCreatorId(request.getCreatorId());
        //设置projId
        reqmnt.setProjId(request.getProjId());
        //设置serialNo
        String serialNo = buildSerialNo();
        reqmnt.setSerialNo(serialNo);
        //设置summary
        reqmnt.setSummary(request.getSummary());
        //设置description
        reqmnt.setDescription(request.getDescription());
        //设置priority
        reqmnt.setPriority(request.getPriority());
        //设置template
        reqmnt.setTemplateId(request.getTemplateId());
        //设置functionLabel
        reqmnt.setFunctionLabelId(buildLabel(request.getCreatorId(), request.getFunctionLabel()));
        //设置startDate
        reqmnt.setStartDate(request.getStartDate());
        //设置endDate
        reqmnt.setEndDate(request.getEndDate());
        return reqmnt;

    }

    /**
     * 过程方法模块处理:
     * 1.如果参数functionLabel的值为Long类型，其值为当前所选template的一个过程方法模块的id
     * 2.如果参数functionLabel的值非Long, 其值为用户自定义的过程方法模块名称
     *
     * @param creatorId     creatorId
     * @param functionLabel functionLabel
     * @return labelId
     */
    private Long buildLabel(Long creatorId, Object functionLabel) throws BusinessException {
        Long id = null;
        //如果functionLabel为Long类型, 则为已存在的过程方法的id,直接返回
        if (functionLabel instanceof Integer) {
            id = Long.valueOf((Integer) functionLabel);
        } else if (functionLabel instanceof String) {
            String customLabelUrl = webConfig.getCustomLabelUrl();
            FunctionLabelModel labelModel = RestTemplateUtil.customLabel(customLabelUrl, new CustomFunctionLabelRequest(creatorId, (String) functionLabel));
            id = labelModel.getId();
        }
        return id;
    }

    /**
     * 构建需求编号:
     * 1.查询表中的最大id  maxId
     * 2.serialNum = "R" + (maxId + 1)
     *
     * @return serialNo
     */
    private String buildSerialNo() {
        //查询表中的最大id  maxId
        Long maxId = this.repository.getMaxId();
        //如果表中没有数据，则给maxId赋值为0
        if (maxId == null) {
            maxId = Long.valueOf(MdvnConstant.ZERO);
        }
        maxId += 1;
        return MdvnConstant.R + maxId;
    }


}
