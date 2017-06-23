package com.example.pawel.championsscore.model.enums;

/**
 * Created by Mateusz on 04.06.2017.
 */

public enum EventType {
    SUBSTITUTION, GOAL, STATE, CARD;

    public String getType() {
        return this.toString().toLowerCase();
    }


}
