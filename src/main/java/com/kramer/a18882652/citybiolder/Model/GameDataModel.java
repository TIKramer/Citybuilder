package com.kramer.a18882652.citybiolder.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.kramer.a18882652.citybiolder.Database.GameDataCursor;
import com.kramer.a18882652.citybiolder.Database.GameDataDbHelper;
import com.kramer.a18882652.citybiolder.Database.GameDataSchema;
import com.kramer.a18882652.citybiolder.Database.MapDataCursor;
import com.kramer.a18882652.citybiolder.Database.SettingCursor;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Map;
/* This class is a singleton - it does all the loading and saving of data within the program


 */
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
    //There is one spot to store a temp bitmap
        //Used by the details screen - see details activity
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
//This will create a new map using the Settings width and Height
    public void createNewMap()
    {
       int row = Integer.valueOf("" +settings.get(GameDataSchema.SettingsTable.Cols.MAP_WIDTH).getData());
        int col = Integer.valueOf("" +settings.get(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT).getData());

        map = new MapElement[row][col];
        for(int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                map[i][j] = new MapElement(StructureData.getGameData().getDefault(), null);
            }
        }
    }
//Load a map from the database
    public void loadMap()
    {

        int col = Integer.valueOf("" +settings.get(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT).getData());

        MapDataCursor cursor = new MapDataCursor(
                db.query(GameDataSchema.MapElementsTable.NAME,null,null,null,null,null, GameDataSchema.MapElementsTable.Cols.LOCATION + " ASC", null)
        );
//Every time j == length of column
        //means we have searched an entire row
            //Go to next row by adding one to column and setting j to 0
        try
        {
            //If there is a map read from data base - other wise create a new one
            if(cursor.getCount() != 0) {
                map = new MapElement[Integer.valueOf("" +settings.get(GameDataSchema.SettingsTable.Cols.MAP_WIDTH).getData())][Integer.valueOf("" +settings.get(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT).getData())];

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


//Load settings
        //If one in database load that
            //otherwise create a new one
    private void loadSettings()
    {
        SettingCursor cursor = new SettingCursor(
                db.query(GameDataSchema.SettingsTable.NAME,null,null,null,null,null,null)
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
                settings.get(GameDataSchema.SettingsTable.Cols.MAP_WIDTH).setLock(true);
                settings.get(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT).setLock(true);
                settings.get(GameDataSchema.SettingsTable.Cols.INITIAL_MONEY).setLock(true);
                //change to get defults
            }
        }
        finally {
            cursor.close();
        }
    }

/*Load game data
        load from database if its there
            other set cash to IntialMoney setting
                and set game time to 0
 */

    public void loadGameData()
    {
        GameDataCursor cursor = new GameDataCursor(
                db.query(GameDataSchema.GameDataTable.NAME,null,null,null,null,null,null)
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
                cash = Integer.parseInt( "" + settings.get(GameDataSchema.SettingsTable.Cols.INITIAL_MONEY).getData());
                gameTime = 0;
            }
        }
        finally {
            cursor.close();
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

//Puts the user data into the database
    private void saveUserData()
    {
        ContentValues cv = new ContentValues();
        cv.put(GameDataSchema.GameDataTable.Cols.GAME_TIME,gameTime);
        cv.put(GameDataSchema.GameDataTable.Cols.MONEY,cash);
        cv.put(GameDataSchema.GameDataTable.Cols.NUMBER_OF_RESIDENTIAL,nResidential);
        cv.put(GameDataSchema.GameDataTable.Cols.NUMBER_OF_COMMERCIAL,nCommerical);
        cv.put(GameDataSchema.GameDataTable.Cols.NUMBER_OF_ROADS,nRoad);


        db.execSQL("delete from "+ GameDataSchema.GameDataTable.NAME);
        this.db.insert(GameDataSchema.GameDataTable.NAME,null,cv);

    }

    public void saveSettings()
    {
        ContentValues cv = new ContentValues();
        cv.put(GameDataSchema.SettingsTable.Cols.CITY_NAME, settings.get(GameDataSchema.SettingsTable.Cols.CITY_NAME).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.MAP_WIDTH,  settings.get(GameDataSchema.SettingsTable.Cols.MAP_WIDTH).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT, settings.get(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.INITIAL_MONEY,  settings.get(GameDataSchema.SettingsTable.Cols.INITIAL_MONEY).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.FAMILY_SIZE, settings.get(GameDataSchema.SettingsTable.Cols.FAMILY_SIZE).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.SHOP_SIZE,  settings.get(GameDataSchema.SettingsTable.Cols.SHOP_SIZE).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.SALARY, settings.get(GameDataSchema.SettingsTable.Cols.SALARY).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.TAX_RATE,  settings.get(GameDataSchema.SettingsTable.Cols.TAX_RATE).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.SERVICE_COST,  settings.get(GameDataSchema.SettingsTable.Cols.SERVICE_COST).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.HOUSE_BUILDING_COST, settings.get(GameDataSchema.SettingsTable.Cols.HOUSE_BUILDING_COST).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.COMM_BUILDING_COST, settings.get(GameDataSchema.SettingsTable.Cols.COMM_BUILDING_COST).getData().toString());
        cv.put(GameDataSchema.SettingsTable.Cols.ROAD_BUILDING_COST,  settings.get(GameDataSchema.SettingsTable.Cols.ROAD_BUILDING_COST).getData().toString());
        db.execSQL("delete from "+ GameDataSchema.SettingsTable.NAME);
        this.db.insert(GameDataSchema.SettingsTable.NAME,null,cv);


    }

/* to save the map insert each map element one by one in order
            order if got by going through the nested loop and incrementing count
 */
    public void saveMap()
    {
        int row = Integer.valueOf("" +settings.get(GameDataSchema.SettingsTable.Cols.MAP_WIDTH).getData());
        int col = Integer.valueOf("" +settings.get(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT).getData());
        db.execSQL("delete from "+ GameDataSchema.MapElementsTable.NAME);
        int count = 0;
        for(int i = 0; i <row ; i++) {
            for (int j = 0; j < col; j++)
            {
                MapElement currentElement = map[i][j];
                Bitmap userImage = currentElement.getImage();
                ContentValues cv = new ContentValues();
                cv.put(GameDataSchema.MapElementsTable.Cols.OWNER,currentElement.getOwnerName());


                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                if(userImage != null) {
                    userImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();
                   // userImage.recycle();
                    cv.put(GameDataSchema.MapElementsTable.Cols.IMAGE, byteArray);

                }
                else
                {
                    cv.put(GameDataSchema.MapElementsTable.Cols.IMAGE, "");

                }
                cv.put(GameDataSchema.MapElementsTable.Cols.STRUCTURE_IMAGE_ID, currentElement.getStructure().getStructureID());
                cv.put(GameDataSchema.MapElementsTable.Cols.STRUCTURE_TYPE, currentElement.getStructure().getClass().getSimpleName());
                cv.put(GameDataSchema.MapElementsTable.Cols.LOCATION, count);



                this.db.insert(GameDataSchema.MapElementsTable.NAME,null,cv);
                count ++;


            }

        }

    }



/* This sets the intial settings created by the SettingsActivity*/
    public void setSettings(Map<String, Setting> settings)
    {
        this.settings = settings;
        settings.get(GameDataSchema.SettingsTable.Cols.MAP_WIDTH).setLock(true);
        settings.get(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT).setLock(true);
        settings.get(GameDataSchema.SettingsTable.Cols.INITIAL_MONEY).setLock(true);


        saveSettings();
    }

    public Map<String, Setting>  getSettings()
    {
        return settings;
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
//Reset the entire data to defaults.
    //Deletes all entries in the database
    public void reset(Context context)
    {
        deleteDatabase();

        instance = new GameDataModel(context);
        SettingsModel.getInstance().resetSettings();

    }
    private void deleteDatabase()
    {
        db.execSQL("delete from "+ GameDataSchema.SettingsTable.NAME);
        db.execSQL("delete from "+ GameDataSchema.MapElementsTable.NAME);
        db.execSQL("delete from "+ GameDataSchema.GameDataTable.NAME);



    }


}
