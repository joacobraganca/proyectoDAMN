package com.example.app.model.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseLogin implements Serializable {

    @SerializedName("doc")
    public UserLogin doc;
    @SerializedName("token")
    public String token;

    public ResponseLogin(UserLogin doc, String token) {
        this.doc = doc;
        this.token = token;
    }

}
