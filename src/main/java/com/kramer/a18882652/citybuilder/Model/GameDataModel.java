package com.kramer.a18882652.citybuilder.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.kramer.a18882652.citybuilder.Database.GameDataCursor;
import com.kramer.a18882652.citybuilder.Database.GameDataDbHelper;
import com.kramer.a18882652.citybuilder.Database.GameDataSchema.GameDataTable;
import com.kramer.a18882652.citybuilder.Database.GameDataSchema.MapElementsTable;
import com.kramer.a18882652.citybuilder.Database.GameDataSchema.SettingsTable;
import com.kramer.a18882652.citybuilder.Database.MapDataCursor;
import com.kramer.a18882652.citybuilder.Database.SettingCursor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;

public class GameDataModel
{
        private static GameDataModel instance;
        private Map<String, Setting> settings;
        private int cash;
        private int gameTime;
        private SQLiteDatabase db;
        private MapElement[][] map;
        int nRoad;
        int nResidential;
        int nCommerical;
        private Bitmap temp;


    private GameDataModel(Context context)
    {
        nRoad =0;
        nResidential =0;
        nCommerical =0;


        load(context);

    }

    public Bitmap getTemp() {
        return temp;
    }
    public void setTemp(Bitmap bitmap)
    {
        temp = bitmap;
    }

    public MapElement[][] getMap()
    {
        return  map;
    }

    private void load(Context context)
    {

        this.db = new GameDataDbHelper(context.getApplicationContext()).getWritableDatabase();

        loadSettings();
        loadGameData();

    }

    public void createNewMap()
    {
       int row = Integer.valueOf("" +settings.get(SettingsTable.Cols.MAP_WIDTH).getData());
        int col = Integer.valueOf("" +settings.get(SettingsTable.Cols.MAP_HEIGHT).getData());

        map = new MapElement[row][col];
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = new MapElement(StructureData.getGameData().getDefault(), null);
            }
        }
    }

    public void loadMap()
    {

        int row = Integer.valueOf("" +settings.get(SettingsTable.Cols.MAP_WIDTH).getData());
        int col = Integer.valueOf("" +settings.get(SettingsTable.Cols.MAP_HEIGHT).getData());

        MapDataCursor cursor = new MapDataCursor(
                db.query(MapElementsTable.NAME,null,null,null,null,null,MapElementsTable.Cols.LOCATION + " ASC", null)
        );

        try
        {

            if(cursor.getCount() != 0) {
                map = new MapElement[Integer.valueOf("" +settings.get(SettingsTable.Cols.MAP_WIDTH).getData())][Integer.valueOf("" +settings.get(SettingsTable.Cols.MAP_HEIGHT).getData())];

                int i = 0;
                int j = 0;
                cursor.moveToFirst();
                while(!cursor.isAfterLast()) {

                    map[i][j] = cursor.getGameData();

                    cursor.moveToNext();
                    j++;
                    if(j == col)
                    {
                        j =0;
                        i++;
                    }
                    }

                }

            else
            {
              createNewMap();
                //change to get defults
            }
        }
        finally {
            cursor.close();
        }
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
                settings.get(SettingsTable.Cols.MAP_WIDTH).setLock(true);
                settings.get(SettingsTable.Cols.MAP_HEIGHT).setLock(true);
                settings.get(SettingsTable.Cols.INITIAL_MONEY).setLock(true);
                //change to get defults
            }
        }
        finally {
            cursor.close();
        }
    }



    public void loadGameData()
    {
        GameDataCursor cursor = new GameDataCursor(
                db.query(GameDataTable.NAME,null,null,null,null,null,null)
        );

        try
        {
            if(cursor.getCount() != 0) {
                cursor.moveToFirst();
                ArrayList<Integer> list = cursor.getGameData();
                gameTime = list.get(0);

                cash = list.get(1);
                nResidential = list.get(2);
                nCommerical = list.get(3);
                nRoad = list.get(4);
            }
            else
            {
                cash = Integer.parseInt( "" + settings.get(SettingsTable.Cols.INITIAL_MONEY).getData());
                gameTime = 0;
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
                map[i][j] = new MapElement(StructureData.getGameData().getRoad(2), null);
            }
        }

    }
    public static GameDataModel getGameData(Context context)
    {
        if (instance == null)
            instance = new GameDataModel(context);

        return instance;
    }
    public void setGameTime(int time)
    {
        gameTime = time;
    }
    public int getGameTime()
    {
        return gameTime;
    }

    public void setUserMoney(int cash)
    {
        this.cash = cash;
    }
    public int getUserMoney()
    {
        return this.cash;
    }


    private void saveUserData()
    {
        ContentValues cv = new ContentValues();
        cv.put(GameDataTable.Cols.GAME_TIME,gameTime);
        cv.put(GameDataTable.Cols.MONEY,cash);
        cv.put(GameDataTable.Cols.NUMBER_OF_RESIDENTIAL,nResidential);
        cv.put(GameDataTable.Cols.NUMBER_OF_COMMERCIAL,nCommerical);
        cv.put(GameDataTable.Cols.NUMBER_OF_ROADS,nRoad);


        db.execSQL("delete from "+ GameDataTable.NAME);
        this.db.insert(GameDataTable.NAME,null,cv);

    }

    public void saveSettings()
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


    public void saveMap()
    {
        int row = Integer.valueOf("" +settings.get(SettingsTable.Cols.MAP_WIDTH).getData());
        int col = Integer.valueOf("" +settings.get(SettingsTable.Cols.MAP_HEIGHT).getData());
        db.execSQL("delete from "+ MapElementsTable.NAME);
        int count = 0;
        for(int i = 0; i <row ; i++) {
            for (int j = 0; j < col; j++)
            {
                MapElement currentElement = map[i][j];
                Bitmap userImage = currentElement.getImage();
                ContentValues cv = new ContentValues();
                cv.put(MapElementsTable.Cols.OWNER,currentElement.getOwnerName());


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if(userImage != null) {
                    userImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                   // userImage.recycle();
                    cv.put(MapElementsTable.Cols.IMAGE, byteArray);

                }
                else
                {
                    cv.put(MapElementsTable.Cols.IMAGE, "");

                }
                cv.put(MapElementsTable.Cols.STRUCTURE_IMAGE_ID, currentElement.getStructure().getStructureID());
                cv.put(MapElementsTable.Cols.STRUCTURE_TYPE, currentElement.getStructure().getClass().getSimpleName());
                cv.put(MapElementsTable.Cols.LOCATION, count);



                this.db.insert(MapElementsTable.NAME,null,cv);
                count ++;


            }

        }

    }




    public void setSettings(Map<String, Setting> settings)
    {
        this.settings = settings;
        settings.get(SettingsTable.Cols.MAP_WIDTH).setLock(true);
        settings.get(SettingsTable.Cols.MAP_HEIGHT).setLock(true);
        settings.get(SettingsTable.Cols.INITIAL_MONEY).setLock(true);


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


    public void setStructureNumber(int nResidential, int nCommerical, int nRoad)
    {
        this.nResidential = nResidential;
        this.nCommerical = nCommerical;
        this.nRoad = nRoad;
    }




    public void setMap(MapElement[][] map) {
        this.map = map;

    }


    public void saveGameData()
    {
        saveUserData();
    }

    public int getnResidential()
    {
        return nResidential;
    }

    public int getnCommerical()
    {
        return  nCommerical;
    }

    public int getnRoads()
    {
        return nRoad;
    }

    public void reset(Context context)
    {
        SettingsModel.getInstance().resetSettings();
        deleteDatabase();

        instance = new GameDataModel(context);
    }
    private void deleteDatabase()
    {
        db.execSQL("delete from "+ SettingsTable.NAME);
        db.execSQL("delete from "+ MapElementsTable.NAME);
        db.execSQL("delete from "+ GameDataTable.NAME);



    }


}
