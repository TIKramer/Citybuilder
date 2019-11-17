package com.kramer.a18882652.citybiolder.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.kramer.a18882652.citybiolder.Database.GameDataSchema.*;
import com.kramer.a18882652.citybiolder.Model.Setting;
import com.kramer.a18882652.citybiolder.Model.SettingsModel;

import java.util.Map;

public class SettingCursor extends CursorWrapper {

    public SettingCursor(Cursor cursor){super(cursor);}

    public Map<String, Setting> getSettings()
    {
        SettingsModel model = SettingsModel.getInstance();

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

        model.addData(SettingsTable.Cols.CITY_NAME, name);
        model.addData(SettingsTable.Cols.MAP_WIDTH, map_width);
        model.addData(SettingsTable.Cols.MAP_HEIGHT, map_Height);
        model.addData(SettingsTable.Cols.INITIAL_MONEY, initialMoney);

        model.addData(SettingsTable.Cols.FAMILY_SIZE, familySize);
        model.addData(SettingsTable.Cols.SHOP_SIZE, shopSize);
        model.addData(SettingsTable.Cols.SALARY, salary);
        model.addData(SettingsTable.Cols.TAX_RATE, taxRate);
        model.addData(SettingsTable.Cols.SERVICE_COST, serviceCost);

        model.addData(SettingsTable.Cols.HOUSE_BUILDING_COST, houseBuildingcost);
        model.addData(SettingsTable.Cols.COMM_BUILDING_COST, commericalBuildingCost);
        model.addData(SettingsTable.Cols.ROAD_BUILDING_COST, roadBuildingcost);


        //Indicate the settings are locked and cant be changed by the user.
        model.lock(SettingsTable.Cols.INITIAL_MONEY);
        model.lock(SettingsTable.Cols.MAP_WIDTH);
        model.lock(SettingsTable.Cols.MAP_HEIGHT);


        return  model.getSettings();




    }
}
