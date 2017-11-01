package ar.edu.itba.iot.iot_android.view;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.io.IOException;

import ar.edu.itba.iot.iot_android.R;
import ar.edu.itba.iot.iot_android.controller.UserController;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Layout(R.layout.drawer_item)
public class DrawerMenuItem {

    public static final int DRAWER_MENU_ITEM_PROFILE = 1;
    public static final int DRAWER_MENU_ITEM_NOTIFICATIONS = 2;
    public static final int DRAWER_MENU_ITEM_SETTINGS = 3;
    public static final int DRAWER_MENU_ITEM_TERMS = 4;
    public static final int DRAWER_MENU_ITEM_LOGOUT = 5;
    public static final int DRAWER_MENU_SIGNIN = 6;


    private static String s;

    private int mMenuPosition;
    private Context mContext;
    private DrawerCallBack mCallBack;

    @View(R.id.itemNameTxt)
    private TextView itemNameTxt;

    @View(R.id.itemIcon)
    private ImageView itemIcon;

    private UserController userController;

    public DrawerMenuItem(Context context, int menuPosition, UserController userController) {
        this.userController = userController;
        mContext = context;
        mMenuPosition = menuPosition;
    }

    @Resolve
    private void onResolved() {
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                itemNameTxt.setText("Profile");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_account_circle_black_18dp));
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                itemNameTxt.setText("Notifications");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications_black_18dp));
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                itemNameTxt.setText("Settings");
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_settings_black_18dp));
                break;
            case DRAWER_MENU_ITEM_TERMS:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_book_black_18dp));
                itemNameTxt.setText("Terms");
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_exit_to_app_black_18dp));
                itemNameTxt.setText("Logout");
                break;
            case DRAWER_MENU_SIGNIN:
                itemIcon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_exit_to_app_black_18dp));
                itemNameTxt.setText("Login");
                break;

        }
    }

    @Click(R.id.mainView)
    private void onMenuItemClick(){
        switch (mMenuPosition){
            case DRAWER_MENU_ITEM_PROFILE:
                Toast.makeText(mContext, "Profile", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onProfileMenuSelected();
                break;
            case DRAWER_MENU_ITEM_NOTIFICATIONS:
                Toast.makeText(mContext, "Notifications", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onNotificationsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                Toast.makeText(mContext, "Settings", Toast.LENGTH_SHORT).show();
                if(mCallBack != null)mCallBack.onSettingsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_TERMS:
                Toast.makeText(mContext, "Terms", Toast.LENGTH_SHORT).show();
                onTermsMenuSelected();
                if(mCallBack != null)mCallBack.onTermsMenuSelected();
                break;
            case DRAWER_MENU_ITEM_LOGOUT:
                Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show();
                userController.signOut(userController.getUser());
                if(mCallBack != null)mCallBack.onLogoutMenuSelected();
                break;
            case DRAWER_MENU_SIGNIN:
                Toast.makeText(mContext, "SIGNIN", Toast.LENGTH_SHORT).show();
               // onTermsMenuSelected();
             //   if(mCallBack != null)mCallBack.onTermsMenuSelected();
                break;
        }
    }

    public void setDrawerCallBack(DrawerCallBack callBack) {
        mCallBack = callBack;
    }

    public interface DrawerCallBack{
        void onProfileMenuSelected();
        void onNotificationsMenuSelected();
        void onSettingsMenuSelected();
        void onTermsMenuSelected();
        void onLogoutMenuSelected();
    }

    public void onTermsMenuSelected() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://server.carne-iot.itba.bellotapps.com/devices")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                // you code to handle response
                Log.d("http", response.body().string());
                //Toast.makeText(mContext, response.body().string().substring(0, 20), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void onLogoutMenuSelected(){

    }
}