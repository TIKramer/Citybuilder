package com.kramer.a18882652.citybuilder.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kramer.a18882652.citybuilder.Adapters.MapAdapter;
import com.kramer.a18882652.citybuilder.Database.GameDataSchema;
import com.kramer.a18882652.citybuilder.Model.GameDataModel;
import com.kramer.a18882652.citybuilder.Model.SettingsModel;
import com.kramer.a18882652.citybuilder.R;

public class MapFragment extends android.support.v4.app.Fragment  {

    private MapAdapter mapAdapter;
    RecyclerView recView;
    MapAdapter.StructureUpdateListener structureUpdateListener;
    MapAdapter.PlayerCashCallBack cashCallBack;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_map, container, false);
             recView = view.findViewById(R.id.mapRecycler);
            SettingsModel settings = SettingsModel.getInstance();

            int col = Integer.valueOf("" +settings.getData(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT));

            recView.setHasFixedSize(true);
            //use  GridLayout manager
            recView.setLayoutManager(new GridLayoutManager(getActivity(), col, GridLayoutManager.HORIZONTAL, false));
            int spaceInPixels = 0;
            recView.addItemDecoration(new RecyclerViewItemDecorator(spaceInPixels));
            initializeAdapter();
            recView.setAdapter(mapAdapter);
            return view;
        }


        private void initializeAdapter()
        {

            GameDataModel model = GameDataModel.getGameData(getActivity());
            SettingsModel settings = SettingsModel.getInstance();
            int row = Integer.valueOf("" +settings.getData(GameDataSchema.SettingsTable.Cols.MAP_WIDTH));
            int col = Integer.valueOf("" +settings.getData(GameDataSchema.SettingsTable.Cols.MAP_HEIGHT));
            mapAdapter = new MapAdapter(getActivity(), model.getMap(), this, row, col);
            mapAdapter.setStructureUpdateListener(structureUpdateListener);
            mapAdapter.setCashCallBack(cashCallBack);
            mapAdapter.setStructureValues(model.getnResidential(), model.getnCommerical(), model.getnResidential());

        }

    public void setStructureUpdateListener(MapAdapter.StructureUpdateListener structureUpdateListener) {
        this.structureUpdateListener = structureUpdateListener;
    }






//Tell the adapter that the details tool has been selected.
    public void setDetailsMode(boolean change) { mapAdapter.setDetailsMode(change); }
//Tell the adapter that the demolish tool has been selected.
    public void setDemolish(boolean value) {
        mapAdapter.setDemolish(value);
    }


    public void setCashCallBack(MapAdapter.PlayerCashCallBack cashCallBack) { this.cashCallBack = cashCallBack; }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        int row;
        int col;
        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            String name = (String) data.getExtras().get("name");
            col = (int) data.getExtras().get("col");
            row = (int) data.getExtras().get("row");
            mapAdapter.updateUsingIntent(row,col, name);
            int pos = col * 15 + row;
            mapAdapter.notifyItemChanged(pos);

        }
    }
/* on pause and on stop
 *  Save the data to the model and call the save method
 *  I did not want to update directly to model in-case model gets changed else where -vh wont be updated
 */

    @Override
    public void onPause()
    {
        super.onPause();
        GameDataModel model = GameDataModel.getGameData(getActivity());

        model.setMap(mapAdapter.getElements());
        model.saveMap();
    }

    @Override
    public void onStop()
    {
        super.onStop();
        GameDataModel model = GameDataModel.getGameData(getActivity());
        model.setMap(mapAdapter.getElements());
        model.saveMap();
    }

}
