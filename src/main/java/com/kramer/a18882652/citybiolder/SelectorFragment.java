package com.kramer.a18882652.citybiolder;

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

    public SelectorFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    View view;
    view = inflater.inflate(R.layout.fragment_selector, container, false);

        String[] arraySpinner = new String[] {
                "Residents", "Commerical", "Roads"
        };
        Spinner s = (Spinner) view.findViewById(R.id.structureSelector);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);


        RecyclerView recView = (RecyclerView) view.findViewById(R.id.selector_container);


        recView.setHasFixedSize(true);
        //use  GridLayout manager
        recView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL, false));

        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                            SelectorAdapter.setData();

                                        }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
                                    });



            //set adapter
        recView.setAdapter(new SelectorAdapter(getActivity(), GameDataModel.getGameData(getActivity()).getMap()));




        return view ;


    }





}
