package com.example.app.model.local;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DistanciaLocal implements Serializable {

    //1 - Agregar los campos
    @SerializedName("distance_earth_radians")
    public double distanceRadians;
    @SerializedName("distance")
    public String distance;
    @SerializedName("unit")
    public String unit;

    //2 - Agregar un constructor con todos los params
    public DistanciaLocal(double distanceRadians, String distance, String unit) {
        this.distanceRadians = distanceRadians;
        this.distance = distance;
        this.unit = unit;

    }
}
