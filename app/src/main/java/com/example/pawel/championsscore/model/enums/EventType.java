package com.example.pawel.championsscore.model.enums;

/**
 * Created by Mateusz on 04.06.2017.
 */

public enum EventType {
    SUBSTITUTE("substitution"), GOAL("GOAL"), STATE("STATE"), CARD("CARD");
    private String type;

    EventType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
