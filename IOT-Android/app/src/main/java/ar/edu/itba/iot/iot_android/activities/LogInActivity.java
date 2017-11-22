package ar.edu.itba.iot.iot_android.activities;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

import ar.edu.itba.iot.iot_android.R;
import ar.edu.itba.iot.iot_android.controller.UserController;
import ar.edu.itba.iot.iot_android.model.User;
import ar.edu.itba.iot.iot_android.service.HTTPService;
import ar.edu.itba.iot.iot_android.service.UserService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static ar.edu.itba.iot.iot_android.service.HTTPService.JSON;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    SharedPreferences sp;

    User user;
    UserService userService = new UserService();

    EditText usernameText;
    EditText passwordText;
    Button loginButton;
    TextView signupLink;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("user", Context.MODE_PRIVATE);

        String token = sp.getString("token", "");
        if(!token.isEmpty()) onLoginSuccess(sp.getString("username", ""),
                                            sp.getString("password", ""),
                                            token,
                                            sp.getString("logoutURL", ""));

        usernameText = (EditText) this.findViewById(R.id.input_username);
        passwordText = (EditText) this.findViewById(R.id.input_password);
        loginButton = (Button) this.findViewById(R.id.btn_login);
        signupLink = (TextView) this.findViewById(R.id.link_signup);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        progressBar.setIndeterminate(true);


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);


        progressBar.setVisibility(View.VISIBLE);

        final String username = usernameText.getText().toString();
        final String password = passwordText.getText().toString();

        final StrictMode.ThreadPolicy oldPolicy = StrictMode.getThreadPolicy();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Response response = userService.logInSync(username, password);
                        if(response != null && response.isSuccessful()){
                            StrictMode.setThreadPolicy(oldPolicy);
                            onLoginSuccess(username, password, response.header("X-Token"), response.header("X-Logout-Url"));
                        }else{
                            onLoginFailed();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here

                usernameText.setText(data.getStringExtra("username"));
                passwordText.setText(data.getStringExtra("password"));
                login();

                // By default we just finish the Activity and log them in automatically
                //this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(String username, String password, String token, String logoutURL) {
        loginButton.setEnabled(true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        intent.putExtra("token", token);
        intent.putExtra("logoutURL", logoutURL);
        startActivity(intent);
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        if (username.isEmpty() || !username.matches("[A-Za-z0-9]*")) {
            usernameText.setError("enter a valid username");
            valid = false;
        } else {
            usernameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}