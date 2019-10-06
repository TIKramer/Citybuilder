package com.kramer.a18882652.citybiolder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.validation.Schema;

public class GameActivity extends AppCompatActivity implements SelectorFragment.OnMapItemClickListener, MapAdapter.RecyclerViewClickListener, MapAdapter.RoadUpdateListener, MapAdapter.CommercialUpdateListener, MapAdapter.ResidentialUpdateListener, MapAdapter.PlayerCashCallBack {


    private int gameTime;
    private double employmentRate;
    private int population;
    private int cash;
    int salary;
    double taxRate;
    int serviceCost;
    int familySize;
    int nResidential;
    int nCommerical;
    int shopSize;
   private final Timer timer = new Timer();

    private UserStatusFragment userStatusFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        intialValues();
        FragmentManager fm = getSupportFragmentManager();
        MapFragment mapFrag = (MapFragment) fm.findFragmentById(R.id.map_container);
        SelectorFragment selectorFrag = (SelectorFragment) fm.findFragmentById(R.id.selector_container);
        userStatusFragment = (UserStatusFragment) fm.findFragmentById(R.id.userStatusContainer);


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
        if(userStatusFragment ==null)
        {
            userStatusFragment = new UserStatusFragment();
            fm.beginTransaction().add(R.id.userStatusContainer,userStatusFragment).commit();
        }
        timer.scheduleAtFixedRate(new TimerTask()
        {
            public void run()
            {
                // Your code

                gameTime++;
                new Handler(Looper.getMainLooper().getMainLooper()).post(new Runnable(){
                    @Override
                    public void run() {
                        userStatusFragment.updateTime(gameTime);
                   //     userStatusFragment.updateCash(nResidential);
                        population = familySize * nResidential;
                        if(population > 0) {
                            employmentRate = Math.min(1, nCommerical * shopSize / population);
                        }
                        else
                            employmentRate = 0;
                        cash += population * (employmentRate * salary * taxRate - serviceCost);
                        userStatusFragment.updateCash(cash);

                    }
                });


            }
        }, 1000, 1000);
    }

    private  void intialValues()
    {

        SettingsModel settings = SettingsModel.getInstance();
        cash = Integer.valueOf("" + settings.getData(GameDataSchema.SettingsTable.Cols.INITIAL_MONEY));


         taxRate = Double.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.TAX_RATE));
        familySize = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.FAMILY_SIZE));
        salary = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SALARY));
        serviceCost = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SERVICE_COST));
        shopSize = Integer.valueOf("" +settings.getData( GameDataSchema.SettingsTable.Cols.SHOP_SIZE));

    }

    public void calculateCash()
    {


    }
    @Override
    public void onAttachFragment(android.support.v4.app.Fragment fragment) {
        if (fragment instanceof SelectorFragment) {
            SelectorFragment headlinesFragment = (SelectorFragment) fragment;
            headlinesFragment.setOnMapItemClickListener(this);
        }
        else if(fragment instanceof MapFragment)
        {
            MapFragment headlinesFragment = (MapFragment) fragment;
            headlinesFragment.setRecyclerViewClickListener(this);
            headlinesFragment.setRoadUpdateListener(this);
            headlinesFragment.setResidentialUpdateListener(this);
            headlinesFragment.setCommercialUpdateListener(this);
            headlinesFragment.setCashCallBack(this);



        }
    }


    public int getResidents(int x)
    {
        return 0;
    }



    @Override
    public void demolishBuilding(int x, int y) {
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

    @Override
    public void recyclerViewListClicked(int x, int y)

    {
        SelectorFragment articleFrag = (SelectorFragment)
                getSupportFragmentManager().findFragmentById(R.id.selector_container);

        if (articleFrag != null) {
            // If article frag is available, we're in two-pane layout...

            // Call a method in the ArticleFragment to update its content
            articleFrag.getClickLocation(x, y);
        }
    }


    public void incrementResidential()
    {
        nResidential++;
    }
    public void deIncrementResidential()
    {

    }

    @Override
    public void roadsUpdateListener(int x) {
        nResidential = x;
    }

    @Override
    public void commercialUpdateListener(int x) {
        nCommerical = x;
    }
    @Override
    public void residentialUpdateListener(int x) {
        nResidential = x;
    }

    @Override
    public Integer getCash() {
        return cash;
    }


    @Override
    protected void onStop() {
        // call the superclass method first
        super.onStop();
        timer.cancel();
    }
}


