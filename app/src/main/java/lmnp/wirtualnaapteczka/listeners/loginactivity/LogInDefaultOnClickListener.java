package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.LogInActivity;
import lmnp.wirtualnaapteczka.session.SessionManager;

public class LogInDefaultOnClickListener implements View.OnClickListener {
    private LogInActivity logInActivity;

    private CheckBox rememberMeCheckBox;
    private EditText emailEditText;
    private EditText passwordEditText;

    public LogInDefaultOnClickListener(LogInActivity logInActivity) {
        this.logInActivity = logInActivity;
    }

    @Override
    public void onClick(View v) {
        rememberMeCheckBox = (CheckBox) logInActivity.findViewById(R.id.remember_me);
        emailEditText = (EditText) logInActivity.findViewById(R.id.email_login_text);
        passwordEditText = (EditText) logInActivity.findViewById(R.id.password_login_text);

        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        boolean isRememberMeChecked = rememberMeCheckBox.isChecked();

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(logInActivity, new LogInOnCompleteListener(v.getContext(), email, password, isRememberMeChecked));
        } else {
            Toast.makeText(v.getContext(), R.string.empty_email_or_password_msg, Toast.LENGTH_SHORT).show();
        }
    }
}
