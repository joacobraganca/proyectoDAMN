package com.example.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app.api.APIClient;
import com.example.app.api.APIInterface;
import com.example.app.model.local.ResponseLocal;
import com.example.app.model.login.ResponseLogin;
import com.example.app.model.pedido.LocalPedido;
import com.example.app.model.pedido.PreparacionPedido;
import com.example.app.model.pedido.RequestPedidoDTO;
import com.example.app.model.pedido.ResponsePedido;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PedidoActivity extends AppCompatActivity {

    private ResponseLogin loggedUserData = null;

    private UserViewModel mUserViewModel;

    APIInterface apiInterface;
    Context ctx;

    Spinner spinnerLocales;
    Spinner spinnerPreparaciones;
    EditText txtCantidad;

    String emailUsuario="";
    String tokenUsuario="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);
        ctx = this.getApplicationContext();
        loggedUserData  = (ResponseLogin) getIntent().getSerializableExtra("USER");
        setContentView(R.layout.activity_pedido);
        spinnerLocales = findViewById(R.id.slcLocal);
        spinnerPreparaciones = findViewById(R.id.slcPreparacion);
        txtCantidad = findViewById(R.id.txtCantidad);

        // Hardcodeo latitud y longitud del usuario
        // No se pudo obtener de forma dinámica.
        String latitud = "1";
        String longitud = "2";

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

       // AtomicReference<String> token= new AtomicReference<>("");

        mUserViewModel.getUser().observe(this, user -> {
           // token.set(user.getToken());
           tokenUsuario= "Bearer " + user.getToken();
            emailUsuario = user.getEmail();
            try {
                Call<List<ResponseLocal>> callAuthResponse = apiInterface.locales(tokenUsuario, latitud, longitud);
                callAuthResponse.enqueue(new Callback<List<ResponseLocal>>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<List<ResponseLocal>> call, Response<List<ResponseLocal>> response) {

                        // Si pudo obtener los locales
                        //  Guardo los datos que necesito de cada local en una lista nueva.
                        if (response.code() == 200) {
                            Log.d("GET_LOCALES", "Obtengo Locales");
                            List<LocalPedido> locales = new ArrayList<>();
                            response.body().forEach(localResult -> {
                                LocalPedido local = new LocalPedido(localResult.id, localResult.nombre);
                                locales.add(local);
                            });

                            // Cargo los locales en el select
                            loadLocales(locales);
                            // Cargo las preparaciones en el select
                            loadPreparaciones();

                        } else {
                            Log.d("GET_LOCALES", "Error: " + response.code());
                            Toast.makeText(ctx, "Error al obtener locales.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ResponseLocal>> call, Throwable t) {
                        Log.d("GET_LOCALES", "Error: " + t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.d("GET_LOCALES", "Error.");
            }
        });




    }


    private void loadLocales(List<LocalPedido> list) {
        // Cargo los locales obtenidos en el selector correspondiente.
        ArrayAdapter<LocalPedido> adapter = new ArrayAdapter<LocalPedido>(this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerLocales.setAdapter(adapter);
    }

    private void loadPreparaciones() {

        // Hardcodeo preparaciones ya que no tengo endpoint para obtenerlo.
        List<PreparacionPedido> preparaciones = new ArrayList<>();
        PreparacionPedido prepUno = new PreparacionPedido("60ca58020115feaa4bcf68f9", "Polenta");
        preparaciones.add(prepUno);

        PreparacionPedido prepDos = new PreparacionPedido("60ca5d7f0115feaa4bcf68fa", "Burger Clásica");
        preparaciones.add(prepDos);

        // Cargo las preparaciones en el selector correspondiente.
        ArrayAdapter<PreparacionPedido> adapter = new ArrayAdapter<PreparacionPedido>(this, android.R.layout.simple_spinner_item, preparaciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerPreparaciones.setAdapter(adapter);
    }


    public void doPedido(View view) {
        // Obtengo los datos ingresados por el usuario.
        LocalPedido localPedido = (LocalPedido) spinnerLocales.getSelectedItem();
        PreparacionPedido preparacionPedido = (PreparacionPedido) spinnerPreparaciones.getSelectedItem();
        String cant = txtCantidad.getText().toString();

        // Si no está vacio, entro
        if (!cant.equals("")) {
            int cantidad = Integer.parseInt(cant);

            // Genero los objetos necesarios para poder hacer el POST.
            RequestPedidoDTO.Local local = new RequestPedidoDTO.Local(localPedido.getId());
            List<RequestPedidoDTO.Preparacion> preps = new ArrayList<>();
            RequestPedidoDTO.Preparacion preparacion = new RequestPedidoDTO.Preparacion(preparacionPedido.getId(), cantidad);
            preps.add(preparacion);

            RequestPedidoDTO dto = new RequestPedidoDTO(emailUsuario, local, preps);

            try {
                Call<ResponsePedido> callAuthResponse = apiInterface.pedido(tokenUsuario, dto);
                callAuthResponse.enqueue(new Callback<ResponsePedido>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(Call<ResponsePedido> call, Response<ResponsePedido> response) {

                        // Si pude registrar el pedido muestro un toast con el precio.
                        if (response.code() == 200) {
                            Toast.makeText(ctx, "Precio: " + response.body().monto.total, Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("POST_PEDIDO", "Error: " + response.code());
                            Toast.makeText(ctx, "Error al ingresar pedido.", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponsePedido> call, Throwable t) {
                        Log.d("POST_PEDIDO", "Error: " + t.getMessage());
                    }
                });
            } catch (Exception e) {
                Log.d("POST_PEDIDO", "Error");
            }
        } else {
            Toast.makeText(ctx, "Ingrese la cantidad.", Toast.LENGTH_LONG).show();
        }

    }
}