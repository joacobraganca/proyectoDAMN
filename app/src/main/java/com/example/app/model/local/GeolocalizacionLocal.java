package com.example.app.model.local;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GeolocalizacionLocal implements Serializable {

    //1 - Agregar los campos
    @SerializedName("latitud")
    public String latitud;
    @SerializedName("longitud")
    public String longitud;


    //2 - Agregar un constructor con todos los params
    public GeolocalizacionLocal(String latitud, String longitud) {
        this.latitud = latitud;
        this.longitud = longitud;

    }
}
