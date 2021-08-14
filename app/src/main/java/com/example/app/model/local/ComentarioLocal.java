package com.example.app.model.local;

import com.example.app.model.login.UserLogin;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ComentarioLocal implements Serializable {

    @SerializedName("comentario")
    public String comentario;
    @SerializedName("usuario")
    public UserLogin usuario;

    public ComentarioLocal(String comentario, UserLogin usuario) {
        this.comentario = comentario;
        this.usuario = usuario;
    }


}
