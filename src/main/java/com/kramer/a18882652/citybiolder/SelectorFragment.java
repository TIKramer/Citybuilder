package com.kramer.a18882652.citybiolder;

import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;


public class SelectorFragment extends Fragment {
    private OnHeadlineSelectedListener callback;
    private SelectorAdapter selectorAdapter;
    private StructureData data;
    public SelectorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    data = StructureData.getGameData();
        // Inflate the layout for this fragment
        View view;
        view = inflater.inflate(R.layout.fragment_selector, container, false);


        RecyclerView recView = (RecyclerView) view.findViewById(R.id.selector_recyclerView);
        int spaceInPixels = 15;
        recView.addItemDecoration(new RecyclerViewItemDecorator(spaceInPixels));
        //use  GridLayout manager
        recView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        selectorAdapter = new SelectorAdapter(getActivity(), data.getResidential(), 0);

        recView.setAdapter(selectorAdapter);


        //set adapter

        String[] arraySpinner = new String[] {
                "Residents", "Commerical", "Roads"
        };
        Spinner s = (Spinner) view.findViewById(R.id.structureSelector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(position == 0) {
                    selectorAdapter.setData(data.getResidential(), 0);
                }
                else if(position ==1)
                {
                    selectorAdapter.setData(data.getCommercial(), 1);
                }
                else if(position ==2)
                {
                    selectorAdapter.setData(data.getRoads(), 2);
                    callback.onArticleSelected(0,0);

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


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
            outRect.top = spaceInPixels;
            outRect.bottom = spaceInPixels;




        }


    }

    public void setOnHeadlineSelectedListener(OnHeadlineSelectedListener callback) {
        this.callback = callback;
    }

    // This interface can be implemented by the Activity, parent Fragment,
    // or a separate test implementation.
    public interface OnHeadlineSelectedListener {
        public void onArticleSelected(int x, int y);
    }

}
