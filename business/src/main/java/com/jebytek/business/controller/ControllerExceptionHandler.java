package com.jebytek.business.controller;

import com.jebytek.server.dto.ResponseDto;
import com.jebytek.server.exception.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerExceptionHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(value = ValidatorException.class)
    @ResponseBody
    public ResponseDto validatorExceptionHandler(ValidatorException e) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setSuccess(false);
        LOG.warn(e.getMessage());
        responseDto.setMessage("Error!");
        return responseDto;
    }
}
