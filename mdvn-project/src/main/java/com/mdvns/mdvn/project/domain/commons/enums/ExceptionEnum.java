package com.mdvns.mdvn.project.domain.commons.enums;

/**
 * 异常定义 枚举类
 */
public enum ExceptionEnum {
    /*UNKNOW_EXCEPTION("555", "未知错误"),
    PARAMS_EXCEPTION("556", "参数异常"),
    SAPI_EXCEPTION("10001", "SAPI异常"),
    OBJECT_DOES_NOT_EXIST("557", "对象不存在"),
    TAG_NOT_FOUND("1000", "Tag不存在!"),
    TAG_IS_CREATED("1001", "Tag已存在!"),
    Model_NOT_FOUND("1200", "模块不存在!"),
    Model_IS_CREATED("1201", "模块已存在!"),
    functionModel_NOT_FOUND("1202", "过程方法模块不存在!"),
    REQUEST_NOT_VALID("1300", "请求参数错误"),
    PROJECT_NOT_FOUND("1301", "项目不存在!"),
    PROJECT_STAFF_NOT_CREATE("1302", "调用SAPI保存项目负责人数据失败"),
    PROJECT_TAG_NOT_CREATE("1303", "调用SAPI保存项目标签数据失败"),
    PROJECT_MODEL_NOT_CREATE("1304", "调用SAPI保存项目模块数据失败"),
    PROJECT_CHECKLIST_NOT_CREATE("1305", "调用SAPI保存项目checklist数据失败"),
    PROJECT_ATTCHURL_NOT_CREATE("1306", "调用SAPI保存项目附件数据失败"),
    PROJECT_BASEINFO_NOT_CREATE("1307", "调用SAPI保存项目基础信息数据失败"),
    PROJECT_STAFF_NOT_UPDATE("1308", "调用SAPI更改项目负责人数据失败"),
    PROJECT_TAG_NOT_UPDATE("1309", "调用SAPI更改项目标签数据失败"),
    PROJECT_MODEL_NOT_UPDATE("1310", "调用SAPI更改项目模块数据失败"),
    PROJECT_CHECKLIST_NOT_UPDATE("1311", "调用SAPI更改项目checklist数据失败"),
    PROJECT_CHECKLIST_STAFFDETAIL_NOT_RTRV("1312", "调用SAPI获取项目checklist中的人员详细信息时失败"),
    PROJECT_ATTCHURL_NOT_UPDATE("1313", "调用SAPI更改项目附件数据失败"),
    PROJECT_BASEINFO_NOT_UPDATE("1314", "调用SAPI更改项目基础信息数据失败"),
    PROJECT_DETAIL_BASEINFO_NOT_RTRV("1315", "项目不存在!"),
    PROJECT_DETAIL_STAFF_NOT_RTRV("1316", "调用SAPI获取项目detail负责人信息时失败"),
    PROJECT_DETAIL_TAG_NOT_RTRV("1317", "调用SAPI获取项目detail标签信息时失败"),
    PROJECT_DETAIL_MODEL_NOT_RTRV("1318", "调用SAPI获取项目detail模型信息时失败"),
    PROJECT_DETAIL_CHECKLIST_NOT_RTRV("1319", "调用SAPI获取项目detail Checklist信息时失败"),
    PROJECT_DETAIL_ATTCHURL_NOT_RTRV("1320", "调用SAPI获取项目detail附件信息时失败"),
    PROJECT_DETAIL_REQMNT_NOT_RTRV("1321", "调用SAPI获取项目detail下的requirment列表信息时失败"),
    REQMNT_DOES_NOT_EXIST("1401", "Reqmnt不存在"),
    REQMNT_QUERY_MEMBER_FAIELD("1402", "查询失败"),
    REQMNT_QUERY_CHECKLIST_FAIELD("1403", "查询失败"),
    PROJECT_DETAIL_STORY_NOT_RTRV("1404", "调用SAPI获取项目detail下的STORY列表信息时失败"),
    STORY_NOT_FOUND("1501", "用户故事不存在!"),
    STORY_STAFF_NOT_CREATE("1502", "调用SAPI保存用户故事成员数据失败"),
    STORY_TAG_NOT_CREATE("1503", "调用SAPI保存用户故事标签数据失败"),
    STORY_MODEL_NOT_CREATE("1504", "调用SAPI保存用户故事模块数据失败"),
    STORY_TASK_NOT_CREATE("1505", "调用SAPI保存用户故事task数据失败"),
    STORY_ATTCHURL_NOT_CREATE("1506", "调用SAPI保存用户故事附件数据失败"),
    STORY_BASEINFO_NOT_CREATE("1507", "调用SAPI保存用户故事基础信息数据失败"),
    STORY_STAFF_NOT_UPDATE("1508", "调用SAPI更改用户故事成员数据失败"),
    STORY_TAG_NOT_UPDATE("1509", "调用SAPI更改用户故事标签数据失败"),
    STORY_MODEL_NOT_UPDATE("1510", "调用SAPI更改用户故事模块数据失败"),
    STORY_TASK_NOT_UPDATE("1511", "调用SAPI更改用户故事task数据失败"),
    STORY_TASK_STAFFDETAIL_NOT_RTRV("1512", "调用SAPI获取用户故事task中的人员详细信息时失败"),
    STORY_ATTCHURL_NOT_UPDATE("1513", "调用SAPI更改用户故事附件数据失败"),
    STORY_BASEINFO_NOT_UPDATE("1514", "调用SAPI更改用户故事基础信息数据失败"),
    STORY_DETAIL_BASEINFO_NOT_RTRV("1515", "用户故事不存在!"),
    STORY_DETAIL_STAFF_NOT_RTRV("1516", "调用SAPI获取用户故事detail成员信息时失败"),
    STORY_DETAIL_TAG_NOT_RTRV("1517", "调用SAPI获取用户故事detail标签信息时失败"),
    STORY_DETAIL_MODEL_NOT_RTRV("1518", "调用SAPI获取用户故事detail模型信息时失败"),
    STORY_DETAIL_TASK_NOT_RTRV("1519", "调用SAPI获取用户故事detail Task信息时失败"),
    STORY_DETAIL_ATTCHURL_NOT_RTRV("1520", "调用SAPI获取用户故事detail附件信息时失败"),
    STORY_DETAIL_MODEL_TASKDELIVERY_NOT_RTRV("1521", "调用SAPI获取模型下的交付件信息时失败"),
    STORY_DASHBOARD_NOT_CREATE("15022", "调用SAPI保存用户故事看板sprint数据失败"),
    TASK_SAVE_FAILED("1601", "保存task失败"),
    TASK_DOES_NOT_EXIST("1602", "task不存在"),
    DASHBOARD_DETAIL_REQMNTINFO_NOT_RTRV("1701", "调用SAPI获取看板前的reqmnt信息时失败"),
    DASHBOARD_DETAIL_STORY_NOT_RTRV("1702", "调用SAPI获取看板的storylist信息时失败"),
    DASHBOARD_NOT_CREATE("1703", "调用SAPI创建dashboard时失败"),
    DASHBOARD_NOT_UPDATE("1704", "调用SAPI更改dashboard时失败"),
    MYDASHBOARD_NOT_RTRV("1705", "调用SAPI获取个人dashboard时失败"),
    MYDASHBOARD_NOT_UPDATE("1706", "调用SAPI更改个人dashboard时失败"),
    DASHBOARD_STATUS_START_NOT_UPDATE("1707", "调用SAPI更改SprintInfo(start)时失败"),
    DASHBOARD_STATUS_CLOSE_NOT_UPDATE("1708", "调用SAPI更改SprintInfo(CLOSE)时失败"),
    DASHBOARD_NEXT_SPRINT_NOT_RTRV("1709", "调用SAPI获取接下来两个sprint时失败"),
    USER_NOT_FOUND("1800", "用户不存在!"),
    ACCOUNT_OR_PASSWORD_INCORRECT("1801", "账号或密码错误!"),
    AUTH_INFO_NOT_FOUND("1802", "权限信息不存在"),
    UPDATE_STAFF_FAIL("1900", "Fail to Update Staff Information "),
    DELETE_STAFF_FAIL("1901", "Fail to delete staff"),
    USER_NAME_EXISTS("1902", "User name already exists"),
    USER_ACCT_EXISTS("1903", "User account already exists"),
    COMMENT__NOT_CREATE("2201", "评论未创建成功"),
    COMMENT__NOT_RTRV("2202", "评论未获取成功"),
    COMMENT__LIKEORDISLIKE_FAILD("2203", "评论点赞或者踩失败");*/

    IllegalArgumentException("555", "请求参数不正确");

    String erroCode;

    String errorMsg;

    ExceptionEnum(String erroCode, String errorMsg) {
        this.erroCode = erroCode;
        errorMsg = errorMsg;
    }

    public String getErroCode() {
        return erroCode;
    }

    public void setErroCode(String erroCode) {
        this.erroCode = erroCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
