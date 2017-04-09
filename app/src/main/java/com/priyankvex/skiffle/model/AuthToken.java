package com.priyankvex.skiffle.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by @priyankvex on 26/3/17.
 */

public class AuthToken {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}
