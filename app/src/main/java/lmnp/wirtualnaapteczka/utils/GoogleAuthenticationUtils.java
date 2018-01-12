package lmnp.wirtualnaapteczka.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MainActivity;
import lmnp.wirtualnaapteczka.data.enums.LoginTypeEnum;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;

/**
 * Utils for authentication in Google Account.
 *
 * @author Sebastian Nowak
 * @createdAt 10.01.2018
 */
public final class GoogleAuthenticationUtils {
    private GoogleAuthenticationUtils() {
    }

    public static void signIn(AppCompatActivity activity) {
        String defaultWebClientId = activity.getString(R.string.default_web_client_id);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(defaultWebClientId)
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, googleSignInOptions);

        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, AppConstants.GOOGLE_SIGN_IN);
    }

    public static void analyseGoogleLoginResult(Intent data, AppCompatActivity activity) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            GoogleAuthenticationUtils.authenticateInFirebaseUsingGoogleAccount(account, activity);
        } catch (ApiException e) {
            Toast.makeText(activity, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
        }
    }

    private static void authenticateInFirebaseUsingGoogleAccount(final GoogleSignInAccount acct, final AppCompatActivity activity) {
        FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DbService dbService = SessionManager.getDbService();
                            dbService.createUserAccountCreatorListenerForGoogleAuth(acct);

                            SharedPreferences sharedPreferences = activity.getApplicationContext().getSharedPreferences(AppConstants.APP_SETTINGS, Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.putBoolean(AppConstants.LOGGED_IN, Boolean.TRUE);
                            edit.putString(AppConstants.LOGIN_TYPE, LoginTypeEnum.GOOGLE.name());
                            edit.commit();

                            SessionManager.initializeFirebaseListener();

                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                        } else {
                            Toast.makeText(activity, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
