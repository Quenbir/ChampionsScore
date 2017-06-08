package com.example.pawel.championsscore.model.enums;

/**
 * Created by Mateusz on 04.06.2017.
 */

public enum Side {
    HOME("H"), AWAY("A");

    private String side;

    Side(String side) {
        this.side = side;
    }

    public String getSide() {
        return side;
    }
}
