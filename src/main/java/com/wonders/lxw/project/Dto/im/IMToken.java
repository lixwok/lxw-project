package com.wonders.lxw.project.dto.im;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by lixuanwu on 15/10/14.
 */
public class IMToken {

    private String access_token;

    private long expires_in;

    private long expiresAt;

    private String application;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public long getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(long expiresAt) {
        this.expiresAt = expiresAt;
    }

    @JsonIgnore
    public boolean isExpire() {
        return System.currentTimeMillis() > expiresAt;
    }

}
