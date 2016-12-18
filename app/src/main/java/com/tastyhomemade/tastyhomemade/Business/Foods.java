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
    private String Photo;
    private float Price;
    private boolean IsVisible;
    private int NumberOfRequestsCount;

    public Foods(int id, int languageId, String name, String description, int categoryId, int userId, java.sql.Time requestTimeFrom, java.sql.Time requestTimeTo, String photo, float price,boolean IsVisible) {
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
        this.IsVisible = IsVisible;
        NumberOfRequestsCount = 0;

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

    public String getPhoto() {
        return Photo;
    }

    public boolean isVisible() {
        return IsVisible;
    }

    public void setVisible(boolean visible) {
        IsVisible = visible;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public float getPrice() {
        return Price;
    }

    public void setPrice(float price) {
        Price = price;
    }

    public int getNumberOfRequestsCount() {
        return NumberOfRequestsCount;
    }

    public void setNumberOfRequestsCount(int numberOfRequestsCount) {
        NumberOfRequestsCount = numberOfRequestsCount;
    }
}
