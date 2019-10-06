package com.kramer.a18882652.citybiolder;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapFragment extends android.support.v4.app.Fragment {

    private MapAdapter mapAdapter;
    private MapAdapter.RecyclerViewClickListener callback;
    MapAdapter.RoadUpdateListener roadCallBack;
    MapAdapter.ResidentialUpdateListener residentialCallBack;
    MapAdapter.CommercialUpdateListener commericalCallBack;
    MapAdapter.PlayerCashCallBack cashCallBack;


    public MapFragment() {
        // Required empty public constructor
    }



        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            View view = inflater.inflate(R.layout.fragment_map, container, false);
            RecyclerView recView = (RecyclerView) view.findViewById(R.id.mapRecycler);


            recView.setHasFixedSize(true);
            //use  GridLayout manager
            recView.setLayoutManager(new GridLayoutManager(getActivity(), 10, GridLayoutManager.HORIZONTAL, false));
            int spaceInPixels = 0;
            recView.addItemDecoration(new RecyclerViewItemDecorator(spaceInPixels));
            //set adapter
            mapAdapter = new MapAdapter(getActivity(), GameDataModel.getGameData(getActivity()).getMap(), 0,0,0);
            mapAdapter.setCallBack(callback);
            mapAdapter.setCommericalCallBack(commericalCallBack);
            mapAdapter.setResidentialCallBack(residentialCallBack);
            mapAdapter.setRoadCallBack(roadCallBack);
            mapAdapter.setCashCallBack(cashCallBack);
            recView.setAdapter(mapAdapter);
            return view;
        }

    public void setRoadUpdateListener(MapAdapter.RoadUpdateListener roadUpdateListener) {
        this.roadCallBack = roadUpdateListener;
    }

    public void setResidentialUpdateListener(MapAdapter.ResidentialUpdateListener residentialUpdateListener) {
        this.residentialCallBack = residentialUpdateListener;
    }

    public void setCommercialUpdateListener(MapAdapter.CommercialUpdateListener commercialUpdateListener) {
        this.commericalCallBack = commercialUpdateListener;
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


    public void demolishStructure(int x, int y)
    {
        mapAdapter.removeFromLocation(x,y);

    }
    public void setRecyclerViewClickListener(MapAdapter.RecyclerViewClickListener callback) {
        this.callback = callback;
    }

    public void setCashCallBack(MapAdapter.PlayerCashCallBack cashCallBack)
    {
        this.cashCallBack = cashCallBack;
    }






}
