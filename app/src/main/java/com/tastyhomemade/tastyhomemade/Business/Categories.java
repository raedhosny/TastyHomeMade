package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by Raed on 11/28/2016.
 */

public class Categories {
    int Id;
    int LanguageId;
    String Name;

    public Categories(int id, int languageId, String name) {
        Id = id;
        LanguageId = languageId;
        Name = name;
    }

    public Categories() {
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
}
