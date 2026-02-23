package com.mamr.comparaclima.models;

/**
 * @author Miguel Ángel Martínez Ramírez
 * Proyecto: ComparaClima - TFG DAM
 */
public class Preference {
    private int userId;
    private String locationType;
    private float tempMin;
    private float tempMax;
    private float windMax;
    private float precipThreshold; // Umbral de lluvia
    private float weightTemp;      // Peso importancia temperatura
    private float weightWind;      // Peso importancia viento
    private float weightPrecip;    // Peso importancia lluvia

    // Constructor vacío
    public Preference() {
    }

    // Constructor completo (Sincronizado con DatabaseHelper)
    public Preference(int userId, String locationType, float tempMin, float tempMax,
                      float windMax, float precipThreshold, float weightTemp,
                      float weightWind, float weightPrecip) {
        this.userId = userId;
        this.locationType = locationType;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.windMax = windMax;
        this.precipThreshold = precipThreshold;
        this.weightTemp = weightTemp;
        this.weightWind = weightWind;
        this.weightPrecip = weightPrecip;
    }

    // Getters y Setters
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getLocationType() { return locationType; }
    public void setLocationType(String locationType) { this.locationType = locationType; }
    public float getTempMin() { return tempMin; }
    public void setTempMin(float tempMin) { this.tempMin = tempMin; }
    public float getTempMax() { return tempMax; }
    public void setTempMax(float tempMax) { this.tempMax = tempMax; }
    public float getWindMax() { return windMax; }
    public void setWindMax(float windMax) { this.windMax = windMax; }
    public float getPrecipThreshold() { return precipThreshold; }
    public void setPrecipThreshold(float precipThreshold) { this.precipThreshold = precipThreshold; }
    public float getWeightTemp() { return weightTemp; }
    public void setWeightTemp(float weightTemp) { this.weightTemp = weightTemp; }
    public float getWeightWind() { return weightWind; }
    public void setWeightWind(float weightWind) { this.weightWind = weightWind; }
    public float getWeightPrecip() { return weightPrecip; }
    public void setWeightPrecip(float weightPrecip) { this.weightPrecip = weightPrecip; }
}