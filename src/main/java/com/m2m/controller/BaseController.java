package com.m2m.controller;

import com.m2m.exception.SendMessageLimitException;
import com.m2m.exception.SendMessageTimeException;
import com.m2m.exception.TokenNullException;
import com.m2m.exception.UidAndTokenNotMatchException;
import com.m2m.exception.UserGagException;
import com.m2m.web.Response;
import com.m2m.web.ResponseStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @SuppressWarnings("rawtypes")
	@ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response init(RuntimeException e){
        //短信的相关返回
        if(e instanceof SendMessageLimitException || e instanceof SendMessageTimeException){
            return Response.success(20094,e.getMessage());
        }
        //禁言返回
        if(e instanceof UserGagException){
        	return Response.failure(ResponseStatus.USER_IS_GAGGED.status, ResponseStatus.USER_IS_GAGGED.message);
        }
        //uid和token的不匹配的错误堆栈就不要打印了
        if(e instanceof TokenNullException || e instanceof UidAndTokenNotMatchException){
        	logger.error(e.getMessage());
        }else{
        	logger.error("系统异常", e);
        }
        
        return Response.failure(e.getMessage());
    }

}
