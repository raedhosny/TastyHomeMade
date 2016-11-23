package com.tastyhomemade.tastyhomemade.Business;

/**
 * Created by Raed on 11/18/2016.
 */

public class MainMenuItem {
    private int Id;
    private String Name;

    public MainMenuItem(int id, String name) {
        Id = id;
        Name = name;
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
}
