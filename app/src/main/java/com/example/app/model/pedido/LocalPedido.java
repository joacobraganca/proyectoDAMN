package com.example.app.model.pedido;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

public class LocalPedido {
    private String id;
    private String nombre;

    public LocalPedido(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @NonNull
    @NotNull
    @Override
    public String toString() {
        return nombre;
    }
}
