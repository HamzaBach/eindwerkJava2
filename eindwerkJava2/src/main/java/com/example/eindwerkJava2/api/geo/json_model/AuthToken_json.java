package com.example.eindwerkJava2.api.geo.json_model;

public class AuthToken_json {
    private String auth_token;

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = "Bearer "+auth_token;
    }
}
