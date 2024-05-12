package cn.anlper.train.exception;

import cn.anlper.train.resp.CommonResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public CommonResp exceptionHandler(Exception e) {
        log.error("系统异常：", e);
        return CommonResp.fail(e.getMessage());
    }

}
