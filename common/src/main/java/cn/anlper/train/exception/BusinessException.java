package cn.anlper.train.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BusinessException extends RuntimeException{
    private BusinessExceptionEnum businessExceptionEnum;
}
