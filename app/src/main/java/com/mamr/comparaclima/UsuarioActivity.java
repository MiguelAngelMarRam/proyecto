package com.mamr.comparaclima;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.mamr.comparaclima.db.DatabaseHelper;
import com.mamr.comparaclima.models.User;

/**
 * @author Miguel Ángel Martínez Ramírez
 * Pantalla principal tras el login (Dashboard)
 */
public class UsuarioActivity extends AppCompatActivity {

    TextView tvBienvenida;
    DatabaseHelper db;
    User usuarioLogueado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario); // Asegúrate de tener este layout

        db = new DatabaseHelper(this);
        tvBienvenida = findViewById(R.id.textViewBienvenida); // Ajusta según tu ID en XML

        // 1. Recuperar el email enviado desde Login
        String email = getIntent().getStringExtra("USER_EMAIL");

        if (email != null) {
            // 2. Cargar los datos completos del usuario desde la DB
            usuarioLogueado = db.getUserByEmail(email);

            if (usuarioLogueado != null) {
                tvBienvenida.setText("Bienvenido, " + usuarioLogueado.getName());
            }
        } else {
            // Si no hay email, algo ha ido mal, volvemos al login
            Toast.makeText(this, "Error de sesión", Toast.LENGTH_SHORT).show();
            finish();
        }

        // Aquí irán los OnClickListeners para:
        // - Ver Ubicaciones Favoritas
        // - Configurar Preferencias (Pesos de IA)
        // - Realizar nueva Comparación
    }
}