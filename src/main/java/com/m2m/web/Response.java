package com.m2m.web;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

import com.m2m.response.BaseResponse;
import com.m2m.util.SecurityUtil;

/**
 * 该接口是一个泛型类接口
 * 该接口必须实现序列化接口的实体类作为泛型实际参数
 * @param <T>
 */
public class Response<T extends Serializable> implements Serializable  {

	private static final long serialVersionUID = -8524609179308474508L;

	private static final String DEFAULT_MESSAGE_SUCCESS = "ok";

    private static final String DEFAULT_MESSAGE_FAILURE = "failure";

    private static final int DEFAULT_CODE_SUCCESS = 200;

    private static final int DEFAULT_CODE_FAILURE = 500;

    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private int code;
    @Getter
    @Setter
    private T data;
    @Getter
    @Setter
    private String accessToken;


    public Response(int code,String message,T data){
        this(code,message);
        this.data = data;
    }

    public Response(int code,String message){
        this.code = code;
        this.message = message;
    }


    /**
     * 系统默认成功
     * @param data
     * @return
     */
    public static <T extends BaseResponse> Response<T> success(T data){
        Response<T> response =  new Response<T>(DEFAULT_CODE_SUCCESS,DEFAULT_MESSAGE_SUCCESS,data);
        response.refreshAccessToken();
        return response;
    }

    /**
     * 系统默认成功
     * @return
     */
    public static <T extends BaseResponse> Response<T> success(){
        Response<T> response =  new Response<T>(DEFAULT_CODE_SUCCESS,DEFAULT_MESSAGE_SUCCESS);
        response.refreshAccessToken();
        return response;
    }

    /**
     * 系统默认失败 ,失败并设置data.
     * @param data 设置data.
     * @return
     */
    public static Response<String> failure(String data){
        Response<String> response =  new Response<String>(DEFAULT_CODE_FAILURE,DEFAULT_MESSAGE_FAILURE,data);
        response.refreshAccessToken();
        return response;
    }

    /**
     * 系统默认失败
     * @param message
     * @return
     */
    public static <T extends BaseResponse> Response<T> failure(int code,String message){
        Response<T> response =  new Response<T>(code,message);
        response.refreshAccessToken();
        return response;
    }

    /**
     * 请求成功
     * @param code
     * @param message
     * @return
     */
    public static <T extends BaseResponse> Response<T> success(int code,String message){
        Response<T> response =  new Response<T>(code,message);
        response.refreshAccessToken();
        return response;
    }

    /**
     * 用户自定义成功
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static <T extends BaseResponse> Response<T> success(int code,String message,T data){
        Response<T> response =  new Response<T>(code,message,data);
        response.refreshAccessToken();
        return response;
    }

    /**
     * 用户自定义失败
     * @param code
     * @param message
     * @param data
     * @return
     */
    public static <T extends BaseResponse> Response<T> failure(int code,String message,T data){

        Response<T> response =  new Response<T>(code,message,data);
        response.refreshAccessToken();
        return response;
    }

    public void refreshAccessToken(){
        this.accessToken = SecurityUtil.getAccessToken();
    }


}
