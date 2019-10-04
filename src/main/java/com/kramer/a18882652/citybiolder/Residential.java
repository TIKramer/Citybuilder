package com.kramer.a18882652.citybiolder;

public class Residential implements Structure
{
    int imageId;
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

}
