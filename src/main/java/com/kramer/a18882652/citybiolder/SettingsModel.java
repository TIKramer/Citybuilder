package com.kramer.a18882652.citybiolder;

import android.provider.Settings;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.kramer.a18882652.citybiolder.GameDataSchema.*;

public class SettingsModel {


    private boolean newGame;
    private static final Map<String, Setting> settings;
    static  {
        settings = new LinkedHashMap<>();
        Setting cityName = new Setting("City Name", Setting.STRING);
        Setting map_width = new Setting("Map Width", Setting.INTERGER);
        Setting mapHeight = new Setting("Map Height", Setting.INTERGER);
        Setting initalMoney = new Setting("Inital Money", Setting.INTERGER);
        Setting familySize = new Setting("Family Size", Setting.INTERGER);
        Setting shopSize = new Setting("Shop Size", Setting.INTERGER);
        Setting salary = new Setting("Salary", Setting.INTERGER);
        Setting taxRate = new Setting("Tax Rate", Setting.FLOAT);
        Setting serviceCost = new Setting("Service Cost", Setting.INTERGER);
        Setting houseBuildingcost = new Setting("House Building Cost", Setting.INTERGER);
        Setting commericalBuildingCost = new Setting("Commerical Building Cost", Setting.INTERGER);
        Setting roadBuildingcost = new Setting("Road Building Cost", Setting.INTERGER);
        cityName.setLock(true);
        settings.put(SettingsTable.Cols.CITY_NAME, cityName);
        settings.put(SettingsTable.Cols.MAP_WIDTH, map_width);
        settings.put(SettingsTable.Cols.MAP_HEIGHT, mapHeight);
        settings.put(SettingsTable.Cols.INITIAL_MONEY, initalMoney);
        settings.put(SettingsTable.Cols.FAMILY_SIZE, familySize);
        settings.put(SettingsTable.Cols.SHOP_SIZE,shopSize);
        settings.put(SettingsTable.Cols.SALARY, salary);
        settings.put(SettingsTable.Cols.TAX_RATE,taxRate);
        settings.put(SettingsTable.Cols.SERVICE_COST, serviceCost);
        settings.put(SettingsTable.Cols.HOUSE_BUILDING_COST, houseBuildingcost);

        settings.put(SettingsTable.Cols.COMM_BUILDING_COST,commericalBuildingCost);
        settings.put(SettingsTable.Cols.ROAD_BUILDING_COST,roadBuildingcost);
    }

    public SettingsModel(Boolean isNewGame)
    {
        newGame = isNewGame;
    }



    public boolean isNewGame()
    {
        return newGame;
    }

    public void addData(String settingName, Object data)
    {
        settings.get(settingName).setData(data);
    }
    public String getData(String settingName)
    {
        return (String) settings.get(settingName).getData();
    }
    public static Map<String, Setting> getSettings()
    {
        return  settings;

    }
}
