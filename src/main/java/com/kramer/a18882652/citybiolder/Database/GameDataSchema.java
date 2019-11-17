package com.kramer.a18882652.citybiolder.Database;

import android.provider.Settings;

import java.util.Set;
//A Schema!
public class GameDataSchema {

    public static class SettingsTable
    {

        public static final String NAME = "Settings";
        public static class Cols
        {
            public static String CITY_NAME = "CITY_NAME";
            public static String MAP_WIDTH = "map_width";
            public static String MAP_HEIGHT = "map_height";
            public static String INITIAL_MONEY = "initial_money";
            public static String FAMILY_SIZE = "family_size";
            public static String SHOP_SIZE = "shop_size";
            public static String SALARY = "salary";
            public static String TAX_RATE = "tax_rate";
            public static String SERVICE_COST = "service_cost";
            public static String HOUSE_BUILDING_COST = "house_building_cost";
            public static String COMM_BUILDING_COST = "comm_building_cost";
            public static String ROAD_BUILDING_COST = "road_building_cost";


        }
    }

    public static class GameDataTable
    {

        public static final String NAME = "GameData";
        public static class Cols
        {
            public static String MONEY = "money";
            public static String GAME_TIME = "game_time";
            public static String NUMBER_OF_RESIDENTIAL = "number_of_residential";
            public static String NUMBER_OF_COMMERCIAL = "number_of_commercial";
            public static String NUMBER_OF_ROADS = "number_of_roads";


        }
    }

    public static class MapElementsTable
    {

        public static final String NAME = "MapTable";
        public static class Cols
        {
            public static String OWNER = "name";
            public static String IMAGE = "image";
            public static String STRUCTURE_TYPE = "type";
            public static String STRUCTURE_IMAGE_ID = "structure_image_id";
            public static String LOCATION = "location";



        }
    }






}
