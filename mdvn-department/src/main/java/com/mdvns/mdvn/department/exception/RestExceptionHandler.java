package com.mdvns.mdvn.department.exception;


import com.mdvns.mdvn.common.exception.BusinessException;
import com.mdvns.mdvn.common.exception.ErrorEnum;
import com.mdvns.mdvn.common.util.RestResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);


    //BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> businessExceptionHandler(BusinessException ex) {
        LOG.error("异常信息:{}", ex.toString());
        return RestResponseUtil.error(ex);
    }

    //请求参数不正确：
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        LOG.error("请求参数不正确:{}", ex.getLocalizedMessage());
        return RestResponseUtil.error(ErrorEnum.ILLEGAL_ARG.getCode(), ex.getLocalizedMessage());

    }

}