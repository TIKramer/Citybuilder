package com.kramer.a18882652.citybuilder.Model;

import com.kramer.a18882652.citybuilder.Database.GameDataSchema;

public class Residential implements Structure
{
    int imageId;
    int structureID;
    public Residential()
    {

    }
    public Residential(int imageId)
    {
        this.imageId = imageId;
    }

    public int getImageID()
    {
        return imageId;
    }
    @Override
    public void setImageID(int imageID) {
        this.imageId = imageID;
    }

    @Override
    public int getCost() {
        return Integer.valueOf("" + SettingsModel.getInstance().getData(GameDataSchema.SettingsTable.Cols.HOUSE_BUILDING_COST));
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
