package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.listeners.passwordreminderactivity.RemindPasswordOnClickListener;

/**
 * Activity responsible for handling events on the layout for reminding passwords.
 *
 * @author Sebastian Nowak
 * @createdAt 07.01.2018
 */
public class PasswordReminderActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button remindPasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reminder);

        emailEditText = (EditText) findViewById(R.id.remind_password_email);

        remindPasswordBtn = (Button) findViewById(R.id.remind_password_btn);
        remindPasswordBtn.setOnClickListener(new RemindPasswordOnClickListener(this, emailEditText));
    }
}