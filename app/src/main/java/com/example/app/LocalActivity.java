package com.example.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.app.api.APIClient;
import com.example.app.api.APIInterface;
import com.example.app.model.local.ResponseLocal;
import com.example.app.model.login.ResponseLogin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocalActivity extends AppCompatActivity {

    private ResponseLogin loggedUserData = null;

    private UserViewModel mUserViewModel;

    APIInterface apiInterface;
    Context ctx;

    // Elementos Vista
    EditText txtLatitud;
    EditText txtLongitud;
    ExpandableListView lstLocales;

    // Para cargar la lista expandible
    ArrayList<String> listGroup = new ArrayList<>();
    HashMap<String, ArrayList<String>> listChild = new HashMap<>();
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);
        ctx = this.getApplicationContext();
        // Guardo datos del usuario
        loggedUserData = (ResponseLogin) getIntent().getSerializableExtra("USER");
        setContentView(R.layout.activity_local);
        txtLatitud = findViewById(R.id.txtLatitud);
        txtLongitud = findViewById(R.id.txtLongitud);
        lstLocales = findViewById(R.id.lstLocales);
    }


    // Obtengo los locales
    public void getLocales(View view) {
        // Latitud y longitud ingresada por el usuario
        String latitud = txtLatitud.getText().toString();
        String longitud = txtLongitud.getText().toString();

        if (!latitud.equals("") && !longitud.equals("")) {
            // Token devuelta en el login

            mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);

            // AtomicReference<String> token= new AtomicReference<>("");
           mUserViewModel.getUser().observe(this, user -> {
             // token.set(user.getToken());
               String authotization = "Bearer " + user.getToken();

               try {
                   Call<List<ResponseLocal>> callAuthResponse = apiInterface.locales(authotization, latitud, longitud);
                   callAuthResponse.enqueue(new Callback<List<ResponseLocal>>() {
                       @Override
                       public void onResponse(Call<List<ResponseLocal>> call, Response<List<ResponseLocal>> response) {
                           // Si obtengo los locales genero la lista expandible
                           if (response.code() == 200) {
                               listGroup = new ArrayList<>();
                               listChild = new HashMap<>();
                               Log.d("GET_LOCALES", "Locales obtenidos");
                               List<ResponseLocal> locales = response.body();
                               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                   loadLocales(locales);
                               }
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
                   Log.d("GET_LOCALES", "Error");
               }
           });

        } else {
            Toast.makeText(ctx, "Ingrese latitud y longitud", Toast.LENGTH_LONG).show();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void loadLocales(List<ResponseLocal> locales) {

        // Para cada local, tomo cada elemento y lo agrego a mi array de datos de cada local.
        locales.forEach((local) -> {
            // Guardo el nombre del grupo. Ej: Pizzería
            listGroup.add(local.nombre);

            // Tomo cada dato del local
            String nombre = local.nombre;
            String direccion = local.direccion;
            String geolocalizacion = "Lat: " + local.geolocalizacion.latitud +
                    " - Long:" + local.geolocalizacion.longitud;
            String tiempoEntrega = String.valueOf(local.tiempoEntrega);
            String costoEnvio = String.valueOf(local.costoEnvio);
            String horario = local.horario.abre + " - " + local.horario.cierra;
            String distancia = local.distancia.distance + " km";

            // Obtengo tags
            StringBuilder sbTags = new StringBuilder(local.tags.size());
            local.tags.forEach(tag -> {
                sbTags.append(tag + ", ");
            });

            // Obtengo y calculo promedio de puntuaciones
            int puntuaciones = 0;
            if (local.puntuaciones.size() > 0) {

                AtomicInteger total = new AtomicInteger();
                local.puntuaciones.forEach(puntuacion -> {
                    total.set(Integer.sum(total.get(), puntuacion.numero));
                });
                puntuaciones = (int) (total.intValue() / local.puntuaciones.stream().count());
            }

            // Obtengo comentarios
            StringBuilder sbComentarios = new StringBuilder(local.comentarios.size());
            if (local.comentarios.size() > 0) {
                local.comentarios.stream().forEach(c -> {
                    String comentario = c.usuario.nombre + ": " + c.comentario;
                    sbComentarios.append(comentario + ". // ");

                });
            } else {
                sbComentarios.append("*Sin comentarios.*");
            }

            // Creo mi array para guardar todos los datos y agrego cada elemento.
            ArrayList<String> datos = new ArrayList<String>();

            datos.add("Nombre: " + nombre);
            datos.add("Dirección: " + direccion);
            datos.add("Geolocalización: " + geolocalizacion);
            datos.add("Puntuaciones: " + String.valueOf(puntuaciones));
            datos.add("Comentarios: " + sbComentarios.toString());
            datos.add("Tiempo entrega: " + tiempoEntrega);
            datos.add("Costo envío: " + costoEnvio);
            datos.add("Tags: " + sbTags.toString());
            datos.add("Horario: " + horario);
            datos.add("Distancia: " + distancia);

            // A mi hashmap le guardo el nombre del local y sus datos
            // En este caso solo hay 2 locales con nombres diferentes
            listChild.put(local.nombre, datos);
        });
        adapter = new MainAdapter(listGroup, listChild);
        lstLocales.setAdapter(adapter);
    }

}