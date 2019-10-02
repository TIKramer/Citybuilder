package com.kramer.a18882652.citybiolder;


import android.graphics.Bitmap;

public class MapElement
{
    Structure structure;
    Bitmap image;
    String ownerName;


    public MapElement(Structure struct, Bitmap image, String ownerName)
    {
        structure = struct;
        this.image = image;
        this.ownerName = ownerName;
    }
    public boolean isBuildable()
    {
        return structure!=null;
    }

    public Structure getStructure() {
        return structure;
    }
}
