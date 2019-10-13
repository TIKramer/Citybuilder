package com.kramer.a18882652.citybuilder.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.ArrayList;

public class GameDataCursor extends CursorWrapper {
    public GameDataCursor(Cursor cursor) {
        super(cursor);
    }

    public ArrayList<Integer> getGameData()
    {
        ArrayList<Integer> values = new ArrayList<>();

        int gameTime = getInt(getColumnIndex(GameDataSchema.GameDataTable.Cols.GAME_TIME));
        int cash = getInt(getColumnIndex(GameDataSchema.GameDataTable.Cols.MONEY));
        int residential = getInt(getColumnIndex(GameDataSchema.GameDataTable.Cols.NUMBER_OF_RESIDENTIAL));
        int commercial = getInt(getColumnIndex(GameDataSchema.GameDataTable.Cols.NUMBER_OF_COMMERCIAL));
        int roads = getInt(getColumnIndex(GameDataSchema.GameDataTable.Cols.NUMBER_OF_ROADS));


       values.add(gameTime);
       values.add(cash);
        values.add(residential);
        values.add(commercial);
        values.add(roads);




        return  values;




    }
}
