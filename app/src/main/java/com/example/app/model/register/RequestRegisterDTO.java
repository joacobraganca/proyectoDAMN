package com.example.app.model.register;

import com.google.gson.annotations.SerializedName;

public class RequestRegisterDTO {

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
    @SerializedName("activo")
    public String activo;


    public RequestRegisterDTO(String nombre, String apellido, String email, int telefono, String contrasenia, String documento, String activo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.contrasenia = contrasenia;
        this.documento = documento;
        this.activo = activo;
    }
}
