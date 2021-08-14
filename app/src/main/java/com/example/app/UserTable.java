package com.example.app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserTable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "_id")
    private String mId;

    @NonNull
    @ColumnInfo(name = "token")
    private String mToken;

    @NonNull
    @ColumnInfo(name = "nombre")
    private String mNombre;

    @NonNull
    @ColumnInfo(name = "apellido")
    private String mApellido;

    @NonNull
    @ColumnInfo(name = "email")
    private String mEmail;

    public UserTable(String id, String token, String nombre, String apellido, String email) {
        this.mId = id;
        this.mToken = token;
        this.mNombre = nombre;
        this.mApellido = apellido;
        this.mEmail = email;
    }

    @NonNull
    public String getId() {
        return this.mId;
    }

    @NonNull
    public String getToken() {
        return this.mToken;
    }

    @NonNull
    public String getNombre() {
        return this.mNombre;
    }

    @NonNull
    public String getApellido() {
        return this.mApellido;
    }

    @NonNull
    public String getEmail() {
        return this.mEmail;
    }


}