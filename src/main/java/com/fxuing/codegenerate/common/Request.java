package com.fxuing.codegenerate.common;

import lombok.Data;

/**
 * @Author: Hou_fx
 * @Date: 2020.8.14 11:41
 * @Description:
 */
public class Request<T>{
    private T body;
    private Header header;

    public Header getHeader() { return header; }
    public void setHeader(Header header) { this.header = header; }

    public T getBody() {
        return body;
    }
    public void setBody(T body) { this.body = body; }

    @Data
    static class Header {
        private String appId;
        private String format;
        private String timeStamp;
        private String partnerId;
        private String signMethod;
        private String deviceType;
        private String channelCode;
        private String token;
        private String diviceId;
        private String referer;
        private String sign;
    }
}
