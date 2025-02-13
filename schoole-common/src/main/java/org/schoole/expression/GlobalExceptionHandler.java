package org.schoole.expression;

import org.schoole.domain.ResponseResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * @RequestBody 上校验失败后抛出的异常是 MethodArgumentNotValidException 异常。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        String messages = bindingResult.getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(";"));
        System.out.println("全局异常"+messages);
//        String messages = bindingResult.getAllErrors()
//                .stream()
//                .map(ObjectError::getDefaultMessage)
//                .collect(Collectors.joining("；"));
        Map<String,Object> data = new HashMap<>() ;
        data.put("msg" , messages) ;
        return new ResponseResult(400 , messages , data);
    }
    /**
     * 不加 @RequestBody注解，校验失败抛出的则是 BindException
     */
//    @ExceptionHandler(value = BindException.class)
//    public String exceptionHandler(BindException e){
//        String messages = e.getBindingResult().getAllErrors()
//                .stream()
//                .map(ObjectError::getDefaultMessage)
//                .collect(Collectors.joining("；"));
//        return messages;
//    }

    /**
     *  @RequestParam 上校验失败后抛出的异常是 ConstraintViolationException
     */
    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseResult methodArgumentNotValid(ConstraintViolationException exception) {
        String message = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("；"));
        Map<String,Object> data = new HashMap<>()  ;
        data.put("msg",message);
        return new ResponseResult(400,message ,data );
    }
}
