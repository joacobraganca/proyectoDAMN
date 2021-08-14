package com.example.app.model.pedido;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RequestPedidoDTO {


    @SerializedName("email")
    public String email;
    @SerializedName("local")
    public Local local;
    @SerializedName("preparaciones")
    public List<Preparacion> prepararciones;

    public RequestPedidoDTO(String email, Local local, List<Preparacion> prepararciones) {
        this.email = email;
        this.local = local;
        this.prepararciones = prepararciones;
    }

    public static class Local {
        @SerializedName("id")
        public String id;

        public Local(String id) {
            this.id = id;
        }
    }


    public static class Preparacion {
        @SerializedName("_id")
        public String id;
        @SerializedName("cantidad")
        public int cantidad;

        public Preparacion(String id, int cantidad) {
            this.id = id;
            this.cantidad = cantidad;
        }
    }
}

