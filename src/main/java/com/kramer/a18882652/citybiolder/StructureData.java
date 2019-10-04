package com.kramer.a18882652.citybiolder;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class StructureData {

    private static StructureData instance;
    private List<Road> roads;
    private List<Residential> residential;
    private List<Commercial> commercial;
    private int[] roadID = {R.drawable.ic_road_e, R.drawable.ic_road_ew, R.drawable.ic_road_n, R.drawable.ic_road_ne,
                             R.drawable.ic_road_new, R.drawable.ic_road_ns, R.drawable.ic_road_nse, R.drawable.ic_road_nsew, R.drawable.ic_road_nsw};

    private int[] commericalId = {R.drawable.ic_building1, R.drawable.ic_building2, R.drawable.ic_building3, R.drawable.ic_building4, R.drawable.ic_building5,
            R.drawable.ic_building6, R.drawable.ic_building7, R.drawable.ic_building8};


    private int[] residentialId = {R.drawable.ic_building1, R.drawable.ic_building2, R.drawable.ic_building3, R.drawable.ic_building4, R.drawable.ic_building5,
            R.drawable.ic_building6, R.drawable.ic_building7, R.drawable.ic_building8};

    private StructureData()
    {
        roads = new ArrayList<Road>();
        residential = new ArrayList<Residential>();
        commercial = new ArrayList<Commercial>();
        createRoads();
        createResidential();
        createCommercial();
    }

    private void createRoads()
    {
        for(int id : roadID)
        {
            roads.add(new Road(id));
        }
    }

    private void createResidential()
    {
        for(int id : residentialId)
        {
            residential.add(new Residential(id));
        }
    }

    private void createCommercial()
    {
        for(int id : residentialId)
        {
            residential.add(new Residential(id));
        }
    }

    public static StructureData getGameData()
    {
        if (instance == null)
            instance = new StructureData();

        return instance;
    }

    public Road getRoad(int i)
    {
        return roads.get(i);
    }
    public Residential getResidential(int i)
    {
        return residential.get(i);
    }

}
