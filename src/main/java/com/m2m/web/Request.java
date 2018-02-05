package com.m2m.web;

import lombok.Getter;
import lombok.Setter;

public abstract class Request {

    @Getter
    @Setter
    private long uid;
    @Getter
    @Setter
    private String token;
    @Getter
    @Setter
    private String version;
    @Getter
    @Setter
    private String appId;
    @Getter
    @Setter
    private String secretKey;
    @Getter
    @Setter
    private String sign;
    @Getter
    @Setter
    private long currentTime;
    @Getter
    @Setter
    private String nonce;
    @Getter
    @Setter
    private String channel;


}
