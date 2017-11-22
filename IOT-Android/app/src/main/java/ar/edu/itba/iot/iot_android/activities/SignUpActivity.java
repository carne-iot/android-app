package ar.edu.itba.iot.iot_android.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ar.edu.itba.iot.iot_android.R;
import ar.edu.itba.iot.iot_android.service.UserService;
import okhttp3.Response;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText userNameText;
    EditText fullNameText;
    EditText emailText;
    EditText passwordText;
    Button signupButton;
    TextView loginLink;
    ProgressBar progressBar;


    UserService userService = new UserService();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fullNameText = (EditText) this.findViewById(R.id.input_full_name);
        userNameText = (EditText) this.findViewById(R.id.input_username);
        emailText = (EditText) this.findViewById(R.id.input_email);
        passwordText = (EditText) this.findViewById(R.id.input_password);
        signupButton = (Button) this.findViewById(R.id.btn_signup);
        loginLink = (TextView) this.findViewById(R.id.link_login);
        progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        progressBar.setIndeterminate(true);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        signupButton.setEnabled(false);

        progressBar.setVisibility(View.VISIBLE);

        // TODO: Implement your own signup logic here.

        final StrictMode.ThreadPolicy oldPolicy = StrictMode.getThreadPolicy();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        Date birthDate = null;
                        try {
                            birthDate = dateFormat.parse("1989-03-26");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        Response response = userService.signUpSync(fullNameText.getText().toString(),
                                emailText.getText().toString(), dateFormat.format(birthDate),
                                passwordText.getText().toString(), userNameText.getText().toString());

                        progressBar.setVisibility(View.INVISIBLE);
                        if(response != null && response.isSuccessful()){
                            StrictMode.setThreadPolicy(oldPolicy);
                            System.out.println("calling success");
                            onSignupSuccess();
                        }else{
                            System.out.println("calling failure");
                            onSignupFailed();
                        }
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        signupButton.setEnabled(true);
        Intent intent = new Intent();
        intent.putExtra("username", userNameText.getText().toString());
        intent.putExtra("password", passwordText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = fullNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            fullNameText.setError("at least 3 characters");
            valid = false;
        } else {
            fullNameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
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