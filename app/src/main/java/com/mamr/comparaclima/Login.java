
package com.mamr.comparaclima;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mamr.comparaclima.db.DatabaseHelper;

/**
 * @author Miguel Ángel Martínez Ramírez
 */
public class Login extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        etEmail = findViewById(R.id.editTextLEmail);
        etPassword = findViewById(R.id.editTextLPassword);
        btnLogin = findViewById(R.id.button_login2);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, rellena todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Usamos el nuevo metodo de DatabaseHelper
        if (db.checkLogin(email, password)) {
            // GUARDAR SESIÓN
            getSharedPreferences("SESION", MODE_PRIVATE).edit()
                    .putString("email", email)
                    .putBoolean("logueado", true)
                    .apply();

            Toast.makeText(this, R.string.toast_loginValido, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, UsuarioActivity.class);
            // Pasamos el email para identificar al usuario en la siguiente pantalla
            intent.putExtra("USER_EMAIL", email);
            startActivity(intent);
            finish(); // Cerramos login para que no se pueda volver atrás al loguearse
        } else {
            Toast.makeText(this, R.string.toast_loginInvalido, Toast.LENGTH_SHORT).show();
        }
    }
}