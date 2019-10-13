package com.kramer.a18882652.citybuilder.Model;


import android.graphics.Bitmap;

public class MapElement
{
    Structure structure;
    Bitmap image;
    String ownerName;



    public MapElement(Structure struct, Bitmap image)
    {
        structure = struct;
        this.image = image;
    }

    public void setOwnerName(String name)
    {
        ownerName = name;
    }

    public String getOwnerName()
    {
        String name;
        if(ownerName !=null && !(ownerName.length() >0))
        {
            name = structure.getClass().getSimpleName();

        }
        else
        {
            name = ownerName;

        }
        return name;


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

    public void reloadData()
    {

    }
}
