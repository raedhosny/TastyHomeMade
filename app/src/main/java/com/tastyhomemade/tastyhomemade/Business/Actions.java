package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by Raed on 12/12/2016.
 */

public class Actions {
    int Id;
    int LanguageId;
    String Name;

    public Actions() {
    }

    public Actions(int id, int languageId, int name) {
        Id = id;
        LanguageId = languageId;
        Name = name;
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
