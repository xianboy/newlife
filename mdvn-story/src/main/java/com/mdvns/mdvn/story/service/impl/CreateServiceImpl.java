package com.mdvns.mdvn.story.service.impl;

import com.mdvns.mdvn.common.bean.CustomFunctionLabelRequest;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.model.FunctionLabelModel;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.common.util.RestTemplateUtil;
import com.mdvns.mdvn.story.config.WebConfig;
import com.mdvns.mdvn.story.domain.CreateStoryRequest;
import com.mdvns.mdvn.story.domain.entity.Story;
import com.mdvns.mdvn.story.repository.StoryRepository;
import com.mdvns.mdvn.story.service.CreateService;
import com.mdvns.mdvn.story.service.MemberService;
import com.mdvns.mdvn.story.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class CreateServiceImpl implements CreateService {
    private static final Logger LOG = LoggerFactory.getLogger(CreateServiceImpl.class);

    @Resource
    private StoryRepository repository;

    @Resource
    private MemberService memberService;

    @Resource
    private TagService tagService;

    @Resource
    private WebConfig webConfig;

    /**
     * 创建story
     *
     * @param createRequest createRequest
     * @return restResponse
     */

    @Override
    @Transactional
    public RestResponse<?> create(CreateStoryRequest createRequest) throws BusinessException {
        //根据request构建story对象
        Story story = buildByRequest(createRequest);
        LOG.info("Story 的编号为【{}】", story.getSerialNo());
        //调用repository保存story
        story = this.repository.saveAndFlush(story);
        //story保存成功,保存成员映射
        Integer memberAmount = MdvnConstant.ZERO;
        if (!(null == createRequest.getMembers() || createRequest.getMembers().isEmpty())) {
            memberAmount = this.memberService.buildMembers(createRequest.getCreatorId(), story.getId(), createRequest.getMembers());
        }
        //设置成员数量
        story.setMemberAmount(memberAmount);
        //保存成功,保存story标签映射
        if (!(null == createRequest.getTags() || createRequest.getTags().isEmpty())) {
            this.tagService.buildTags(createRequest.getCreatorId(), story.getId(), createRequest.getTags());
        }

        LOG.debug("id为【{}】的story创建成功...", story.getId());
        return RestResponseUtil.success(story);
    }

    /**
     * 根据request构建Story对象
     *
     * @param request request
     * @return story
     */
    private Story buildByRequest(CreateStoryRequest request) throws BusinessException {
        Story story = new Story();
        story.setCreatorId(request.getCreatorId());
        story.setHostSerialNo(request.getHostSerialNo());
        String serialNo = buildSerialNo();
        story.setSerialNo(serialNo);
        story.setSummary(request.getSummary());
        story.setDescription(request.getDescription());
        story.setPriority(request.getPriority());
        story.setFunctionLabelId(buildLabel(request.getCreatorId(), serialNo, request.getFunctionLabel()));
        story.setStartDate(request.getStartDate());
        story.setEndDate(request.getEndDate());
        story.setStoryPoint(request.getStoryPoint());
        return story;
    }

    private Long buildLabel(Long creatorId, String hostSerialNo, Object functionLabel) throws BusinessException {
        Long id = null;
        //如果functionLabel为Long类型, 则为已存在的过程方法的id,直接返回
        if (functionLabel instanceof Integer) {
            id = Long.valueOf((Integer) functionLabel);
        } else if (functionLabel instanceof String) {
            //自定义过程方法url
            String customLabelUrl = webConfig.getCustomLabelUrl();
            //调用template模块构建自定义过程方法
            FunctionLabelModel labelModel = RestTemplateUtil.customLabel(customLabelUrl, new CustomFunctionLabelRequest(creatorId, hostSerialNo, (String) functionLabel));
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
        return MdvnConstant.S + maxId;
    }
}

