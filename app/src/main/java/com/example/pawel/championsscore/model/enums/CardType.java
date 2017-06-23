package com.example.pawel.championsscore.model.enums;

import com.example.pawel.championsscore.R;

/**
 * Created by Mateusz on 07.06.2017.
 */

public enum CardType {
    FIRST_YELLOW(R.drawable.yellow), SECOND_YELLOW(R.drawable.second_yellow), RED(R.drawable.red);

    private int picture;

    CardType(int picture) {
        this.picture = picture;
    }

    public String getType() {
        return this.toString().toLowerCase();
    }

    public int getPicture() {
        return picture;
    }
}
