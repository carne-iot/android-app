package ar.edu.itba.iot.iot_android.tasks;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

import ar.edu.itba.iot.iot_android.activities.MainActivity;

/**
 * Created by julianrodrigueznicastro on 11/21/17.
 */

public class GetDevicesAsyncTask extends AsyncTask<String, Integer, String> {

    private final MainActivity mainActivity;


    public GetDevicesAsyncTask(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // Runs in UI before background thread is called
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        // Do something like display a progress bar
    }

    // This is run in a background thread
    @Override
    protected String doInBackground(String... params) {
        while (true) {
            SystemClock.sleep(10000);
            mainActivity.updateDevices();
            Log.d("async", "devices are being updated");
        }
    }

    // This is called from background thread but runs in UI
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        // Do things like update the progress bar
    }

    // This runs in UI when background thread finishes
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Do things like hide the progress bar or change a TextView
    }
}