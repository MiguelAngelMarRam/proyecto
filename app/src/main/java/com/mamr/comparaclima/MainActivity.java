package com.mamr.comparaclima;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Miguel Ángel Martínez Ramírez
 * Esta actividad no tiene interfaz. Actúa como enrutador de sesión.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Consultamos si hay una sesión activa
        SharedPreferences prefs = getSharedPreferences("SESION", MODE_PRIVATE);
        boolean estaLogueado = prefs.getBoolean("logueado", false);
        String email = prefs.getString("email", null);

        Intent intent;
        if (estaLogueado && email != null) {
            // Usuario ya identificado -> Directo al panel principal
            intent = new Intent(this, UsuarioActivity.class);
            intent.putExtra("USER_EMAIL", email);
        } else {
            // No hay sesión -> A loguearse
            intent = new Intent(this, Login.class);
        }

        startActivity(intent);
        finish(); // Cerramos MainActivity para que no quede en el historial de pantallas
    }
}