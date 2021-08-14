package com.example.app.model.register;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseRegister implements Serializable {

    //1 - Agregar los campos
    @SerializedName("_id")
    public String id;
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("apellido")
    public String apellido;
    @SerializedName("email")
    public String email;
    @SerializedName("telefono")
    public int telefono;
    @SerializedName("contrasenia")
    public String contrasenia;
    @SerializedName("documento")
    public String documento;

    //2 - Agregar un constructor con todos los params
    public ResponseRegister(String id, String nombre, String apellido, String email, int telefono, String contrasenia, String documento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.documento = documento;
    }
}
