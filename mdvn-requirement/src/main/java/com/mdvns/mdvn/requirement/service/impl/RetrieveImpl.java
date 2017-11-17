package com.mdvns.mdvn.requirement.service.impl;

import com.mdvns.mdvn.common.bean.PageableCriteria;
import com.mdvns.mdvn.common.bean.PageableResponse;
import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.bean.RetrieveListByProjIdRequest;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.requirement.domain.Requirement;
import com.mdvns.mdvn.requirement.repository.RequirementRepository;
import com.mdvns.mdvn.requirement.service.RetrieveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

@Service
public class RetrieveImpl implements RetrieveService {

    private static final Logger LOG = LoggerFactory.getLogger(RetrieveImpl.class);

    @Autowired
    private RequirementRepository repository;

    /**
     * 查询指定project下的requirement列表:支持分页
     * @param byProjIdRequest
     * @return
     */
    @Override
    public ResponseEntity<?> retrieveListByProjId(RetrieveListByProjIdRequest byProjIdRequest) {

        Long projId = byProjIdRequest.getProjId();
        PageableCriteria pageableCriteria = byProjIdRequest.getPageableCriteria();
        PageRequest pageRequest = null;
        //构建分页对象
        if (pageableCriteria == null) {
            pageRequest = defaultPageReqestBuilder();
        } else {
            pageRequest = pageRequestBuilder(pageableCriteria);
        }
        LOG.info("projId:{},分页参数:{}",projId, pageRequest.toString());
        //执行分页查询
        Page<Requirement> requirementPage = this.repository.findByProjId(projId, pageRequest);
        //构建分页response
        return RestResponseUtil.success(requirementPage);
    }

    /**
     * 根据Page对象构建分页response
     * @param requirementPage
     * @return
     */
    private PageableResponse<Requirement> pageableResponseBuilder(Page<?> requirementPage) {

        PageableResponse pageableResponse = new PageableResponse();
        //设置当前页数据
        pageableResponse.setContent(requirementPage.getContent());
        //设置总页数
        pageableResponse.setTotalPages(requirementPage.getTotalPages());
        //设置数据总数
        pageableResponse.setTotalElements(requirementPage.getTotalElements());
        //是否最后一页
        pageableResponse.setLast(requirementPage.isLast());
        //当前页码:从0开始
        pageableResponse.setNumber(requirementPage.getNumber()+1);
        //设置每页额定数据条数
        pageableResponse.setSize(requirementPage.getSize());
        //设置当前页数据
        pageableResponse.setNumberOfElements(requirementPage.getNumberOfElements());
        //设置是否第一页
        pageableResponse.setFirst(requirementPage.isFirst());
        return pageableResponse;
    }

    /**
     * 根据pageableCriteria 构建分页参数对象 pageRequest
     * @param pageableCriteria
     * @return
     */
    private PageRequest pageRequestBuilder(@Validated PageableCriteria pageableCriteria) {
        //当前页
        Integer page = pageableCriteria.getPage();
        //每页条数
        Integer size = pageableCriteria.getSize();

        if (null==page||null==size||page<1||size<1) {
            LOG.error("分页参数page:{}, size:{}, 不能为空且不能小于1", page, size);
            throw new IllegalArgumentException("分页参数：page和size 不能为空且不能小于1!");
        }

        //排序字段
        String[] defaultSortBy = {"id"};
        List<String> sortBy = pageableCriteria.getSortBy();
        if (null==sortBy||sortBy.isEmpty()) {
            sortBy = Arrays.asList(defaultSortBy);
        }
        //排序方向
        Integer direction = pageableCriteria.getDirection();
        PageRequest pageRequest = null;
        if (direction == null || direction == 0) {
            pageRequest = new PageRequest(page - 1, size, new Sort(Sort.DEFAULT_DIRECTION, sortBy));
        } else {
            pageRequest = new PageRequest(page - 1, size, new Sort(Sort.Direction.DESC, sortBy));
        }

        return pageRequest;
    }
    /**
     * 构建默认分页查询参数对象
     * @return
     */
    private PageRequest defaultPageReqestBuilder() {
        return new PageRequest(0, 10, new Sort(Sort.Direction.ASC, "id"));
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
