package ar.edu.itba.iot.iot_android.service.callbacks;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mindorks.placeholderview.PlaceHolderView;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.R;
import ar.edu.itba.iot.iot_android.activities.MainActivity;
import ar.edu.itba.iot.iot_android.controller.UserController;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.utils.JSONManager;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class GetUserByUserNameCallback implements Callback {

    private final UserController userController;
    private final MainActivity mainActivity;

    public GetUserByUserNameCallback(MainActivity mainActivity, UserController userController) {
        this.mainActivity = mainActivity;
        this.userController = userController;
    }

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if(!response.isSuccessful()){
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(mainActivity, "Username and Password did not match", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            return;
        }
        User newUser =  JSONManager.parseUser(response.body().string());
        userController.getUser().setId(newUser.getId());
        userController.getUser().setFullName(newUser.getFullName());
        userController.getUser().setEmail(newUser.getEmail());
        mainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                View v = mainActivity.findViewById(R.id.addDevice);
                v.setVisibility(View.VISIBLE);
                mainActivity.updateDrawer();
            }
        });
    }
}
