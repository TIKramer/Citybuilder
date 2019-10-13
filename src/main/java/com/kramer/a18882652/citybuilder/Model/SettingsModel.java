package com.kramer.a18882652.citybuilder.Model;

import com.kramer.a18882652.citybuilder.Database.GameDataSchema.SettingsTable;

import java.util.LinkedHashMap;
import java.util.Map;

public class SettingsModel {

    private final String NAME_DEFAULT = "City";
    private final int MAP_WIDTH_DEFAULT = 50;
    private final int MAP_HEIGHT_DEFAULT = 10;
    private final int INITIAL_MONEY_DEFAULT = 1000;
    private final int FAMILY_SIZE_DEFAULT = 4;
    private final int SHOP_SIZE_DEFAULT = 6;
    private final int SALARY_DEFAULT =10;
    private final double TAX_RATE_DEFAULT = 0.3;
    private final int SERVICE_COST_DEFAULT = 2;
    private final int HOUSE_BUILDING_COST_DEFAULT = 100;
    private final int COMM_BUILDING_COST = 500;
    private final int ROAD_BUILDING_COST = 20;





    private boolean newGame;
    private  Map<String, Setting> settings;
  private static SettingsModel instance;

    public SettingsModel(Boolean isNewGame)
    {
        newGame = isNewGame;
    }

    private SettingsModel()
    {
        create();
    }

    public static SettingsModel getInstance()
    {
        if(instance ==null)
        {
           instance = new SettingsModel();
        }
        return instance;
    }


    public void resetSettings()
    {
        instance = new SettingsModel();
    }

    private void create()
    {
        settings = new LinkedHashMap<>();
        Setting cityName = new Setting("City Name", Setting.STRING);
        cityName.setData(NAME_DEFAULT);
        Setting map_width = new Setting("Map Width", Setting.INTERGER);
        map_width.setData(MAP_WIDTH_DEFAULT);
        Setting mapHeight = new Setting("Map Height", Setting.INTERGER);
        mapHeight.setData(MAP_HEIGHT_DEFAULT);

        Setting initalMoney = new Setting("Inital Money", Setting.INTERGER);
                initalMoney.setData(INITIAL_MONEY_DEFAULT);
        Setting familySize = new Setting("Family Size", Setting.INTERGER);
        familySize.setData(FAMILY_SIZE_DEFAULT);
        Setting shopSize = new Setting("Shop Size", Setting.INTERGER);
        shopSize.setData(SHOP_SIZE_DEFAULT);
        Setting salary = new Setting("Salary", Setting.INTERGER);
        salary.setData(SALARY_DEFAULT);
        Setting taxRate = new Setting("Tax Rate", Setting.FLOAT);
        taxRate.setData(TAX_RATE_DEFAULT);
        Setting serviceCost = new Setting("Service Cost", Setting.INTERGER);
        serviceCost.setData(SERVICE_COST_DEFAULT);
        Setting houseBuildingcost = new Setting("House Building Cost", Setting.INTERGER);
        houseBuildingcost.setData(HOUSE_BUILDING_COST_DEFAULT);
        Setting commericalBuildingCost = new Setting("Commerical Building Cost", Setting.INTERGER);
        commericalBuildingCost.setData(COMM_BUILDING_COST);
        Setting roadBuildingcost = new Setting("Road Building Cost", Setting.INTERGER);
        roadBuildingcost.setData(ROAD_BUILDING_COST);
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

    public boolean isNewGame()
    {
        return newGame;
    }

    public void addData(String settingName, Object data)
    {
        settings.get(settingName).setData(data);
    }
    public Object getData(String settingName)
    {
        return  settings.get(settingName).getData();
    }

    public void lock(String settingName)
    {
          settings.get(settingName).setLock(true);
    }
    public  Map<String, Setting> getSettings()
    {
        return  settings;

    }
}
