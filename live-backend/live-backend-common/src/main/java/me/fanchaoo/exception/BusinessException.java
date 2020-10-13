package me.fanchaoo.exception;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
        this.code = -1;
    }

}