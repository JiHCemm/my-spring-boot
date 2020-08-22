package com.my_springboot.handler;

import com.my_springboot.util.Result;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author JiHC
 * @date 2020/7/31
 * @description: 控制器的异常处理类
 */
@ControllerAdvice
public class ErrorHandler {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ErrorHandler.class);

    /**
     * 输入参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Result> NotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("异常详情: ", e);
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors() == false) {
            return null;
        }
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getCode() + "|" + error.getDefaultMessage());
        }
        return new ResponseEntity<>(Result.error(422, "不可处理实体", fieldErrors),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * 404异常处理
     */
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ResponseEntity<Result> NoHandlerFoundExceptionHandler(Exception e) {
        log.error("异常详情: ", e);
        return new ResponseEntity<>(Result.error(404, "未找到"), HttpStatus.NOT_FOUND);
    }

    /**
     * 默认异常处理，前面未处理
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Result> defaultHandler(Exception e) {
        log.error ("异常详情: ", e);
        return new ResponseEntity<>(Result.error(500, "内部服务器错误:" + e),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
