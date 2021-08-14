package com.example.app.model.local;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class HorarioLocal implements Serializable {

    //1 - Agregar los campos
    @SerializedName("abre")
    public int abre;
    @SerializedName("cierra")
    public int cierra;


    //2 - Agregar un constructor con todos los params
    public HorarioLocal(int abre, int cierra) {
        this.abre = abre;
        this.cierra = cierra;
    }
}
