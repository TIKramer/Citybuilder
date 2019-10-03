package com.kramer.a18882652.citybiolder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.kramer.a18882652.citybiolder.GameDataSchema.*;
import java.util.List;
import java.util.Map;

public class GameDataModel
{
        private static GameDataModel instance;
        private Map<String, Setting> settings;
        private int money;
        private int gameTime;
        private SQLiteDatabase db;
        private MapElement[][] map;

    private GameDataModel(Context context)
    {
        map = new MapElement[1][1];
        load(context);

    }

    private void load(Context context)
    {

        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();
       // MapElement element = new MapElement(StructureData.getGameData().getRoad(1), null, "Thomas");
       // MapElement element2 = new MapElement(StructureData.getGameData().getRoad(2), null, "Thomas");
     //   map[0][0] = element;
      //  map[0][1] = element2;
       // map[1][0] = element;
      //  map[1][1] = element2;


    }

    public static GameDataModel getGameData(Context context)
    {
        if (instance == null)
            instance = new GameDataModel(context);

        return instance;
    }

    private void saveSettings()
    {
        ContentValues cv = new ContentValues();
        cv.put(SettingsTable.Cols.CITY_NAME, settings.get(SettingsTable.Cols.CITY_NAME).getData().toString());
        cv.put(SettingsTable.Cols.MAP_WIDTH,  settings.get(SettingsTable.Cols.MAP_WIDTH).getData().toString());
        cv.put(SettingsTable.Cols.MAP_HEIGHT, settings.get(SettingsTable.Cols.MAP_HEIGHT).getData().toString());
        cv.put(SettingsTable.Cols.INITIAL_MONEY,  settings.get(SettingsTable.Cols.INITIAL_MONEY).getData().toString());
        cv.put(SettingsTable.Cols.FAMILY_SIZE, settings.get(SettingsTable.Cols.FAMILY_SIZE).toString());
        cv.put(SettingsTable.Cols.SHOP_SIZE,  settings.get(SettingsTable.Cols.SHOP_SIZE).toString());
        cv.put(SettingsTable.Cols.SALARY, settings.get(SettingsTable.Cols.SALARY).toString());
        cv.put(SettingsTable.Cols.TAX_RATE,  settings.get(SettingsTable.Cols.TAX_RATE).toString());
        cv.put(SettingsTable.Cols.SERVICE_COST,  settings.get(SettingsTable.Cols.SERVICE_COST).getData().toString());
        cv.put(SettingsTable.Cols.HOUSE_BUILDING_COST, settings.get(SettingsTable.Cols.HOUSE_BUILDING_COST).getData().toString());
        cv.put(SettingsTable.Cols.COMM_BUILDING_COST, settings.get(SettingsTable.Cols.COMM_BUILDING_COST).getData().toString());
        cv.put(SettingsTable.Cols.COMM_BUILDING_COST,  settings.get(SettingsTable.Cols.COMM_BUILDING_COST).getData().toString());

        this.db.replace(SettingsTable.NAME,null,cv);
    }

    public void setSettings(Map<String, Setting> settings)
    {
        this.settings = settings;
        saveSettings();
    }

    public Map<String, Setting>  getSettings()
    {
        return settings;
    }
}
