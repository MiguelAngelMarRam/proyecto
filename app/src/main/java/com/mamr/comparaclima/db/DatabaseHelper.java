package com.mamr.comparaclima.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.mamr.comparaclima.models.Preference;
import com.mamr.comparaclima.models.User;

/**
 * @author Miguel Ángel Martínez Ramírez
 * Proyecto: ComparaClima - TFG DAM
 * Descripción: Gestor central de la base de datos SQLite.
 * Implementa la estructura definida en el diseño lógico de la Tarea 2.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Configuración de la Base de Datos
    private static final String DATABASE_NAME = "ComparaClima.db";
    private static final int DATABASE_VERSION = 1;

    // Nombres de Tablas (Modernizados a tu diseño de la Tarea 2)
    public static final String TABLE_USERS = "USERS";
    public static final String TABLE_ROLES = "ROLES";
    public static final String TABLE_LOCATIONS = "LOCATIONS";
    public static final String TABLE_PREFERENCES = "PREFERENCES";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        // Habilitamos claves foráneas para integridad referencial (ON DELETE CASCADE)
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Tabla de Roles
        db.execSQL("CREATE TABLE " + TABLE_ROLES + " (" +
                "role_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "role_name TEXT NOT NULL UNIQUE)");

        // 2. Tabla de Usuarios (Sustituye a 'usuario')
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (" +
                "user_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT NOT NULL UNIQUE, " +
                "password_hash TEXT NOT NULL, " +
                "name TEXT, " +
                "role_id INTEGER NOT NULL, " +
                "FOREIGN KEY (role_id) REFERENCES ROLES(role_id) ON DELETE RESTRICT)");

        // 3. Tabla de Ubicaciones (Para favoritos)
        db.execSQL("CREATE TABLE " + TABLE_LOCATIONS + " (" +
                "location_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "name TEXT NOT NULL, " +
                "type TEXT NOT NULL, " +
                "latitude REAL, " +
                "longitude REAL, " +
                "FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE CASCADE)");

        // 4. Tabla de Preferencias (Clave para el algoritmo de clasificación)
        db.execSQL("CREATE TABLE " + TABLE_PREFERENCES + " (" +
                "pref_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "user_id INTEGER NOT NULL, " +
                "location_type TEXT NOT NULL, " +
                "temp_min REAL, " +
                "temp_max REAL, " +
                "wind_max REAL, " +
                "precip_threshold REAL, " +
                "weight_temp REAL DEFAULT 1.0, " +
                "weight_wind REAL DEFAULT 1.0, " +
                "weight_precip REAL DEFAULT 1.0, " +
                "FOREIGN KEY (user_id) REFERENCES USERS(user_id) ON DELETE CASCADE)");

        // Insertamos datos maestros iniciales
        db.execSQL("INSERT INTO ROLES (role_name) VALUES ('admin'), ('user')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En desarrollo, eliminamos todo y recreamos para evitar conflictos de versiones
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PREFERENCES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLES);
        // También borramos las tablas antiguas por si quedaran restos
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS busqueda");
        db.execSQL("DROP TABLE IF EXISTS comparacion");
        onCreate(db);
    }

    // --- MÉTODOS DE OPERACIÓN (Modernizados con ContentValues por seguridad) ---

    /**
     * Registra un nuevo usuario en la aplicación.
     * Utiliza ContentValues para evitar Inyección SQL.
     */
    public boolean registerUser(String name, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Comprobar si el email existe (Reutilizamos tu lógica pero modernizada)
        Cursor cursor = db.query(TABLE_USERS, null, "email=?", new String[]{email}, null, null, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return false; // Email ya registrado
        }
        cursor.close();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("password_hash", password); // En el futuro aplicaremos hashing aquí
        values.put("role_id", 2); // Por defecto rol 'user'

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    /**
     * Verifica las credenciales de acceso.
     */
    public boolean checkLogin(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = "email=? AND password_hash=?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(TABLE_USERS, null, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();

        return count > 0;
    }

    /**
     * Recupera los datos del usuario a partir de su email
     */
    public User getUserByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        User user = null;
        Cursor cursor = db.query(TABLE_USERS, null, "email=?", new String[]{email}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            user = new User(
                    cursor.getInt(cursor.getColumnIndexOrThrow("user_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    cursor.getString(cursor.getColumnIndexOrThrow("password_hash")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("role_id"))
            );
            cursor.close();
        }
        return user;
    }
    // Guarda o actualiza las preferencias del usuario
    public void savePreferences(Preference pref) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user_id", pref.getUserId());
        values.put("location_type", pref.getLocationType());
        values.put("temp_min", pref.getTempMin());
        values.put("temp_max", pref.getTempMax());
        values.put("wind_max", pref.getWindMax());
        values.put("precip_threshold", pref.getPrecipThreshold());
        values.put("weight_temp", pref.getWeightTemp());
        values.put("weight_wind", pref.getWeightWind());
        values.put("weight_precip", pref.getWeightPrecip());

        // Intentamos actualizar, si no existe, insertamos (cláusula CONFLICT_REPLACE)
        db.insertWithOnConflict(TABLE_PREFERENCES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    // Recupera las preferencias de un usuario
    public Preference getPreferences(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PREFERENCES, null, "user_id=?", new String[]{String.valueOf(userId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Preference p = new Preference(
                    cursor.getInt(0), cursor.getString(1), cursor.getFloat(2),
                    cursor.getFloat(3), cursor.getFloat(4), cursor.getFloat(5),
                    cursor.getFloat(6), cursor.getFloat(7), cursor.getFloat(8)
            );
            cursor.close();
            return p;
        }
        return null; // Si no tiene preferencias guardadas aún
    }
}