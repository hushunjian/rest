package com.m2m.service;

import java.net.Proxy;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.m2m.request.GetQiniuAccessTokenRequest;
import com.m2m.response.GetQiniuAccessTokenResponse;
import com.m2m.web.Response;
import com.m2m.web.ResponseStatus;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.ProxyConfiguration;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

@Service
public class IoService {

	private static final Logger log = LoggerFactory.getLogger(IoService.class);
	
	/** 图片bucket */
    private static final String BUCKET = "ifeeling";
    /** 视频bucket */
    private static final String BUCKET_VIDEO = "m2m-video";
    /** 七牛accesskey */
    private static final String ACCESS_KEY ="1XwLbO6Bmfeqyj7goM1ewoDAFHKiQOI8HvkvkDV0";
    /** 七牛secretKey */
    private static final String SECRET_KEY ="9fmLV9tnplKRITWQV7QOQYANArqCNELd_SXtjwh9";
	
    private Auth auth;
    private UploadManager uploadManager;
    
    @Value("${proxy.host}")
    private String proxyHost;
    @Value("${proxy.port}")
    private int proxyPort;
    @Value("${proxy.status}")
    private String proxyStatus;
    
    @PostConstruct
    public void init(){
    	Configuration configuration = new Configuration(Zone.zone0());
        if (!StringUtils.isEmpty(proxyStatus) && "enabled".equals(proxyStatus)) {
            log.info("七牛采用代理proxyHost[{}],proxyPort[{}]", proxyHost, proxyPort);
            configuration.proxy = new ProxyConfiguration(proxyHost, proxyPort, null, null, Proxy.Type.HTTP);
        }

        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        uploadManager = new UploadManager(configuration);
    }
    
    /**
     * 获取七牛Token
     * @param request
     * @return
     */
	public Response<GetQiniuAccessTokenResponse> getQiniuAccessToken(GetQiniuAccessTokenRequest request){
		String bucket = BUCKET;
    	if(request.getBucket() == 1){
    		bucket = BUCKET_VIDEO;
    	}
        String token = auth.uploadToken(bucket);
        GetQiniuAccessTokenResponse result = new GetQiniuAccessTokenResponse();
        result.setToken(token);
        result.setExpireTime(60*1000*10);
        return Response.success(ResponseStatus.GET_QINIU_TOKEN_SUCCESS.status,ResponseStatus.GET_QINIU_TOKEN_SUCCESS.message,result);
	}
	
	/**
	 * 上传图片到七牛
	 * @param data
	 * @param key
	 * @return
	 */
    public String upload(byte[] data, String key){
        this.upload(data, key, 0);//默认到图片库
        return  null;
    }
    
    /**
     * 上传资源到七牛
     * @param data
     * @param key
     * @param type	0图片，1视频
     */
    public void upload(byte[] data, String key, int type){
    	String bucket = BUCKET;
    	if(type == 1){
    		bucket = BUCKET_VIDEO;
    	}
    	//上传到七牛后保存的文件名
        String token = auth.uploadToken(bucket);
        try {
        	uploadManager.put(data,key,token);
        } catch (QiniuException e) {
        	log.error("上传七牛失败", e);
        }
    }
}
