package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.content.Intent;
import android.view.View;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.LogInActivity;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class LogInGoogleOnClickListener implements View.OnClickListener {
    private LogInActivity logInActivity;
    private GoogleSignInClient googleSignInClient;

    public LogInGoogleOnClickListener(LogInActivity logInActivity) {
        this.logInActivity = logInActivity;
    }

    @Override
    public void onClick(View v) {
        String defaultWebClientId = v.getContext().getString(R.string.default_web_client_id);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(defaultWebClientId)
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(logInActivity, googleSignInOptions);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        logInActivity.startActivityForResult(signInIntent, AppConstants.GOOGLE_SIGN_IN);
    }
}
