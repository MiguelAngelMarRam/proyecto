package com.mamr.comparaclima;

import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.mamr.comparaclima.db.DatabaseHelper;
import com.mamr.comparaclima.models.Preference;
import com.mamr.comparaclima.models.User;

/**
 * @author Miguel Ángel Martínez Ramírez
 * Configuración de pesos para el algoritmo de recomendación.
 */
public class PreferenciasActivity extends AppCompatActivity {

    SeekBar sbTemp, sbViento, sbLluvia;
    Button btnGuardar;
    DatabaseHelper db;
    String userEmail;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);

        db = new DatabaseHelper(this);
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        user = db.getUserByEmail(userEmail);

        // Enlazar con el XML
        sbTemp = findViewById(R.id.seekBarTemp);
        sbViento = findViewById(R.id.seekBarViento);
        sbLluvia = findViewById(R.id.seekBarLluvia);
        btnGuardar = findViewById(R.id.buttonGuardarPref);

        // Cargar preferencias actuales si existen
        loadCurrentPrefs();

        btnGuardar.setOnClickListener(v -> savePrefs());
    }

    private void loadCurrentPrefs() {
        Preference p = db.getPreferences(user.getUserId());
        if (p != null) {
            sbTemp.setProgress((int) (p.getWeightTemp() * 10));
            sbViento.setProgress((int) (p.getWeightWind() * 10));
            sbLluvia.setProgress((int) (p.getWeightPrecip() * 10));
        }
    }

    private void savePrefs() {
        // Convertimos el progreso (0-10) a un peso (0.0 - 1.0)
        float wTemp = sbTemp.getProgress() / 10.0f;
        float wWind = sbViento.getProgress() / 10.0f;
        float wRain = sbLluvia.getProgress() / 10.0f;

        Preference newPref = new Preference();
        newPref.setUserId(user.getUserId());
        newPref.setLocationType("GENERAL"); // Por ahora general
        newPref.setWeightTemp(wTemp);
        newPref.setWeightWind(wWind);
        newPref.setWeightPrecip(wRain);

        db.savePreferences(newPref);
        Toast.makeText(this, "Preferencias actualizadas", Toast.LENGTH_SHORT).show();
        finish();
    }
}