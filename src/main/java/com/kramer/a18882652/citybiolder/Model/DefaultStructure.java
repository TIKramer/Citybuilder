package com.kramer.a18882652.citybiolder.Model;

import com.kramer.a18882652.citybiolder.R;
/* This defines a default structure in the map
    -placed where there are no user placed buildings
        its image is grass
 */
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
