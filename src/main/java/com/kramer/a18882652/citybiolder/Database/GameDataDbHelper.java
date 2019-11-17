package com.kramer.a18882652.citybiolder.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class GameDataDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "GameData.db";

    public GameDataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_TABLE_SETTINGS);
        db.execSQL(SQL_CREATE_TABLE_GAME_DATA);
        db.execSQL(SQL_CREATE_TABLE_MAP_ELEMENTS);


    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int v1, int v2) {
    }


    private static final String SQL_CREATE_TABLE_SETTINGS =
            "CREATE TABLE IF NOT EXISTS " + GameDataSchema.SettingsTable.NAME +
                    " (" +
                    GameDataSchema.SettingsTable.Cols.CITY_NAME + " TEXT, " +
                    GameDataSchema.SettingsTable.Cols.MAP_WIDTH + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.MAP_HEIGHT + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.INITIAL_MONEY + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.FAMILY_SIZE + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.SHOP_SIZE + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.SALARY + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.TAX_RATE + " REAL, " +
                    GameDataSchema.SettingsTable.Cols.SERVICE_COST + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.HOUSE_BUILDING_COST + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.COMM_BUILDING_COST + " INTEGER, " +
                    GameDataSchema.SettingsTable.Cols.ROAD_BUILDING_COST + " INTEGER)";

    private static final String SQL_CREATE_TABLE_GAME_DATA =
            "CREATE TABLE IF NOT EXISTS " + GameDataSchema.GameDataTable.NAME +
                    " (" +
                    GameDataSchema.GameDataTable.Cols.GAME_TIME + " INTEGER, " +
                    GameDataSchema.GameDataTable.Cols.MONEY + " INTEGER, " +
                    GameDataSchema.GameDataTable.Cols.NUMBER_OF_RESIDENTIAL + " INTEGER, " +
                    GameDataSchema.GameDataTable.Cols.NUMBER_OF_COMMERCIAL + " INTEGER, " +
                    GameDataSchema.GameDataTable.Cols.NUMBER_OF_ROADS + " INTEGER)";

    private static final String SQL_CREATE_TABLE_MAP_ELEMENTS =
            "CREATE TABLE IF NOT EXISTS " + GameDataSchema.MapElementsTable.NAME +
                    " (" +
                    GameDataSchema.MapElementsTable.Cols.OWNER + " TEXT, " +
                    GameDataSchema.MapElementsTable.Cols.IMAGE + " BLOB, " +
                    GameDataSchema.MapElementsTable.Cols.STRUCTURE_TYPE + " TEXT, " +
                    GameDataSchema.MapElementsTable.Cols.STRUCTURE_IMAGE_ID + " INTEGER, " +
                    GameDataSchema.MapElementsTable.Cols.LOCATION + " INTEGER)";

}