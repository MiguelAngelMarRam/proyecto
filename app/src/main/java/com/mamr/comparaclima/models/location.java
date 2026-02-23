package com.mamr.comparaclima.models;

/**
 * @author Miguel Ángel Martínez Ramírez
 * Proyecto: ComparaClima - TFG DAM
 */
public class Location {
    private int locationId;
    private int userId;
    private String name;
    private String type; // playa, montaña, ciudad...
    private double latitude;
    private double longitude;

    // Constructor vacío añadido por seguridad
    public Location() {}

    // Constructor completo
    public Location(int locationId, int userId, String name, String type, double latitude, double longitude) {
        this.locationId = locationId;
        this.userId = userId;
        this.name = name;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters y Setters
    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}