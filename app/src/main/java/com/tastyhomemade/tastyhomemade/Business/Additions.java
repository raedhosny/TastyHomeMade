package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by Raed on 12/3/2016.
 */

public class Additions {
    int Id;
    int LanguageId;
    String Name;
    float Price;
    byte[] Photo;

    public Additions(int id, int languageId, String name,float Price ,byte[] Photo) {
        Id = id;
        LanguageId = languageId;
        Name = name;
        Price = Price ;
        Photo = Photo;

    }

    public Additions() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getLanguageId() {
        return LanguageId;
    }

    public void setLanguageId(int languageId) {
        LanguageId = languageId;
    }

    public String getName() {
        return Name;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public void setName(String name) {
        Name = name;
    }
}
