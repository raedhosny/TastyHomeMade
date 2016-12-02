package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by Raed on 12/3/2016.
 */

public class Additions {
    int Id;
    int LanguageId;
    String Name;

    public Additions(int id, int languageId, String name) {
        Id = id;
        LanguageId = languageId;
        Name = name;
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

    public void setName(String name) {
        Name = name;
    }
}
