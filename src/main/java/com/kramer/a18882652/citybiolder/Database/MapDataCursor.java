package com.kramer.a18882652.citybiolder.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.kramer.a18882652.citybiolder.Model.MapElement;
import com.kramer.a18882652.citybiolder.Model.Structure;
import com.kramer.a18882652.citybiolder.Model.StructureData;

public class MapDataCursor extends CursorWrapper {
    public MapDataCursor(Cursor cursor) {
        super(cursor);
    }

    public MapElement getGameData()
    {

                String ownerName = getString(getColumnIndex(GameDataSchema.MapElementsTable.Cols.OWNER));

                byte[] image = getBlob(getColumnIndex(GameDataSchema.MapElementsTable.Cols.IMAGE));
                int imageId = getInt(getColumnIndex(GameDataSchema.MapElementsTable.Cols.STRUCTURE_IMAGE_ID));
                String structureType = getString(getColumnIndex(GameDataSchema.MapElementsTable.Cols.STRUCTURE_TYPE));
                Structure structure = StructureData.getGameData().getByNameAndId(structureType,imageId);
                Bitmap bitmap = null;
                //Decode the bitmap as its inserted as a byte array need to convert it back
                if(image.length > 0) {
                     bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
                }

                MapElement element = new MapElement(structure, bitmap);
                element.setOwnerName(ownerName);




        return element;




    }
}
