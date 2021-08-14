package com.example.app.model.pedido;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponsePedido implements Serializable {

    //1 - Agregar los campos
    @SerializedName("monto")
    public Monto monto;
    @SerializedName("tiempoEntrega")
    public int tiempoEntrega;

    public ResponsePedido(Monto monto, int tiempoEntrega) {
        this.monto = monto;
        this.tiempoEntrega = tiempoEntrega;
    }

    public class Monto {
        @SerializedName("total")
        public int total;

        public Monto(int total) {
            this.total = total;
        }
    }


}
