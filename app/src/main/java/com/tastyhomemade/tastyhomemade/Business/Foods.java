package com.tastyhomemade.tastyhomemade.Business;

import net.sourceforge.jtds.jdbc.DateTime;

import java.util.Calendar;

/**
 * Created by raed on 12/1/2016.
 */

public class Foods {
    private int Id;
    private int LanguageId;
    private String Name;
    private String Description;
    private int CategoryId;
    private int UserId;
    private java.sql.Time RequestTimeFrom;
    private java.sql.Time RequestTimeTo;
    private byte[] Photo;
    private float Price;

    public Foods(int id, int languageId, String name, String description, int categoryId, int userId, java.sql.Time requestTimeFrom, java.sql.Time requestTimeTo, byte[] photo, float price) {
        Id = id;
        LanguageId = languageId;
        Name = name;
        Description = description;
        CategoryId = categoryId;
        UserId = userId;
        RequestTimeFrom = requestTimeFrom;
        RequestTimeTo = requestTimeTo;
        Photo = photo;
        Price = price;
    }

    public Foods() {
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

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public java.sql.Time getRequestTimeFrom() {
        return RequestTimeFrom;
    }

    public void setRequestTimeFrom(java.sql.Time requestTimeFrom) {
        RequestTimeFrom = requestTimeFrom;
    }

    public java.sql.Time getRequestTimeTo() {
        return RequestTimeTo;
    }

    public void setRequestTimeTo(java.sql.Time requestTimeTo) {
        RequestTimeTo = requestTimeTo;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }
}
