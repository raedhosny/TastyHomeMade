package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by raed on 11/22/2016.
 */

public class User {
     int Id;
    String Name;
    String Username;
    String Password;
    String Email;
    int RegisterTypeId;
    double CurrentLocation_Longitude;
    double CurrentLocation_Latitude;
    int CityId;
    String District;
    String Street;
    String Building;
    String Apartment;
    Boolean IsActive;
    String ActivationCode;

    public User() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getRegisterTypeId() {
        return RegisterTypeId;
    }

    public void setRegisterTypeId(int registerTypeId) {
        RegisterTypeId = registerTypeId;
    }

    public double getCurrentLocation_Longitude() {
        return CurrentLocation_Longitude;
    }

    public void setCurrentLocation_Longitude(double currentLocation_Longitude) {
        CurrentLocation_Longitude = currentLocation_Longitude;
    }

    public double getCurrentLocation_Latitude() {
        return CurrentLocation_Latitude;
    }

    public void setCurrentLocation_Latitude(double currentLocation_Latitude) {
        CurrentLocation_Latitude = currentLocation_Latitude;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public String getApartment() {
        return Apartment;
    }

    public void setApartment(String apartment) {
        Apartment = apartment;
    }

    public Boolean getActive() {
        return IsActive;
    }

    public void setActive(Boolean active) {
        IsActive = active;
    }

    public String getActivationCode() {
        return ActivationCode;
    }

    public void setActivationCode(String activationCode) {
        ActivationCode = activationCode;
    }
}
