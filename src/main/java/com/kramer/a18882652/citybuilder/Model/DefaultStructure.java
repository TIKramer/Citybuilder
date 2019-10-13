package com.kramer.a18882652.citybuilder.Model;

import com.kramer.a18882652.citybuilder.R;

public class DefaultStructure implements Structure {

    private final int COLOR = R.drawable.grass;
    private int structureID;
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
    @Override
    public int getStructureID() {
        return structureID;
    }

    @Override
    public void setStructureID(int structureID) {
        this.structureID = structureID;

    }
}
