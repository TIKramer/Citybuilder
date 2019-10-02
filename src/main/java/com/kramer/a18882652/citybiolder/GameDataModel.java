package com.kramer.a18882652.citybiolder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class GameDataModel
{
        private static GameDataModel instance;
        private SettingsModel settings;
        private int money;
        private int gameTime;
        private SQLiteDatabase db;
        private MapElement[][] map;

    private GameDataModel()
    {
        map = new MapElement[1][1];

    }

    private void load(Context context)
    {

        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();
        MapElement element = new MapElement(StructureData.getGameData().getRoad(1), null, "Thomas");
        MapElement element2 = new MapElement(StructureData.getGameData().getRoad(2), null, "Thomas");
        map[0][0] = element;
        map[0][1] = element2;
        map[1][0] = element;
        map[1][1] = element2;
        MapElement


    }

    public static GameDataModel getGameData()
    {
        if (instance == null)
            instance = new GameDataModel();

        return instance;
    }


}
