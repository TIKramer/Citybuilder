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
        int nRoad;
        int nResidential;
        int nCommerical;

    private GameDataModel(Context context)
    {
        nRoad =0;
        nResidential =0;
        nCommerical =0;
        map = new MapElement[15][30];
        for(int i = 0; i < 15; i++) {
            for (int j = 0; j < 30; j++)
            {
                map[i][j] = new MapElement(StructureData.getGameData().getDefault(), null,"Thomas");
            }
        }
        load(context);

    }

    public MapElement[][] getMap()
    {
        return  map;
    }

    private void load(Context context)
    {

        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();

        loadSettings();


    }


    private void loadSettings()
    {
        SettingCursor cursor = new SettingCursor(
                db.query(SettingsTable.NAME,null,null,null,null,null,null)
        );

        try
        {
            if(cursor.getCount() != 0) {
                cursor.moveToFirst();
                settings = cursor.getSettings();
            }
            else
            {
                settings = SettingsModel.getInstance().getSettings();
            }
        }
        finally {
            cursor.close();
        }
    }

    public void changeTest()
    {
        nRoad =0;
        nResidential =0;
        nCommerical =0;
        map = new MapElement[15][30];
        for(int i = 0; i < 15; i++) {
            for (int j = 0; j < 30; j++)
            {
                map[i][j] = new MapElement(StructureData.getGameData().getRoad(2), null,"Thomas");
            }
        }

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
        cv.put(SettingsTable.Cols.FAMILY_SIZE, settings.get(SettingsTable.Cols.FAMILY_SIZE).getData().toString());
        cv.put(SettingsTable.Cols.SHOP_SIZE,  settings.get(SettingsTable.Cols.SHOP_SIZE).getData().toString());
        cv.put(SettingsTable.Cols.SALARY, settings.get(SettingsTable.Cols.SALARY).getData().toString());
        cv.put(SettingsTable.Cols.TAX_RATE,  settings.get(SettingsTable.Cols.TAX_RATE).getData().toString());
        cv.put(SettingsTable.Cols.SERVICE_COST,  settings.get(SettingsTable.Cols.SERVICE_COST).getData().toString());
        cv.put(SettingsTable.Cols.HOUSE_BUILDING_COST, settings.get(SettingsTable.Cols.HOUSE_BUILDING_COST).getData().toString());
        cv.put(SettingsTable.Cols.COMM_BUILDING_COST, settings.get(SettingsTable.Cols.COMM_BUILDING_COST).getData().toString());
        cv.put(SettingsTable.Cols.ROAD_BUILDING_COST,  settings.get(SettingsTable.Cols.ROAD_BUILDING_COST).getData().toString());
        db.execSQL("delete from "+ SettingsTable.NAME);
        this.db.insert(SettingsTable.NAME,null,cv);
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


    public String getSetting(String name)
    {
       return "" + settings.get(name).getData();
    }

    public void updateStructure(int x, int y, Structure structure)
    {
        Structure temp = map[x][y].getStructure();

        if(temp instanceof Road)
        {
            nRoad--;
        }
        else if(temp instanceof  Residential)
        {
            nResidential--;
        }
        else if(temp instanceof  Commercial)
        {
            nCommerical--;
        }

        map[x][y].setStructure(structure);
        if(structure instanceof Road)
        {
            nRoad++;
        }
        else if(structure instanceof  Residential)
        {
            nResidential++;
        }
        else if(structure instanceof  Commercial)
        {
            nCommerical++;
        }
    }
}
