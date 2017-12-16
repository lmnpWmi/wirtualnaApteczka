package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.listeners.registrationactivity.RegisterNewUserOnClickListener;

public class RegistrationActivity extends AppCompatActivity {
    private Button registerNewUserBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        registerNewUserBtn = (Button) findViewById(R.id.register_new_user_btn);
        registerNewUserBtn.setOnClickListener(new RegisterNewUserOnClickListener());
    }
}
