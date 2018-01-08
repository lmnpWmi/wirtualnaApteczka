package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MainActivity;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;

/**
 * Listener executed when the answer regarding authentication of the user returned from the Firebase.
 *
 * @author Sebastian Nowak
 * @createdAt 07.01.2018
 */
public class LogInOnCompleteListener implements OnCompleteListener<AuthResult> {
    private Context context;
    private String email;
    private String password;
    private boolean isRememberMeChecked;

    public LogInOnCompleteListener(Context context, String email, String password, boolean isRememberMeChecked) {
        this.context = context;
        this.email = email;
        this.password = password;
        this.isRememberMeChecked = isRememberMeChecked;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();
            FirebaseUser currentUser = SessionManager.getFirebaseUser();

            boolean isEmailVerified = currentUser.isEmailVerified();
            if (true) {
                updateLoginConfigInSharedPrefs();

                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            } else {
                firebaseAuth.signOut();
                Toast.makeText(context, R.string.email_not_verified, Toast.LENGTH_SHORT).show();
            }
        } else {
//            Log.w(getClass().getSimpleName(), "Sign In Failure: " + task.getException());
            Toast.makeText(context, R.string.invalid_email_or_password, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLoginConfigInSharedPrefs() {
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences(AppConstants.APP_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();

        edit.putBoolean(AppConstants.REMEMBER_ME, isRememberMeChecked);
        edit.putString(AppConstants.EMAIL, email);

        if (isRememberMeChecked) {
            edit.putString(AppConstants.PASSWORD, password);
        } else {
            edit.putString(AppConstants.PASSWORD, null);
        }

        edit.commit();
    }
}