package ar.edu.itba.iot.iot_android.view;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import ar.edu.itba.iot.iot_android.R;
import ar.edu.itba.iot.iot_android.controller.UserController;

//https://medium.com/@janishar.ali/navigation-drawer-android-example-8dfe38c66f59

@NonReusable
@Layout(R.layout.drawer_header)
public class DrawerHeader {

    @View(R.id.profileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.emailTxt)
    private TextView emailTxt;

    private final UserController userController;

    public DrawerHeader(UserController userController) {
        this.userController = userController;
    }

    @Resolve
    private void onResolved() {
        Log.d("hola", "hola");
//        Log.d("user", userController.getUser().toString());
        Log.d("hola", "hola");
        nameTxt.setText(userController.getUser().getFullName());
        emailTxt.setText(userController.getUser().getEmail());
    }
}