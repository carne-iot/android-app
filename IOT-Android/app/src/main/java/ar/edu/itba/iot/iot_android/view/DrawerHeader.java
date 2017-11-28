package ar.edu.itba.iot.iot_android.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import ar.edu.itba.iot.iot_android.R;
import ar.edu.itba.iot.iot_android.controller.Controller;

//https://medium.com/@janishar.ali/navigation-drawer-android-example-8dfe38c66f59

@NonReusable
@Layout(R.layout.drawer_header)
//View of username and email in drawer
public class DrawerHeader {

    @View(R.id.profileImageView)
    private ImageView profileImage;

    @View(R.id.nameTxt)
    private TextView nameTxt;

    @View(R.id.emailTxt)
    private TextView emailTxt;

    private final Controller controller;

    public DrawerHeader(Controller controller) {
        this.controller = controller;
    }

    @Resolve
    private void onResolved() {
        if(controller.getUser() != null){
            nameTxt.setText(controller.getUser().getFullName());
            emailTxt.setText(controller.getUser().getEmail());
        }
    }
}