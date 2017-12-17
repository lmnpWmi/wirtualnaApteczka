package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.listeners.loginactivity.LogInDefaultOnClickListener;
import lmnp.wirtualnaapteczka.listeners.loginactivity.LogInFacebookOnClickListener;
import lmnp.wirtualnaapteczka.listeners.loginactivity.LogInGoogleOnClickListener;
import lmnp.wirtualnaapteczka.listeners.loginactivity.RegisterOnClickListener;

public class LogInActivity extends AppCompatActivity {
    private Button logInFBBtn;
    private Button logInGoogleBtn;
    private Button logInDefaultBtn;
    private TextView registerBtn;
    private TextView forgotPasswordBtn;

    private EditText emailText;
    private EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initializeViewComponents();
        initializeComponentsListeners();
    }

    @Override
    public void onBackPressed() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void initializeViewComponents() {
        logInFBBtn = (Button) findViewById(R.id.log_in_fb_btn);
        logInGoogleBtn = (Button) findViewById(R.id.log_in_google_btn);
        logInDefaultBtn = (Button) findViewById(R.id.log_in_default_btn);
        registerBtn = (TextView) findViewById(R.id.register_btn);
        forgotPasswordBtn = (TextView) findViewById(R.id.forgot_password_btn);

        emailText = (EditText) findViewById(R.id.email_login_text);
        emailText.setSelected(false);
        passwordText = (EditText) findViewById(R.id.password_login_text);
        passwordText.setSelected(false);
    }

    private void initializeComponentsListeners() {
        logInFBBtn.setOnClickListener(new LogInFacebookOnClickListener());
        logInGoogleBtn.setOnClickListener(new LogInGoogleOnClickListener());

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        logInDefaultBtn.setOnClickListener(new LogInDefaultOnClickListener(email, password));

        registerBtn.setOnClickListener(new RegisterOnClickListener());
    }
}
