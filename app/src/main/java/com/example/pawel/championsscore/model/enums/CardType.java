package com.example.pawel.championsscore.model.enums;

import com.example.pawel.championsscore.R;

/**
 * Created by Mateusz on 07.06.2017.
 */

public enum CardType {
    FIRST_YELLOW("first-yellow", R.drawable.yellow), SECOND_YELLOW("second-yellow", R.drawable.second_yellow), RED("red", R.drawable.red);

    private String type;
    private int picture;

    CardType(String type, int picture) {
        this.type = type;
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public int getPicture() {
        return picture;
    }
}
