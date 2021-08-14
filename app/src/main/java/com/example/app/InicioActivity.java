package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.atomic.AtomicReference;

public class InicioActivity extends AppCompatActivity {

   // private ResponseLogin loggedUserData = null;
   private UserViewModel mUserViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        //loggedUserData = (ResponseLogin) getIntent().getSerializableExtra("USER");

        // Get a new or existing ViewModel from the ViewModelProvider.

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        TextView lblCompleteName = findViewById(R.id.lblCompleteName);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);


        mUserViewModel.getUser().observe(this, user -> {
            if (user==null){
                goLogin();
            }else{
                String nombre = user.getNombre();
                String apellido = user.getApellido();
                lblCompleteName.setText(nombre+ " " +apellido);
            }


        });

    }

    // Solo se puede regresar al login cerrando sesión
    @Override
    public void onBackPressed() {
        boolean volver = false;
        if (volver) {
            super.onBackPressed();
        }
    }

    // Cierro sesión y voy a login
    public void goLogin() {
      //  loggedUserData = null;
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }

    // Voy al activity para ver locales
    public void goLocales(View view) {
        Intent intent = new Intent(getBaseContext(), LocalActivity.class);
      //  intent.putExtra("USER", loggedUserData);
        startActivity(intent);
    }

    // Voy al activity para ver mapa
    public void goMapa(View view) {
        Intent intent = new Intent(getBaseContext(), MapaActivity.class);
       startActivity(intent);
    }

    // Voy al activity para crear pedido
    public void goPedido(View view) {
        Intent intent = new Intent(getBaseContext(), PedidoActivity.class);
      //  intent.putExtra("USER", loggedUserData);
        startActivity(intent);
    }
}