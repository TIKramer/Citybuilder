package com.kramer.a18882652.citybiolder;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.kramer.a18882652.citybiolder.GameDataSchema.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class SettingCursor extends CursorWrapper {

    public SettingCursor(Cursor cursor){super(cursor);}

    public Map<String, Setting> getSettings()
    {

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

        SettingsModel.addData(SettingsTable.Cols.CITY_NAME, name);
        SettingsModel.addData(SettingsTable.Cols.MAP_WIDTH, map_width);
        SettingsModel.addData(SettingsTable.Cols.MAP_HEIGHT, map_Height);
        SettingsModel.addData(SettingsTable.Cols.INITIAL_MONEY, initialMoney);

        SettingsModel.addData(SettingsTable.Cols.FAMILY_SIZE, familySize);
        SettingsModel.addData(SettingsTable.Cols.SHOP_SIZE, shopSize);
        SettingsModel.addData(SettingsTable.Cols.SALARY, salary);
        SettingsModel.addData(SettingsTable.Cols.TAX_RATE, taxRate);
        SettingsModel.addData(SettingsTable.Cols.SERVICE_COST, serviceCost);

        SettingsModel.addData(SettingsTable.Cols.HOUSE_BUILDING_COST, houseBuildingcost);
        SettingsModel.addData(SettingsTable.Cols.COMM_BUILDING_COST, commericalBuildingCost);
        SettingsModel.addData(SettingsTable.Cols.ROAD_BUILDING_COST, roadBuildingcost);



        SettingsModel.lock(SettingsTable.Cols.INITIAL_MONEY);
        SettingsModel.lock(SettingsTable.Cols.MAP_WIDTH);
        SettingsModel.lock(SettingsTable.Cols.MAP_HEIGHT);


        return  SettingsModel.getSettings();




    }
}
