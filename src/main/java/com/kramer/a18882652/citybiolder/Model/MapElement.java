package com.kramer.a18882652.citybiolder.Model;


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

    public void setBitmap(Bitmap image)
    {
        this.image = image;
    }

    public Structure getStructure() {
        return structure;
    }
    public void setStructure(Structure structure) {

         this.structure = structure;
    }

    public Bitmap getImage() {
        return image;
    }
}
