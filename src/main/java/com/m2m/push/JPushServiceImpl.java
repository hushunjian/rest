package com.m2m.push;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.connection.HttpProxy;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class JPushServiceImpl implements ClientPushService {

    private JPushClient jPushClient;

    private static final int DEFAULT_LIVE_TIME = 86400 * 10;
    private static final String masterSecret = "467e198daf63ff0596b0784d";
    private static final String appKey = "9222161c4591256016b4efee";

    @Value("${app.push.IOS_PUSH_ENV}")
    private String env;
    @Value("${proxy.host}")
    private String proxyHost;
    @Value("${proxy.port}")
    private int proxyPort;
    @Value("${proxy.status}")
    private String proxyStatus;

    /**
     * 初始化JPushclient
     */
    @PostConstruct
    public void init(){
        ClientConfig config = ClientConfig.getInstance();
        config.setTimeToLive(DEFAULT_LIVE_TIME);
        HttpProxy proxy = null;
        if (!StringUtils.isEmpty(proxyStatus) && "enabled".equals(proxyStatus)) {
        	proxy = new HttpProxy(proxyHost, proxyPort);
        }
        
        this.jPushClient = new JPushClient(masterSecret, appKey, proxy, config);
    }

    @Override
    public void payloadByIdExtra(String uid,String message,Map<String,String> extraMaps) {
    	log.info("push[to:"+uid+", msg:"+message+"] start...");
        //默认为false开发环境，true为生产环境
        Options options;
        if("product".equals(env)) {
            options = Options.newBuilder().setApnsProduction(true).build();
        }else{
            options = Options.newBuilder().setApnsProduction(false).build();
        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(uid))
                .setNotification(Notification.newBuilder()
                		.setAlert(message)
                        // android 平台
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .addExtras(extraMaps).build())
                        // ios 平台
                        .addPlatformNotification(IosNotification.newBuilder()
                                .incrBadge(1)
                                .addExtras(extraMaps).build())
                        .build()).setOptions(options)
                .build();
        try {
            jPushClient.sendPush(payload);
        } catch (APIConnectionException e) {
        	log.error(e.getMessage());
        } catch (APIRequestException e) {
        	log.error(e.getMessage());
        }
    }

	@Override
	public void payloadByIdForMessage(String uid,String message){
		Message platformMessage = Message.content(message);
        PushPayload payload = PushPayload
                .newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(uid))
                .setMessage(platformMessage)
                .build();
        try {
            jPushClient.sendPush(payload);
        } catch (APIConnectionException e) {
        	log.error(e.getMessage());
        } catch (APIRequestException e) {
        	log.error(e.getMessage());
        }
	}
}
