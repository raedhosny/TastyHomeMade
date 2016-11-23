package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by raed on 11/23/2016.
 */

public class RegisterTypes {
    int id;
    int LanguageId;
    String Name;

    public RegisterTypes(int id, int languageId, String name) {
        this.id = id;
        LanguageId = languageId;
        Name = name;
    }

    public RegisterTypes() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
