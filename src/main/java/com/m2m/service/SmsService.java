package com.m2m.service;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.m2m.entity.ImUserInfoDto;
import com.m2m.util.IMHttpUtil;

@Service
public class SmsService {

	private static final Logger log = LoggerFactory.getLogger(SmsService.class);
	
	private static final String UTF_8 = "UTF-8";
	
	@Value("${im.app.key}")
    private String imAppKey;
    @Value("${im.app.secret}")
    private String imAppSecret;
	
	/**
     * Im 获取调用远程接口获取token
     * @param userId
     * @param name
     * @param portraitUri
     * @return
     * @throws Exception
     */
    public ImUserInfoDto getIMUsertoken(long userId, String name, String portraitUri) throws Exception {
        if (userId == 0) {
        	log.debug("Paramer 'userId' is required");
            throw new IllegalArgumentException("Paramer 'userId' is required");
        }
        if (name == null) {
        	log.debug("Paramer 'name' is required");
            throw new IllegalArgumentException("Paramer 'name' is required");
        }
        if (portraitUri == null) {
        	log.debug("Paramer 'portraitUri' is required");
            throw new IllegalArgumentException("Paramer 'portraitUri' is required");
        }

        StringBuilder sb = new StringBuilder();
        sb.append("&userId=").append(URLEncoder.encode(String.valueOf(userId), "UTF-8"));
        sb.append("&name=").append(URLEncoder.encode(name.toString(), "UTF-8"));
        sb.append("&portraitUri=").append(URLEncoder.encode(portraitUri.toString(), "UTF-8"));
        String body = sb.toString();
        if (body.indexOf("&") == 0) {
            body = body.substring(1, body.length());
        }

        HttpURLConnection conn = IMHttpUtil.createPostHttpConnection(IMHttpUtil.URL_API, imAppKey, imAppSecret, "/user/getToken.json", "application/x-www-form-urlencoded");
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.write(body.getBytes(UTF_8));
        ImUserInfoDto result = JSON.parseObject(IMHttpUtil.getResult(conn), ImUserInfoDto.class);

        return result;
    }
}
