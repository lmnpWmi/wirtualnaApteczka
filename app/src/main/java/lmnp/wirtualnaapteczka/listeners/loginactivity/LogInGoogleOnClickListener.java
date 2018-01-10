package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.view.View;
import lmnp.wirtualnaapteczka.activity.LogInActivity;
import lmnp.wirtualnaapteczka.utils.GoogleAuthenticationUtils;

public class LogInGoogleOnClickListener implements View.OnClickListener {
    private LogInActivity logInActivity;

    public LogInGoogleOnClickListener(LogInActivity logInActivity) {
        this.logInActivity = logInActivity;
    }

    @Override
    public void onClick(View v) {
        GoogleAuthenticationUtils.signIn(logInActivity);
    }
}
