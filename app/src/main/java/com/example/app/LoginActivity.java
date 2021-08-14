package com.example.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app.api.APIClient;
import com.example.app.api.APIInterface;
import com.example.app.model.login.ResponseLogin;
import com.example.app.model.login.RequestLoginDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    APIInterface apiInterface;
    Context ctx;

    EditText txtUsername;
    EditText txtPassword;

    private UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);
        ctx = this.getApplicationContext();
        txtUsername = findViewById(R.id.txtEmailLogin);
        txtPassword = findViewById(R.id.txtPasswordLogin);

        mUserViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    public void doLogin(View view) {
        // Obtengo datos ingresados por el usuario.
        String user = txtUsername.getText().toString();
        String password = txtPassword.getText().toString();
        RequestLoginDTO dto = new RequestLoginDTO(user, password);
        try {
            Call<ResponseLogin> callAuthResponse = apiInterface.login(dto);
            callAuthResponse.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                    // Si pudo realizar login
                    // Guardo los datos devueltos y redirijo a InicioActivity
                    if (response.code() == 200) {
                        Log.d("LOGIN", "Login correcto");

                        ResponseLogin data = response.body();
                        UserTable user = new UserTable(data.doc.id, data.token, data.doc.nombre, data.doc.apellido, data.doc.email);
                        mUserViewModel.insert(user);

                        Intent intent = new Intent(getBaseContext(), InicioActivity.class);
                        //intent.putExtra("USER", response.body());
                        startActivity(intent);

                        finish();
                    } else {
                        Log.d("LOGIN", "Login error!!" + response.code());
                        Toast.makeText(ctx, "ERROR! Revise email y/o contraseña", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Log.d("LOGIN", "Login error!!" + t.getMessage());
                }
            });
        } catch (Exception e) {
            Log.d("ERROR LOGIN", "Error");
        }
    }


    // Si se va para atras desde login, se cierra
    @Override
    public void onBackPressed() {
        boolean volver = false;
        if (volver) {
            super.onBackPressed();
        }
    }

    // Voy al activity de registro
    public void goRegister(View view) {
        Intent intent = new Intent(getBaseContext(), RegistroActivity.class);
        startActivity(intent);
    }
}