package com.mdvns.mdvn.project.exception;


import com.mdvns.mdvn.common.util.RestResponseUtil;
import com.mdvns.mdvn.project.domain.commons.enums.ExceptionEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class RestExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RestExceptionHandler.class);

    //SAPI调用异常
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> businessExceptionHandler(BusinessException ex) {
        LOG.error("Internal Server Error:{}", ex.getMessage());
        return RestResponseUtil.error(ex.getCode(), ex.getLocalizedMessage());
    }

    //请求参数不正确：
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        LOG.error("请求参数不正确:{}", ex.getLocalizedMessage());
        return RestResponseUtil.error(ExceptionEnum.IllegalArgumentException.getErroCode(), ex.getLocalizedMessage());

    }

}