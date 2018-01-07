package lmnp.wirtualnaapteczka.listeners.passwordreminderactivity;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.LogInActivity;
import lmnp.wirtualnaapteczka.session.SessionManager;

public class RemindPasswordOnClickListener implements View.OnClickListener {
    private Context context;
    private EditText emailEditText;

    public RemindPasswordOnClickListener(Context context, EditText emailEditText) {
        this.context = context;
        this.emailEditText = emailEditText;
    }

    @Override
    public void onClick(View v) {
        String email = emailEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(context, R.string.empty_email_err_msg, Toast.LENGTH_SHORT);
        } else {
            FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();
            firebaseAuth.sendPasswordResetEmail(email);

            Intent intent = new Intent(context, LogInActivity.class);
            context.startActivity(intent);

            Toast.makeText(context, R.string.remind_password_email_sent, Toast.LENGTH_SHORT);
        }
    }
}
