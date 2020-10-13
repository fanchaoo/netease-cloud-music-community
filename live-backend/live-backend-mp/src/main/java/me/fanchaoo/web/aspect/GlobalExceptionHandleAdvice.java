package me.fanchaoo.web.aspect;

import lombok.extern.slf4j.Slf4j;
import me.fanchaoo.exception.BusinessException;
import me.fanchaoo.web.response.BaseResponse;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandleAdvice {


    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public BaseResponse handleBusinessException(BusinessException e) {
        return new BaseResponse().setRetcode(e.getCode()).setRetdesc(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        List<ObjectError> errorList = e.getBindingResult().getAllErrors();
        ObjectError firstError = errorList.get(0);
        String msg = firstError.getDefaultMessage();
        String fieldName = "";
        if (firstError instanceof FieldError) {
            fieldName = ((FieldError) firstError).getField();
        }

        return new BaseResponse().setRetcode(-1).setRetdesc(fieldName + msg);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public BaseResponse handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return new BaseResponse().setRetcode(-1).setRetdesc(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse handleOtherException(Exception e) {
        log.error("服务内部错误:" + e.getMessage(), e);
        return new BaseResponse().setRetcode(-1).setRetdesc("服务内部错误");
    }

}
