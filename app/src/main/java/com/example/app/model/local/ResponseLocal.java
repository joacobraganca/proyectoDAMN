package com.example.app.model.local;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseLocal implements Serializable {


    //1 - Agregar los campos
    @SerializedName("id")
    public String id;
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("direccion")
    public String direccion;
    @SerializedName("geolocalizacion")
    public GeolocalizacionLocal geolocalizacion;
    @SerializedName("puntuaciones")
    public List<PuntuacionLocal> puntuaciones;
    @SerializedName("comentarios")
    public List<ComentarioLocal> comentarios;
    @SerializedName("tiempoEntrega")
    public int tiempoEntrega;
    @SerializedName("costoEnvio")
    public int costoEnvio;
    @SerializedName("tags")
    public List<String> tags;
    @SerializedName("horario")
    public HorarioLocal horario;
    @SerializedName("distancia")
    public DistanciaLocal distancia;

    //2 - Agregar un constructor con todos los params
    public ResponseLocal(String id, String nombre, String direccion, GeolocalizacionLocal geolocalizacion, List<PuntuacionLocal> puntuaciones, List<ComentarioLocal> comentarios, int tiempoEntrega, int costoEnvio, List<String> tags, HorarioLocal horario, DistanciaLocal distancia) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.geolocalizacion = geolocalizacion;
        this.puntuaciones = puntuaciones;
        this.comentarios = comentarios;
        this.tiempoEntrega = tiempoEntrega;
        this.costoEnvio = costoEnvio;
        this.tags = tags;
        this.horario = horario;
        this.distancia = distancia;

    }


}
