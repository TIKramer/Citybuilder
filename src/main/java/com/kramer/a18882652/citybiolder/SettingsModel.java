package com.kramer.a18882652.citybiolder;

import android.provider.Settings;

public class SettingsModel {

    private String cityName;
    private int map_Width;
    private int map_Height;
    private int initalMoney;
    private int familySize;
    private int shopSize;
    private int salary;
    private double taxRate;
    private int serviceCost;
    private int houseBuildingcost;
    private int commBuildingCost;
    private int roadBuildingCost;
    private boolean newGame;


    public SettingsModel(Boolean isNewGame)
    {
        newGame = isNewGame;
    }



    public boolean isNewGame()
    {
        return newGame;
    }
}
