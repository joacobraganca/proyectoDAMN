package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.app.api.APIInterface;
import com.example.app.api.APIClient;
import com.example.app.model.register.ResponseRegister;
import com.example.app.model.register.RequestRegisterDTO;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistroActivity extends AppCompatActivity {

    APIInterface apiInterface;
    Context ctx;

    EditText txtFirstName;
    EditText txtLastName;
    EditText txtEmail;
    EditText txtDocument;
    EditText txtPhone;
    EditText txtPassword;
    Switch swcActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiInterface = APIClient.getRetrofitClient().create(APIInterface.class);
        ctx = this.getApplicationContext();

        txtFirstName = findViewById(R.id.txtName);
        txtLastName = findViewById(R.id.txtLastName);
        txtEmail = findViewById(R.id.txtEmail);
        txtDocument = findViewById(R.id.txtDocument);
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPasswordRegister);
        swcActivo = findViewById(R.id.swtActivo);
    }

    public void doRegister(View view) {

        // Obtengo valores de los input
        String name = txtFirstName.getText().toString();
        String lastname = txtLastName.getText().toString();
        String email = txtEmail.getText().toString();
        String document = txtDocument.getText().toString();
        int phone = Integer.parseInt(txtPhone.getText().toString());
        String password = txtPassword.getText().toString();
        String active = "ACTIVO";

        boolean activo = swcActivo.isChecked();
        if (activo) {
            active = "ACTIVO";
        } else {
            active = "INACTIVO";
        }

        // Creo objeto a mandar a la api
        RequestRegisterDTO dto = new RequestRegisterDTO(name, lastname, email, phone, password, document, active);

        try {
            Call<ResponseRegister> callAuthResponse = apiInterface.registro(dto);
            callAuthResponse.enqueue(new Callback<ResponseRegister>() {
                @Override
                public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {

                    // Si pudo registrar correctamente
                    // Muestro toast informando y rediijo al Login
                    if (response.code() == 200) {
                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        Toast.makeText(ctx, "Registrado correctamente! Inicie sesión!", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    } else {
                        Log.d("POST_REGISTRO", "Error: " + response.code());
                        Toast.makeText(ctx, "ERROR! Revise datos ingresados.", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseRegister> call, Throwable t) {
                    Log.d("POST_REGISTRO", "Error: " + t.getMessage());

                }
            });
        } catch (Exception e) {
            Log.d("POST_REGISTRO", "Error.");
        }
    }

    // Vuelvo al activity de login
    public void goLogin(View view) {
        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);
    }
}