package org.goskyer.exception;

import org.apache.log4j.Logger;
import org.goskyer.common.BaseResponse;
import org.goskyer.common.MsgCode;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

/**
 * @Description: 统一异常处理
 * @author: mia.he
 * @date: 2017年11月24日 下午3:56:43
 * @version: V1.0
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = Logger.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Throwable.class)
    public @ResponseBody
    BaseResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        logger.error("request error:", e);
        if (e instanceof MissingServletRequestParameterException) {
            return BaseResponse.result(MsgCode.BAD_PARAM, getRequiredFieldMsg(((MissingServletRequestParameterException) e).getParameterName()));
        } else if (e instanceof ConstraintViolationException) {
            return BaseResponse.result(MsgCode.BAD_PARAM, ((ConstraintViolationException) e).getConstraintViolations().stream()
                    .map(ConstraintViolation::getMessageTemplate)
                    .findFirst()
                    .orElse(e.getMessage()));
        } else if (e instanceof MethodArgumentNotValidException) {
            BindingResult result = ((MethodArgumentNotValidException) e).getBindingResult();
            FieldError error = result.getFieldError();
            String message = String.format("%s:%s", error.getField(), error.getCode());
            return BaseResponse.result(MsgCode.BAD_PARAM, message);
        } else if (e instanceof CommonException) {
            return BaseResponse.result(((CommonException) e).getErrorCode(), ((CommonException) e).getErrorMessage());
        } else if (e instanceof AccessDeniedException) {
            return BaseResponse.result(MsgCode.FORBIDDEN);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            return BaseResponse.result(MsgCode.METHOD_NOT_ALLOW);
        } else if (e instanceof IllegalStateException) {
            return BaseResponse.result(MsgCode.ILLEGAL_ERROR, e.getMessage());
        }
        return BaseResponse.result(MsgCode.SYSTEM_ERROR);
    }

    private String getRequiredFieldMsg(String fieldName) {
        return "参数：【" + fieldName + "】不允许为空";
    }
}
