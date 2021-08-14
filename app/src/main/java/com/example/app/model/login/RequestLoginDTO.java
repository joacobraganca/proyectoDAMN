package com.example.app.model.login;

import com.google.gson.annotations.SerializedName;

public class RequestLoginDTO {

    @SerializedName("email")
    public String email;
    @SerializedName("contrasenia")
    public String contrasenia;

    public RequestLoginDTO(String email, String contrasenia) {
        this.email = email;
        this.contrasenia = contrasenia;
    }
}
