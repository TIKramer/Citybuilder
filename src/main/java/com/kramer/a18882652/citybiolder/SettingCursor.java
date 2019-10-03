package com.kramer.a18882652.citybiolder;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.kramer.a18882652.citybiolder.GameDataSchema.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class SettingCursor extends CursorWrapper {

    public SettingCursor(Cursor cursor){super(cursor);}

    public Setting getSettings()
    {
        Map<String, Setting> settings = new LinkedHashMap<>();

        String name = getString(getColumnIndex(SettingsTable.Cols.CITY_NAME));
        int map_width = getInt(getColumnIndex(SettingsTable.Cols.MAP_WIDTH));
        int map_Height = getInt(getColumnIndex(SettingsTable.Cols.MAP_HEIGHT));
        int initialMoney = getInt(getColumnIndex(SettingsTable.Cols.INITIAL_MONEY));
        int familySize = getInt(getColumnIndex(SettingsTable.Cols.FAMILY_SIZE));
        int shopSize = getInt(getColumnIndex(SettingsTable.Cols.SHOP_SIZE));
        int salary = getInt(getColumnIndex(SettingsTable.Cols.SALARY));
        double taxRate = getDouble(getColumnIndex(SettingsTable.Cols.TAX_RATE));
        int serviceCost = getInt(getColumnIndex(SettingsTable.Cols.SERVICE_COST));
        int houseBuildingcost = getInt(getColumnIndex(SettingsTable.Cols.HOUSE_BUILDING_COST));
        int commericalBuildingCost = getInt(getColumnIndex(SettingsTable.Cols.COMM_BUILDING_COST));

        int roadBuildingcost = getInt(getColumnIndex(SettingsTable.Cols.ROAD_BUILDING_COST));



      getSettings();




    }
}
