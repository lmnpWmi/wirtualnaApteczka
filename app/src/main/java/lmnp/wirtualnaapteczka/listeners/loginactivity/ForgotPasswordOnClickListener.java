package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.PasswordReminderActivity;

public class ForgotPasswordOnClickListener implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), PasswordReminderActivity.class);
        v.getContext().startActivity(intent);
    }
}
