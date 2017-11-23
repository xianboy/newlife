package com.mdvns.mdvn.staff.service.impl;

import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.staff.domain.CreateStaffRequest;
import com.mdvns.mdvn.staff.domain.StaffResponse;
import com.mdvns.mdvn.staff.domain.entity.Staff;
import com.mdvns.mdvn.staff.repository.StaffRepository;
import com.mdvns.mdvn.staff.repository.StaffTagRepository;
import com.mdvns.mdvn.staff.service.StaffService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class StaffServiceImpl implements StaffService {
    /* 日志常量 */
    private static final Logger LOG = LoggerFactory.getLogger(StaffServiceImpl.class);

    private final String CLASS = this.getClass().getName();

    @Autowired
    private StaffRepository staffRepository;


    @Autowired
    private StaffTagRepository staffTagRepository;

    /**
     * 添加staff
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public ResponseEntity create(CreateStaffRequest request) throws BusinessException {
        //如果用户已存在，抛出用户已存在异常
        if (staffExists(request.getAccount())) {
            LOG.error("account 为：{}的Staff已存在.", request.getAccount());
            throw new BusinessException(ErrorEnum.STAFF_EXISTS_ERROR);
        }
        //根据createStaffRequest构建staff对象
        Staff staff = buildStaffByRequest(request);

        //根据Staff构建StaffResponse
        StaffResponse response = buildStaffResponse(staff);


        response.setSerialNum(staff.getSerialNum());
        response.setName(staff.getName());
        response.setGender(staff.getGender());
//       private Postion positionDetail;
//       private Department deptDetail;
        response.setEmailAddr(staff.getEmailAddr());
        response.setPhoneNum(staff.getPhoneNum());
        response.setTagsCnt(staffTags.size());
        response.setStatus(staff.getStatus());

        return response;
    }

    /**
     * 根据Staff构建StaffResponse
     *
     * @param staff
     * @return
     */
    private StaffResponse buildStaffResponse(Staff staff) {
        StaffResponse response = new StaffResponse();
        //设置员工信息
        response.setStaff(staff);
        //获取Position信息
        Position position = retrieveById(staff.getPositionId());

        private Department deptDetail;
        return response;
    }

    /**
     * 根据createStaffRequest 构建Staff
     *
     * @param request
     * @return
     */
    private Staff buildStaffByRequest(CreateStaffRequest request) {
        Staff staff = new Staff();
        //设置编号
        staff.setSerialNum(buildSerialNum4Staff());
        //设置密码
        staff.setPassword(request.getPassword());
        //设置部门id
        staff.setDeptId(request.getDeptId());
        //设置姓名
        staff.setName(request.getName());
        //设置账号
        staff.setAccount(request.getAccount());
        //设置添加人
        staff.setCreatorId(request.getCreatorId());
        //设置职位id
        staff.setPositionId(request.getPositionId());
        //设置职级
        staff.setPositionLvl(request.getPositionLvl());
        //设置邮箱
        staff.setEmail(request.getEmail());
        //设置手机号
        staff.setMobile(request.getMobile());
        //设置性别
        staff.setGender(request.getGender());
        //设置状态
        staff.setStatus("active");
        //设置标签
        staff.setTags(request.getTags());
        return staff;
    }

    /**
     * 构建员工编号
     *
     * @return
     */
    private String buildSerialNum4Staff() {
        //查询表中的最大id  maxId
        Long maxId = this.staffRepository.getMaxId();
        //如果表中没有数据，则给maxId赋值为0
        if (maxId == null) {
            maxId = 0L;
        }
        maxId += MdvnConstant.ONE;
        return MdvnConstant.CONSTANT_E + maxId;
    }

    /**
     * 指定account的Staff是否已存在
     * 不存在：notExists = true
     *
     * @param account
     * @return Boolean
     */
    private Boolean staffExists(String account) {
        Boolean exists = true;
        //查询Staff是否存在
        Staff staff = this.staffRepository.findByAccount(account);
        if (null == staff) {
            exists = false;
        }
        return exists;
    }

    /**
     * 获取全部模块
     * @return
     */
   /* @Override
    public RetrieveStaffListResponse rtrvStaffList(RetrieveStaffListRequest request) {
        RetrieveStaffListResponse retrieveStaffListResponse = new RetrieveStaffListResponse();
        if (request.getPage() == null || request.getPageSize() == null) {
//            List<Staff> list = this.staffRepository.findAll();
            List<Staff> list = this.staffRepository.findAllByAccountIsNot("admin");
            retrieveStaffListResponse.setStaffs(list);
            retrieveStaffListResponse.setTotalNumber(Long.valueOf(list.size()));
            return retrieveStaffListResponse;
        } else {
            String sortBy = request.getSortBy();
            Integer page = request.getPage() - 1;
            Integer pageSize = request.getPageSize();
            sortBy = (sortBy == null) ? "uuId" : sortBy;
            PageRequest pageable = new PageRequest(page, pageSize, Sort.Direction.ASC, sortBy);
            Page<Staff> staffPage = null;
//            staffPage = this.staffRepository.findAll(pageable);
            staffPage = this.staffRepository.findAllByAccountIsNot("admin", pageable);
//            Long count = this.staffRepository.getStaffCount();
            retrieveStaffListResponse.setStaffs(staffPage.getContent());
            retrieveStaffListResponse.setTotalNumber(staffPage.getTotalElements());
            return retrieveStaffListResponse;
        }
    }

    *//**
     * 通过staffIdList获取staff对象列表
     * @param request
     * @return
     *//*
    @Override
    public List<Staff> rtrvStaffListById(RtrvStaffListByIdRequest request) {
        List<Staff> list = new ArrayList<>();
        for (int i = 0; i < request.getStaffIdList().size(); i++) {
            Staff staffInfo = this.staffRepository.findByStaffId(request.getStaffIdList().get(i));
            if (null == staffInfo) {
                LOG.error("该id的员工在员工库中不存在.", staffInfo);
                throw new BusinessException(staffInfo + "该id的员工在员工库中不存在.");
            } else {
                list.add(staffInfo);
            }
        }
        return list;
    }

    *//**
     * 通过staffId获取单条staff对象
     * @param staffId
     * @return
     *//*
    @Override
    public Staff rtrvStaffInfo(String staffId) {
        return this.staffRepository.findByStaffId(staffId);
    }


    @Override
    public List<StaffTag> rtrvStaffTagList(String staffId) {
        return staffTagRepository.findByStaffIdAndIsDeleted(staffId, 0);
    }

    @Override
    public Boolean updateStaffDetail(UpdateStaffDetailRequest request) {
        Boolean flag = false;
        Staff staff = staffRepository.findByStaffId(request.getStaffInfo().getStaffId());
        Staff updateStaff = request.getStaffInfo();
//        RtrvStaffDetailResponse response = new RtrvStaffDetailResponse();
        if (staff != null) {
            if (!updateStaff.getDeptId().equals(staff.getDeptId())) {
                staff.setDeptId(updateStaff.getDeptId());
            }
            if (!StringUtils.isEmpty(updateStaff.getPassword()) && !updateStaff.getPassword().equals(staff.getPassword())) {
                staff.setPassword(updateStaff.getPassword());
            }
            if (!StringUtils.isEmpty(updateStaff.getGender()) && !updateStaff.getGender().equals(staff.getGender())) {
                staff.setGender(updateStaff.getGender());
            }
            if (!StringUtils.isEmpty(updateStaff.getPositionId()) && !updateStaff.getPositionId().equals(staff.getPositionId())) {
                staff.setPositionId(updateStaff.getPositionId());
            }
            if (!StringUtils.isEmpty(updateStaff.getPositionLvl()) && !updateStaff.getPositionLvl().equals(staff.getPositionLvl())) {
                staff.setPositionLvl(updateStaff.getPositionLvl());
            }
            if (!StringUtils.isEmpty(updateStaff.getEmailAddr()) && !updateStaff.getEmailAddr().equals(staff.getEmailAddr())) {
                staff.setEmailAddr(updateStaff.getEmailAddr());
            }
            if (!StringUtils.isEmpty(updateStaff.getPhoneNum()) && !updateStaff.getPhoneNum().equals(staff.getPhoneNum())) {
                staff.setPhoneNum(updateStaff.getPhoneNum());
            }
            if (!StringUtils.isEmpty(updateStaff.getStatus()) && !updateStaff.getStatus().equals(staff.getStatus())) {
                staff.setStatus(updateStaff.getStatus());
            }

            staff = staffRepository.saveAndFlush(staff);

            flag = true;
        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<String> tagIds = request.getTagIds();
        List<StaffTag> staffTagList = staffTagRepository.findByStaffIdAndIsDeleted(request.getStaffInfo().getStaffId(), 0);
        if (tagIds != null && tagIds.size() == 0) {
            for (int i = 0; i < staffTagList.size(); i++) {
                staffTagList.get(i).setIsDeleted(1);
                staffTagList.get(i).setLastUpdateTime(now);
            }
            staffTagRepository.save(staffTagList);
        } else if (tagIds != null && tagIds.size() > 0) {
            List<StaffTag> oldStaffTag = staffTagRepository.findByStaffIdAndIsDeleted(request.getStaffInfo().getStaffId(), 0);
            List<StaffTag> rmvStaffTag = oldStaffTag;

            for (int i = 0; i < tagIds.size(); i++) {
                for (int j = 0; j < oldStaffTag.size(); j++) {
                    if (oldStaffTag.get(j).getTagId().equals(tagIds.get(i))) {
                        rmvStaffTag.remove(j);
                    }
                }
            }

            if (!rmvStaffTag.isEmpty()) {
                for (int i = 0; i < rmvStaffTag.size(); i++) {
                    rmvStaffTag.get(i).setIsDeleted(1);
                    rmvStaffTag.get(i).setLastUpdateTime(now);
                }
                staffTagRepository.save(oldStaffTag);
                flag = true;
            }

            oldStaffTag = staffTagRepository.findByStaffIdAndIsDeleted(request.getStaffInfo().getStaffId(), 0);
            List<String> addList = tagIds;
            for (int i = 0; i < tagIds.size(); i++) {
                for (int j = 0; j < oldStaffTag.size(); j++) {
                    if (tagIds.get(i).equals(oldStaffTag.get(j).getTagId())) {
                        addList.remove(i);
                    }
                }
            }

            if (!addList.isEmpty()) {
                List<StaffTag> addStaffTag = new ArrayList<>();
                for (int i = 0; i < addList.size(); i++) {
                    StaffTag staffTag = new StaffTag();
                    staffTag.setStaffId(staff.getStaffId());
                    staffTag.setTagId(addList.get(i));
                    staffTag.setIsDeleted(0);
                    staffTag.setLastUpdateTime(now);
                    addStaffTag.add(staffTag);
                }
                staffTagRepository.save(addStaffTag);
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public Boolean deleteStaff(String staffId) {
        Staff staff = staffRepository.findByStaffId(staffId);
        if (staff != null && !staff.getStatus().equals("cancellation")) {
            staff.setStatus("cancellation");
        }
        staffRepository.saveAndFlush(staff);
        return true;
    }


    *//**
     * 根据name模糊查询Staff，当name==null, 通过tag查询staff
     * @param request
     * @return
     * @throws SQLException
     *//*
    @Override
    public ResponseEntity<?> rtrvStaffListByStaffName(RtrvStaffListByNameRequest request) throws SQLException {
        String sortBy = (request.getSortBy() == null) ? "staffId" : request.getSortBy();
        //姓名
        String name = request.getName();
        //如果name没有值
        if (StringUtils.isEmpty(name)) {
            List<String> tags = request.getTags();
            if (tags == null) {
                LOG.error("标签参数为空:{}", tags);
            }
            return null;
        }
        //第几页
        Integer page = request.getPage();
        //需获取的数据条数
        Integer pageSize = request.getPageSize();
        page = (page == null || page < Integer.valueOf(ConstantEnum.ONE.getValue())) ? Integer.valueOf(ConstantEnum.ONE.getValue()) : page;
        pageSize = (pageSize == null || pageSize < Integer.valueOf(ConstantEnum.ONE.getValue())) ? Integer.valueOf(ConstantEnum.TEN.getValue()) : pageSize;
        //构建分页参数
        PageRequest pageable = new PageRequest(page, pageSize);
        //分页对象
        Page<Staff> staffPage;
        LOG.info("查询name以：{}开头的员工", name);
        staffPage = this.staffRepository.findByNameStartingWith(name, pageable);

        return ResponseEntity.ok(staffPage);

    }


    *//**
     * 根据指定account查询staff
     * @param account
     * @return
     *//*
    @Override
    public ResponseEntity<?> findByAccountAndPassword(String account, String password) {
        Staff staff = this.staffRepository.findByAccountAndPassword(account, password);
        return ResponseEntity.ok(staff);
    }

    *//**
     * 查找拥有标签集中最多标签的StaffTag
     * @param tags
     * @return List<StaffTag>
     *//*
    @Override
    public ResponseEntity<?> getStaffByTags(List<String> tags) {
        List<StaffTag> staffTags = this.staffTagRepository.findByTagIdIn(tags);
        LOG.info("拥有标签集中任意标签的Staff有：{}个", staffTags.size());
        return ResponseEntity.ok(staffTags);
    }

    *//**
     * 查询name以指定字符串开头的所有Staff
     * @param startingStr
     * @return
     *//*
    @Override
    public ResponseEntity<?> findByNameStartingWith(String startingStr) {

        List<Staff> staffPage = this.staffRepository.findByNameStartingWith(startingStr);
        LOG.info("查询name以指定字符串:{}开头的所有Staff...", startingStr);
        return ResponseEntity.ok(staffPage);
    }

    *//**
     * 查询staffId为指定id的所有tagId集合
     * @param staffId
     * @return
     *//*
    @Override
    public List<String> rtrvTagsByStaffId(String staffId) {

        return this.staffTagRepository.findTagIdByStaffId(staffId);
    }

*/
}
