package ar.edu.itba.iot.iot_android.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import ar.edu.itba.iot.iot_android.R;

import com.mindorks.placeholderview.PlaceHolderView;

import ar.edu.itba.iot.iot_android.service.DeviceService;
import ar.edu.itba.iot.iot_android.service.UserService;
import ar.edu.itba.iot.iot_android.view.DrawerHeader;
import ar.edu.itba.iot.iot_android.view.DrawerMenuItem;
import ar.edu.itba.iot.iot_android.view.MyAdapter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] devicesNames = new String[7];
    private String[] targetTemps = new String[7];
    private String[] currentTemps = new String[7];
    private final DeviceService deviceService = new DeviceService();
    private final UserService userService = new UserService();

    private PlaceHolderView mDrawerView;
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawer = (DrawerLayout)findViewById(R.id.drawerLayout);
        mDrawerView = (PlaceHolderView)findViewById(R.id.drawerView);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setupDrawer();



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        devicesNames[0] = "Device 1";
        devicesNames[1] = "Device 2";
        devicesNames[2] = "Device 3";
        devicesNames[3] = "Device 4";
        devicesNames[4] = "Device 5";
        devicesNames[5] = "Device 6";
        devicesNames[6] = "Device 7";

        currentTemps[0] = "45°C";
        currentTemps[1] = "30°C";
        currentTemps[2] = "24°C";
        currentTemps[3] = "25°C";
        currentTemps[4] = "26°C";
        currentTemps[5] = "35°C";
        currentTemps[6] = "47°C";

        targetTemps[0] = "70°C";
        targetTemps[1] = "60°C";
        targetTemps[2] = "56°C";
        targetTemps[3] = "65°C";
        targetTemps[4] = "64°C";
        targetTemps[5] = "75°C";
        targetTemps[6] = "72°C";
        View v = this.findViewById(R.id.addDevice);
        v.setOnClickListener(this);




        mAdapter = new MyAdapter(devicesNames, currentTemps, targetTemps);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void setNewToken(String token){

    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {

    }


    private void setupDrawer(){
        mDrawerView
                .addView(new DrawerHeader())
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_PROFILE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_REQUESTS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_MESSAGE))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_GROUPS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_NOTIFICATIONS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_TERMS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_SETTINGS))
                .addView(new DrawerMenuItem(this.getApplicationContext(), DrawerMenuItem.DRAWER_MENU_ITEM_LOGOUT));

        ActionBarDrawerToggle  drawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open_drawer, R.string.close_drawer){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        mDrawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
}
