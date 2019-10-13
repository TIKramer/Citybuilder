package com.kramer.a18882652.citybuilder.Model;

import com.kramer.a18882652.citybuilder.R;

import java.util.ArrayList;
import java.util.List;

/* This class just stores all the types of structures
    Other methods can call the get methods to get a specified structure

 */


public class StructureData {

    private static StructureData instance;
    private List<Road> roads;
    private List<Residential> residential;
    private List<Commercial> commercial;
    private final DefaultStructure defaultStuct = new DefaultStructure();
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
        int count = 0;
        for(int id : roadID)
        {
            Road road = new Road(id);
            road.setStructureID(count);
            roads.add(road);
            count ++;
        }
    }

    private void createResidential()
    {
        int count =0;
        for(int id : residentialId)
        {
            Residential res = new Residential(id);
            res.setStructureID(count);
            residential.add(res);
            count ++;
        }
    }

    private void createCommercial()
    {
        int count = 0;
        for(int id : commericalId)
        {
            Commercial com = new Commercial(id);
            com.setStructureID(count);
            commercial.add(com);
            count ++;
        }
    }

    public static StructureData getGameData()
    {
        if (instance == null)
            instance = new StructureData();

        return instance;
    }

    public DefaultStructure getDefault()
    {
        return defaultStuct;
    }

    public List<Road> getRoads()
    {
        return roads;
    }

    public List<Residential> getResidential()
    {
        return residential;
    }

    public List<Commercial> getCommercial()
    {
        return commercial;
    }

    public Commercial getCommercial(int i){return commercial.get(i);};
    public Road getRoad(int i)
    {
        return roads.get(i);
    }
    public Residential getResidential(int i)
    {
        return residential.get(i);
    }


    public Structure getByNameAndId(String type, int id)
    {
        Structure structure = null;
        if(type.equals(Residential.class.getSimpleName()))
            {
                structure = getResidential(id);
            }
        else if(type.equals(Commercial.class.getSimpleName()))
            {
                structure = getCommercial(id);
            }
       else if(type.equals(Road.class.getSimpleName()))
            {
                structure = getRoad(id);
            }
            else if(type.equals(DefaultStructure.class.getSimpleName()))
        {
            structure = getDefault();
        }
            return structure;
    }

}

