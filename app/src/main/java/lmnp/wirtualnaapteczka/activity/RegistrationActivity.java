package lmnp.wirtualnaapteczka.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.listeners.loginactivity.RegulationsOnClickListener;
import lmnp.wirtualnaapteczka.listeners.registrationactivity.RegisterNewUserOnClickListener;

public class RegistrationActivity extends AppCompatActivity {
    private Button registerNewUserBtn;
    private TextView registerRegulationsBtn;

    private EditText passwordEditText;
    private EditText repeatPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerRegulationsBtn = (TextView) findViewById(R.id.register_regulations_btn);
        registerRegulationsBtn.setOnClickListener(new RegulationsOnClickListener());

        registerNewUserBtn = (Button) findViewById(R.id.register_new_user_btn);
        registerNewUserBtn.setOnClickListener(new RegisterNewUserOnClickListener());

        passwordEditText = (EditText) findViewById(R.id.register_password_text);
        passwordEditText.setTypeface(Typeface.DEFAULT);
        passwordEditText.setTransformationMethod(new PasswordTransformationMethod());

        repeatPasswordEditText = (EditText) findViewById(R.id.register_password_repeat_text);
        repeatPasswordEditText.setTypeface(Typeface.DEFAULT);
        repeatPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
    }
}
