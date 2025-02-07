package com.example.oauth.response;

public class LoginResponse {

    private String accessToken;
    private String referesghToken;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LoginResponse() {

    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", referesghToken='" + referesghToken + '\'' +
                '}';
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getReferesghToken() {
        return referesghToken;
    }

    public void setReferesghToken(String referesghToken) {
        this.referesghToken = referesghToken;
    }

    public LoginResponse(String accessToken, String referesghToken) {
        this.accessToken = accessToken;
        this.referesghToken = referesghToken;
    }
}
