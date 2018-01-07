package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.listeners.loginactivity.RegulationsOnClickListener;
import lmnp.wirtualnaapteczka.listeners.registrationactivity.RegisterNewUserOnClickListener;

public class RegistrationActivity extends AppCompatActivity {
    private Button registerNewUserBtn;
    private TextView registerRegulationsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerRegulationsBtn = (TextView) findViewById(R.id.register_regulations_btn);
        registerRegulationsBtn.setOnClickListener(new RegulationsOnClickListener());

        registerNewUserBtn = (Button) findViewById(R.id.register_new_user_btn);
        registerNewUserBtn.setOnClickListener(new RegisterNewUserOnClickListener());
    }
}
