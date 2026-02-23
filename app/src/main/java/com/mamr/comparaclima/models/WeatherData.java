package com.mamr.comparaclima.models;

/**
 * @author Miguel Ángel Martínez Ramírez
 * Modelo para los datos obtenidos de la API
 */
public class WeatherData {
    private String cityName;
    private float temp;
    private float windSpeed;
    private float precipitationProb;

    public WeatherData(String cityName, float temp, float windSpeed, float precipitationProb) {
        this.cityName = cityName;
        this.temp = temp;
        this.windSpeed = windSpeed;
        this.precipitationProb = precipitationProb;
    }

    // Getters
    public String getCityName() { return cityName; }
    public float getTemp() { return temp; }
    public float getWindSpeed() { return windSpeed; }
    public float getPrecipitationProb() { return precipitationProb; }
}