package com.example.app.api;

import com.example.app.model.local.ResponseLocal;
import com.example.app.model.login.ResponseLogin;
import com.example.app.model.login.RequestLoginDTO;
import com.example.app.model.pedido.RequestPedidoDTO;
import com.example.app.model.pedido.ResponsePedido;
import com.example.app.model.register.ResponseRegister;
import com.example.app.model.register.RequestRegisterDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @POST("/login")
    Call<ResponseLogin> login(@Body RequestLoginDTO user);

    @POST("/usuarios/registro")
    Call<ResponseRegister> registro(@Body RequestRegisterDTO user);

    @GET("/locales")
    Call<List<ResponseLocal>> locales(@Header("Authorization") String authorization,
                                      @Query("latitud") String latidud,
                                      @Query("longitud") String longitud);

    @POST("/pedido/nuevo")
    Call<ResponsePedido> pedido(@Header("Authorization") String authorization,
                                @Body RequestPedidoDTO pedido);

}
