package com.example.app.model.local;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PuntuacionLocal implements Serializable {


    @SerializedName("_id")
    public String id;
    @SerializedName("numero")
    public int numero;
    @SerializedName("usuario")
    public String usuario;

    public PuntuacionLocal(String id, int numero, String usuario) {
        this.id = id;
        this.numero = numero;
        this.usuario = usuario;
    }


}
