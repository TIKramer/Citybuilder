package com.kramer.a18882652.citybuilder.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kramer.a18882652.citybuilder.R;


public class UserStatusFragment extends android.support.v4.app.Fragment {

    public UserStatusFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_status, container, false);
    }

    public void updateCash(int cash)
    {
        TextView text = (TextView) getActivity().findViewById(R.id.currentCashTxt);
        text.setText("Cash: " + cash);
    }
    public void updateIncome(int cash)
    {
        TextView text = (TextView) getActivity().findViewById(R.id.lastEarnedCashTxt);
        text.setText("Income: " + cash);
    }

    public void updateTime(int time)
    {
        TextView text = (TextView) getActivity().findViewById(R.id.currentGameTime);
        text.setText("Time: " + time);
    }

    public void updatePopulation(int population)
    {
        TextView text = (TextView) getActivity().findViewById(R.id.populationTxt);
        text.setText("Population: " + population);
    }

    public void updateEmploymentRate(double employmentRate)
    {
        TextView text = (TextView) getActivity().findViewById(R.id.employmentTxt);
        text.setText("Employment Rate: " + employmentRate + "%");
    }


}
