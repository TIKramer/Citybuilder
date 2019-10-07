package com.kramer.a18882652.citybiolder.Model;

import com.kramer.a18882652.citybiolder.Database.GameDataSchema;

public class Road implements Structure
{
    int imageId;
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
    public void setCost(int cost) {
    }
}
