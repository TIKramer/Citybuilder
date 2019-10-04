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
/**
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
            recView.setAdapter(new MapAdapter(getActivity(), GameDataModel.getGameData(getActivity()).getMap()));
            return view;
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




}
