package com.lot.iotsite.config;

import com.lot.iotsite.utils.Result;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResultBodyConfig implements ResponseBodyAdvice<Object>
{
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
      return !methodParameter.getMethod().getReturnType().isAssignableFrom(Void.TYPE)
              &&!methodParameter.getMethod().getReturnType().isAssignableFrom(String.class)
              &&!methodParameter.getMethod().getReturnType().isAssignableFrom(Result.class);
              //&&!methodParameter.getDeclaringClass().getPackage().getName().contains("com.");
    }

    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
       if(o instanceof Result){
           return o;
       }
       return Result.success(o);
    }
}
