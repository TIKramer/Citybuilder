package com.kramer.a18882652.citybiolder;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.app.Fragment;

public class GameActivity extends AppCompatActivity implements SelectorFragment.OnHeadlineSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFrag = (MapFragment) fm.findFragmentById(R.id.map_container);
        SelectorFragment selectorFrag = (SelectorFragment) fm.findFragmentById(R.id.selector_container);


        if(mapFrag == null)
        {
            mapFrag = new MapFragment();
            fm.beginTransaction().add(R.id.map_container, mapFrag).commit();
        }
        if(selectorFrag ==null)
        {
            selectorFrag = new SelectorFragment();
            fm.beginTransaction().add(R.id.selector_container,selectorFrag).commit();
        }
    }

    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        if (fragment instanceof SelectorFragment) {
            SelectorFragment headlinesFragment = (SelectorFragment) fragment;
            headlinesFragment.setOnHeadlineSelectedListener(this);
        }
    }

    @Override
    public void onArticleSelected(int x, int y) {
        {
            // The user selected the headline of an article from the HeadlinesFragment
            // Do something here to display that article

            MapFragment articleFrag = (MapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map_container);

            if (articleFrag != null) {
                // If article frag is available, we're in two-pane layout...

                // Call a method in the ArticleFragment to update its content
                articleFrag.demolishStructure(x, y);
            }
            }
        }
}

