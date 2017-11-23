package com.mdvns.mdvn.common.util;

import com.mdvns.mdvn.common.bean.RestResponse;
import com.mdvns.mdvn.common.exception.BusinessException;
import org.springframework.http.ResponseEntity;


public class RestResponseUtil {

    /**
     * 无参 请求成功response
     * @return
     */
    public static ResponseEntity<?> success() {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode("000");
        restResponse.setMsg("SUCCESS");

        return ResponseEntity.ok(restResponse);
    }

    public static ResponseEntity<?> success(Object data) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode("000");
        restResponse.setMsg("SUCCESS");
        restResponse.setData(data);
        return ResponseEntity.ok(restResponse);
    }

    public static RestResponse<?> success2(Object data) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode("000");
        restResponse.setMsg("SUCCESS");
        restResponse.setData(data);
        return restResponse;
    }

    public static ResponseEntity<?> error(String code, String msg) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(code);
        restResponse.setMsg(msg);

        return ResponseEntity.ok(restResponse);
    }

    public static ResponseEntity<?> error(BusinessException be) {
        RestResponse restResponse = new RestResponse();
        restResponse.setCode(be.getCode());
        restResponse.setMsg(be.getMsg());
        return ResponseEntity.ok(restResponse);
    }

   /* @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class RestResponse<T>{
        *//*response code*//*
        private String code;
        *//*response message*//*
        private String msg;
        *//*response body*//*
        private T data;
    }*/

}
