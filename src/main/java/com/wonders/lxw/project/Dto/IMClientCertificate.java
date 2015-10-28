package com.wonders.lxw.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by lixuanwu on 15/10/26.
 */
public class IMClientCertificate {

    /**
     * grant_type : client_credentials
     * client_id : app的client_id
     * client_secret : app的client_secret
     */
    @JsonProperty("grant_type")
    private String grantType = "client_credentials";

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("client_secret")
    private String clientSecret;

    public IMClientCertificate(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
