package com.mdvns.mdvn.common.util;

import com.mdvns.mdvn.common.bean.model.TerseInfo;
import com.mdvns.mdvn.common.constant.MdvnConstant;

import java.util.ArrayList;
import java.util.List;

public class ConvertObjectUtil {

    public static List<TerseInfo> convertObjectArray2BaseInfo(List<Object[]> objectList) {
        List<TerseInfo> baseInfoList = new ArrayList<>();
        for (Object[] objects : objectList) {
            TerseInfo baseInfo = new TerseInfo();
            for (int i = 0; i <objects.length ; i++) {
                if (MdvnConstant.ZERO.equals(i)) {
                    baseInfo.setId(Long.valueOf(objects[i].toString()));
                }
                baseInfo.setName(objects[i].toString());
            }
            baseInfoList.add(baseInfo);
        }
        return baseInfoList;
    }
}
