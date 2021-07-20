package com.demo.intercept;


import com.demo.entity.Result;
import com.demo.entity.StatusCode;
import com.demo.entity.exception.BizException;
import com.demo.entity.exception.BusinessException;
import com.demo.entity.exception.CommonEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author dzy
 * @date 2021/5/12
 * @desc 全局异常监听器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 通用业务异常
     *
     * @param e
     * @return string
     */
    @ExceptionHandler(BusinessException.class)
    public String businessException(BusinessException e) {
        return Result.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理自定义的业务异常
     *
     * @param e
     * @return string
     */
    @ExceptionHandler(value = BizException.class)
    public String bizExceptionHandler(BizException e) {
        logger.error("发生业务异常！原因是：{}", e.getErrorMsg());
        return Result.error(String.valueOf(StatusCode.ERROR), e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     *
     * @param e
     * @return string
     */
    @ExceptionHandler(value = NullPointerException.class)
    public String exceptionHandler(NullPointerException e) {
        logger.error("发生空指针异常！原因是:", e);
        return Result.error(CommonEnum.BODY_NOT_MATCH);
    }

    /**
     * 处理参数校验的异常
     *
     * @param e
     * @return string
     */
    @ExceptionHandler(value = BindException.class)
    public String bindExceptionExceptionHandler(BindException e) {
        StringBuffer str = new StringBuffer();
        List<ObjectError> list = e.getBindingResult().getAllErrors();
        for (int i = 0; i < list.size(); i++) {
            str.append(list.get(i).getDefaultMessage());
            if (i < list.size() - 1) {
                str.append(",");
            }
        }
        logger.error("发生参数校验异常," + str);
        return Result.result(StatusCode.ERROR, str.toString());
    }

    /**
     * 处理其他异常
     *
     * @param e
     * @return string
     */
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(Exception e) {
        logger.error("未知异常！原因是:", e);
        return Result.error(CommonEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理请求方法不支持异常
     *
     * @param e
     * @return string
     */
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public String methodNotSupportedHandler(Exception e) {
        logger.error("methodNotSupported异常！原因是:", e);
        return Result.error(CommonEnum.Method_Not_Supported);
    }

    /**
     * @param @param  exception
     * @param @param  response
     * @param @return
     * @return String
     * @Title: messageNotReadable
     * @Description: @RequestBody中参数校验失败，捕捉异常
     * @author lrj
     * @Date 2020年10月20日下午2:01:57
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String messageNotReadable(MethodArgumentNotValidException exception) {
        logger.error("参数不合法！原因是:", exception);
        BindingResult bindingResult = exception.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        int size = fieldErrors.size();
        StringBuilder errMsg = new StringBuilder(size * 16);
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                errMsg.append(",");
            }
            FieldError fieldError = fieldErrors.get(i);
            errMsg.append(fieldError.getField()).append(":").append(fieldError.getDefaultMessage());
        }
        return Result.error(CommonEnum.MethodArgument_Not_Valid.getResultCode(), errMsg.toString());
    }

}
