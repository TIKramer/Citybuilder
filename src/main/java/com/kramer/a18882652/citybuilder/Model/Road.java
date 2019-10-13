package com.kramer.a18882652.citybuilder.Model;

import com.kramer.a18882652.citybuilder.Database.GameDataSchema;

public class Road implements Structure
{
    int imageId;
    int structureID;
    public Road()
    {
    }
    public Road(int imageId)
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
        return Integer.valueOf("" + SettingsModel.getInstance().getData(GameDataSchema.SettingsTable.Cols.ROAD_BUILDING_COST));
    }

    @Override
    public int getStructureID() {
        return structureID;
    }

    @Override
    public void setStructureID(int structureID) {
        this.structureID = structureID;

    }

    public void setCost(int cost) {
    }
}
