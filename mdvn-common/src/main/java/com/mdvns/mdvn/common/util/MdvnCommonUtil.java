package com.mdvns.mdvn.common.util;

import com.mdvns.mdvn.common.constant.MdvnConstant;
import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdvnCommonUtil {

    private static final Logger LOG = LoggerFactory.getLogger(MdvnCommonUtil.class);

    /**
     * 校验根据单个参数查询的数据是否不存在
     * @param object
     * @param paramName
     * @param paramValue
     * @throws BusinessException
     */
    public static void notExists(Object object,String paramName, String paramValue) throws BusinessException {
        if (null == object) {
            LOG.error(paramName+" 为:["+paramValue+"]的数据不存在.");
            throw new BusinessException(ErrorEnum.NOT_EXISTS,paramName+" 为:["+paramValue+"]的数据不存在.");
        }
    }

    /**
     * 校验属性为某个值的数据是否已存在
     * @param object
     * @param paramName
     * @param paramValue
     * @throws BusinessException
     */
    public static void exists(Object object, String paramName, String paramValue) throws BusinessException {
        if (null != object) {
            LOG.error(paramName+" 为:["+paramValue+"]的数据已存在.");
            throw new BusinessException(ErrorEnum.EXISTED,paramName+" 为:["+paramValue+"]的数据已存在.");
        }
    }

    /**
     * 对字符串进行去除所有空格操作
     * @param target
     * @return
     */
    public static String trimAllSpace(String target) {
        if (null==target || target.isEmpty()) {
            return target;
        }
        return target.replaceAll("\\s*", "");
    }
}
