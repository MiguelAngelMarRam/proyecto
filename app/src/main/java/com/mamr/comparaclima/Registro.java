package com.mamr.comparaclima;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mamr.comparaclima.db.DatabaseHelper;

import java.util.regex.Pattern;

/**
 * @author Miguel Ángel Martínez Ramírez
 */
public class Registro extends AppCompatActivity {

    EditText etNombre, etEmail, etPassword;
    Button btnRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = new DatabaseHelper(this);

        etNombre = findViewById(R.id.editTextRName);
        etEmail = findViewById(R.id.editTextLEmail); // Nota: Asegúrate que este ID es el correcto en tu XML de registro
        etPassword = findViewById(R.id.editTextLPassword);
        btnRegister = findViewById(R.id.button_login2);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String name = etNombre.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (!validarEmail(email)) {
            Toast.makeText(this, R.string.toast_emailIncorrecto, Toast.LENGTH_LONG).show();
            return;
        }

        if (!validarContrasenia(password)) {
            Toast.makeText(this, R.string.toast_passwordIncorrecto, Toast.LENGTH_LONG).show();
            return;
        }

        // Inserción usando el nuevo método modernizado de DatabaseHelper
        if (db.registerUser(name, email, password)) {
            Toast.makeText(this, R.string.toast_registroExito, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Registro.this, Login.class));
            finish();
        } else {
            Toast.makeText(this, R.string.toast_EmailExistente, Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validarEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean validarContrasenia(String password) {
        // Al menos 8 caracteres, una mayúscula, una minúscula y un número
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
        return password != null && Pattern.compile(regex).matcher(password).matches();
    }
}