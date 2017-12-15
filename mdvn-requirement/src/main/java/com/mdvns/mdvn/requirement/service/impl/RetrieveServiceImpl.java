package com.mdvns.mdvn.requirement.service.impl;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.SingleCriterionRequest;
import com.mdvns.mdvn.common.bean.model.PageableCriteria;
import com.mdvns.mdvn.common.bean.model.RequirementDetail;
import com.mdvns.mdvn.common.bean.model.RoleMember;
import com.mdvns.mdvn.common.bean.model.TerseInfo;
import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.MdvnCommonUtil;
import com.mdvns.mdvn.common.util.PageableQueryUtil;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.common.util.RestTemplateUtil;
import com.mdvns.mdvn.requirement.config.WebConfig;
import com.mdvns.mdvn.requirement.domain.entity.Requirement;
import com.mdvns.mdvn.requirement.repository.RequirementRepository;
import com.mdvns.mdvn.requirement.service.MemberService;
import com.mdvns.mdvn.requirement.service.RetrieveService;
import com.mdvns.mdvn.requirement.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RetrieveServiceImpl implements RetrieveService {

    private static final Logger LOG = LoggerFactory.getLogger(RetrieveServiceImpl.class);

    @Resource
    private RequirementRepository repository;

    @Resource
    private TagService tagService;

    @Resource
    private MemberService memberService;

    @Resource
    private WebConfig webConfig;


    /**
     * 查询指定project下的requirement列表:支持分页
     *
     * @param byProjIdRequest request
     * @return restResponse
     */
    @Override
    public RestResponse<?> retrieveListByProjId(SingleCriterionRequest byProjIdRequest) {
        //获取request中的projId
        Long projId = Long.valueOf(byProjIdRequest.getCriterion());

        PageableCriteria pageableCriteria = byProjIdRequest.getPageableCriteria();
        PageRequest pageRequest;
        //构建分页对象
        if (pageableCriteria == null) {
            pageRequest = PageableQueryUtil.defaultPageReqestBuilder();
        } else {
            pageRequest = PageableQueryUtil.pageRequestBuilder(pageableCriteria);
        }
        LOG.info("projId:{},分页参数:{}", projId, pageRequest.toString());
        //执行分页查询
        Page<Requirement> requirementPage = this.repository.findByProjId(projId, pageRequest);
        //构建分页response
        return RestResponseUtil.success(requirementPage);
    }

    /**
     * 根据id获取需求详情
     *
     * @param retrieveDetailRequest request
     * @return restResponse
     */
    @Override
    public RestResponse<?> retrieveDetailById(SingleCriterionRequest retrieveDetailRequest) throws BusinessException {
        LOG.info("获取指定id需求的详情, 开始运行【retrieveDetailById】service...");
        //获取request中的id
        Long id = Long.valueOf(retrieveDetailRequest.getCriterion());
        //根据id查询
        Requirement requirement = this.repository.findOne(id);
        //数据不存在，抛异常
        MdvnCommonUtil.notExistingError(requirement, MdvnConstant.ID, retrieveDetailRequest.getCriterion());
        //设置
        RequirementDetail detail = buildDetail(retrieveDetailRequest.getStaffId(), requirement);
        LOG.info("获取指定id项目的详情成功, 结束运行【retrieveDetailById】service...");
        //返回结果
        return RestResponseUtil.success(detail);
    }

    /**
     * 构建需求详情
     *
     * @param staffId 当前用户id
     * @param requirement  requirement
     * @return requirementDetail
     */
    private RequirementDetail buildDetail(Long staffId, Requirement requirement) throws BusinessException {
        RequirementDetail detail = new RequirementDetail();
        //设置id
        detail.setId(requirement.getId());
        //设置编号
        detail.setSerialNo(requirement.getSerialNo());
        //设置状态
        detail.setStatus(requirement.getStatus());
        //设置进度
        detail.setProgress(requirement.getProgress());
        //设置概要
        detail.setSummary(requirement.getSummary());
        //设置描述
        detail.setDescription(requirement.getDescription());
        //设置标签
        detail.setTags(getTags(staffId, requirement.getId()));
        //设置优先级
        detail.setPriority(requirement.getPriority());
        //设置过程方法
        detail.setLabel(getLabel(staffId, requirement.getFunctionLabelId()));
        //设置成员
        detail.setMembers(getRoleMembers(staffId, requirement.getId()));
        //设置开始/结束日期
        detail.setStartDate(requirement.getStartDate().getTime());
        detail.setEndDate(requirement.getEndDate().getTime());
        //设置story point 总数
//        detail.setStoryPointAmount(getStoryPointAmount());
        //设置附件
        return detail;
    }

    /**
     * 获取指定reqmntId 的角色成员
     *
     * @return list
     */
    private List<RoleMember> getRoleMembers(Long staffId, Long requirementId) throws BusinessException {
        LOG.info("获取指定需求成员, 开始运行【getLeaders】service...");
        return this.memberService.getRoleMembers(staffId, requirementId);
    }

    /**
     * 根据id获取FunctionLabel
     * @param staffId staffId
     * @param functionLabelId labelId
     * @return baseInfo
     * @throws BusinessException exception
     */
    private TerseInfo getLabel(Long staffId, Long functionLabelId) throws BusinessException {
        List<Long> ids = new ArrayList<>();
        ids.add(functionLabelId);
        String retrieveLabelUrl = webConfig.getRetrieveLabelUrl();
        List<TerseInfo> list = RestTemplateUtil.retrieveBasicInfo(staffId, ids, retrieveLabelUrl);
        //如果list为空, 抛出异常
        MdvnCommonUtil.emptyList(list, ErrorEnum.FUNCTION_LABEL_NOT_EXISTS, "id为【"+functionLabelId+"】的FunctionLabel不存在...");
        return list.get(MdvnConstant.ZERO);
    }

    /**
     * 获取指定reqmntId的标签
     *
     * @param staffId  staffId
     * @param reqmntId reqmntId
     * @return list
     * @throws BusinessException exception
     */
    private List<TerseInfo> getTags(Long staffId, Long reqmntId) throws BusinessException {
        LOG.info("查询指定需求的标签, 开始运行【getTags】service...");
        //获取指定项目的模板人id
        List<Long> ids = this.tagService.getTags(reqmntId);
        //构建获取指定项目标签url
        String retrieveTagsUrl = webConfig.getRetrieveTagsUrl();
        //调用tag模块获取负责人信息
        List<TerseInfo> tags = RestTemplateUtil.retrieveBasicInfo(staffId, ids, retrieveTagsUrl);
        MdvnCommonUtil.emptyList(tags, ErrorEnum.TAG_NOT_EXISTS, "id为【"+ids.toString()+"】的Tag不存在...");
        return tags;
    }


    /**
     * 分页查询:
     * @param
     * @return
     */
   /* @Override
    public ResponseEntity<?> retrieveList(RetrieveListRequest listRequest) {
        List<QueryParam> queryParams = listRequest.getQueryParams();
        PageableCriteria pageableCriteria = listRequest.getPageableCriteria();


        //根据参数名构建查询方法
        String queryMethodName = queryMethodBuilder(queryParams);


        if (listRequest==null) {

        }

        return null;
    }
    private String queryMethodBuilder(List<QueryParam> queryArgs) {
        StringBuilder queryMethodName = new StringBuilder("findBy");

        for (int i = 0; i < queryArgs.size(); i++) {
            QueryParam queryPara = queryArgs.get(i);
            if (i<queryArgs.size()-1) {
                queryMethodName.append(StringUtil.toUpperFristChar(queryPara.getName())).append("And");
            }else{
                queryMethodName.append(StringUtil.toUpperFristChar(queryPara.getName()));
            }
            return queryMethodName.toString();
        }
        for (QueryParam queryPara: queryArgs) {

        }
        Iterator<String> it = keySet.iterator();
        while (it.hasNext()) {
            String propertyName = StringUtil.toUpperFristChar();
            queryMethodName.append(it.next());
        }


        return queryMethodName.toString();
    }

   */


}
