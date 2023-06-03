package com.bistu.why.controller;

import com.alibaba.fastjson.JSONObject;
import com.bistu.why.common.result.RRException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

/**
 * @author why
 */
public class BaseController {
    public  void extractedExceptionMethod(BindingResult bindingResult) {
        HashMap<String, String> errorMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> errorList = bindingResult.getFieldErrors();
            for (FieldError fieldError : errorList) {
                errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            throw new RRException(JSONObject.toJSONString(errorMap));
        }
    }
}
