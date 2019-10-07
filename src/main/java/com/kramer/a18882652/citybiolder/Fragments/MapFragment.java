package com.kramer.a18882652.citybiolder.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kramer.a18882652.citybiolder.Adapters.MapAdapter;
import com.kramer.a18882652.citybiolder.Model.GameDataModel;
import com.kramer.a18882652.citybiolder.R;

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
             recView = (RecyclerView) view.findViewById(R.id.mapRecycler);


            recView.setHasFixedSize(true);
            //use  GridLayout manager
            recView.setLayoutManager(new GridLayoutManager(getActivity(), 10, GridLayoutManager.HORIZONTAL, false));
            int spaceInPixels = 0;
            recView.addItemDecoration(new RecyclerViewItemDecorator(spaceInPixels));
            //set adapter
            mapAdapter = new MapAdapter(getActivity(), GameDataModel.getGameData(getActivity()).getMap(), 0,0,0, this);
            mapAdapter.setStructureUpdateListener(structureUpdateListener);
            mapAdapter.setCashCallBack(cashCallBack);
            recView.setAdapter(mapAdapter);
            return view;
        }

    public void setStructureUpdateListener(MapAdapter.StructureUpdateListener structureUpdateListener) {
        this.structureUpdateListener = structureUpdateListener;
    }


    public void setDemolish(boolean value) {
        mapAdapter.setDemolish(value);
    }


    public class RecyclerViewItemDecorator extends RecyclerView.ItemDecoration {
        private int spaceInPixels;

        public RecyclerViewItemDecorator(int spaceInPixels) {
            this.spaceInPixels = spaceInPixels;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view,
                                   RecyclerView parent, RecyclerView.State state) {
            outRect.left = spaceInPixels;
            outRect.right = spaceInPixels;
            outRect.bottom = spaceInPixels;

            if (parent.getChildLayoutPosition(view) == 0) {
                outRect.top = spaceInPixels;
            } else {
                outRect.top = 0;
            }
        }
    }




    public void setDetailsMode(boolean change)
    {
        mapAdapter.setDetailsMode(change);

    }

    public void setCashCallBack(MapAdapter.PlayerCashCallBack cashCallBack)
    {
        this.cashCallBack = cashCallBack;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        int row;
        int col;



        if (requestCode == 1 && resultCode == Activity.RESULT_OK)
        {
            String name = (String) data.getExtras().get("name");
            Bitmap photo = (Bitmap) data.getExtras().get("photo");
            col = (int) data.getExtras().get("col");
            row = (int) data.getExtras().get("row");


            mapAdapter.updateUsingIntent(row,col, name, photo);

        }
    }







}
