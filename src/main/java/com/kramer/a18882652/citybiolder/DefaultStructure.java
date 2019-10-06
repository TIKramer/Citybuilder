package com.kramer.a18882652.citybiolder;

import android.graphics.Color;

public class DefaultStructure implements Structure{

    private final int COLOR = R.drawable.grass;
    @Override
    public int getImageID() {
        return COLOR;
    }

    @Override
    public void setImageID(int imageID) {

    }

    @Override
    public int getCost() {
        return 0;
    }
}
