package com.example.pawel.championsscore.model;

public class Stage {

    private String name;

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stage(int id, String name) {
        this.id = id;
        this.name = name;
    }
}