package com.kramer.a18882652.citybiolder.Fragments;

import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.kramer.a18882652.citybiolder.Adapters.SelectorAdapter;
import com.kramer.a18882652.citybiolder.Model.Commercial;
import com.kramer.a18882652.citybiolder.Model.Residential;
import com.kramer.a18882652.citybiolder.Model.Road;
import com.kramer.a18882652.citybiolder.Model.StructureData;
import com.kramer.a18882652.citybiolder.R;

/* the selector fragment display the different strucutures to the user
  * Has UI controls for the user
  * Dragging structure from selector fragment onto the map
  * Demolish and details button
  * */


public class SelectorFragment extends Fragment  {
    private OnMapItemClickListener callback;
    private SelectorAdapter selectorAdapter;
    private StructureData data;
    private boolean demolishMode;
    private boolean detailsMode = false;
    private RecyclerView recView;
    private Spinner s;
    private OnDetailsChangeListener detailsModeListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

    data = StructureData.getGameData();
        // Inflate the layout for this fragment

        View view;
        view = inflater.inflate(R.layout.fragment_selector, container, false);
        final Button detailsBtn = (Button) view.findViewById(R.id.detailsBtn);


        recView = (RecyclerView) view.findViewById(R.id.selector_recyclerView);
        int spaceInPixels = 15;
        recView.addItemDecoration(new RecyclerViewItemDecorator(spaceInPixels));
        //use  GridLayout manager
        recView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        selectorAdapter = new SelectorAdapter(getActivity(), data.getResidential(), Residential.class.getSimpleName());

        recView.setAdapter(selectorAdapter);


        //Spinner chooses what structors are displayed to the user
        //Each selection changes the adapter of the Recycler view

        String[] arraySpinner = new String[] {
                "Residents", "Commerical", "Roads"
        };
        s = (Spinner) view.findViewById(R.id.structureSelector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if(position == 0) {
                    selectorAdapter.setData(data.getResidential(), Residential.class.getSimpleName());
                }
                else if(position ==1)
                {
                    selectorAdapter.setData(data.getCommercial(), Commercial.class.getSimpleName());
                }
                else if(position ==2)
                {
                    selectorAdapter.setData(data.getRoads(), Road.class.getSimpleName());

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });



        /*
        When a button is clicked I disable all other UI until a user clicks button again disabling the mode
        Also notify the appropriate listeners

         */
        final ImageView demolishImage = (ImageView) view.findViewById(R.id.demolishImage);
        demolishImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                demolishMode = !demolishMode;
                if(demolishMode)
                {
                    s.setEnabled(false);
                    recView.setEnabled(false);
                    demolishImage.setColorFilter(Color.BLUE);
                    demolishImage.invalidate();
                    detailsBtn.setEnabled(false);

                }
                else
                {
                    s.setEnabled(true);
                    recView.setEnabled(true);
                    demolishImage.clearColorFilter();
                    demolishImage.invalidate();
                    detailsBtn.setEnabled(true);

                }
                callback.demolishBuilding(demolishMode);

            }
        });

        detailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                detailsMode = !detailsMode;
                if(detailsMode)
                {
                    s.setEnabled(false);
                    recView.setEnabled(false);
                    detailsBtn.getBackground().setColorFilter(new LightingColorFilter(Color.BLUE, Color.BLUE));
                    detailsBtn.invalidate();
                    demolishImage.setEnabled(false);

                }
                else
                {
                    s.setEnabled(true);
                    recView.setEnabled(true);
                    detailsBtn.getBackground().clearColorFilter();
                    detailsBtn.invalidate();
                    demolishImage.setEnabled(true);

                }
                detailsModeListener.detailsChangeListener(detailsMode);

            }
        });
        return view;


    }


/* set on click listener */

    public void setOnDetailsChangeListener(OnDetailsChangeListener callback) {
        this.detailsModeListener = callback;
    }

    public void setOnMapItemClickListener(OnMapItemClickListener callback) {
        this.callback = callback;
    }


    public interface OnMapItemClickListener {
         void demolishBuilding(boolean value);
    }

    public interface OnDetailsChangeListener
    {
         void detailsChangeListener(boolean change);
    }








}
