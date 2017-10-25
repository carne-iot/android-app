package ar.edu.itba.iot.iot_android.activities;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import ar.edu.itba.iot.iot_android.R;
import ar.edu.itba.iot.iot_android.view.MyAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String[] devicesNames = new String[7];
    private String[] targetTemps = new String[7];
    private String[] currentTemps = new String[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

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
        mAdapter = new MyAdapter(devicesNames, currentTemps, targetTemps);
        mRecyclerView.setAdapter(mAdapter);
    }


    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
