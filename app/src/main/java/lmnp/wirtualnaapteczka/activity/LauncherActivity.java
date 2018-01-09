package lmnp.wirtualnaapteczka.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.listeners.loginactivity.LogInOnCompleteListener;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.services.FirebaseDbServiceImpl;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;

import static lmnp.wirtualnaapteczka.utils.AppConstants.APP_SETTINGS;

/**
 * Activity responsible for launching application.
 *
 * @author Sebastian Nowak
 * @createdAt 07.01.2018
 */
public class LauncherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        boolean exitApplicationFlag = getIntent().getBooleanExtra(AppConstants.EXIT, false);

        if (exitApplicationFlag) {
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            initializeApplication();
        }
    }

    private void initializeApplication() {
        prepareSessionManager();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);

        boolean logInInitialized = signInIfLoginDataHasBeenRemembered(sharedPreferences);

        if (!logInInitialized) {
            launchProperActivity(sharedPreferences);
        }
    }

    private void prepareSessionManager() {
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DbService dbService = FirebaseDbServiceImpl.createNewInstance();

        SessionManager.setFirebaseAuth(firebaseAuth);
        SessionManager.setDbService(dbService);
    }

    private boolean signInIfLoginDataHasBeenRemembered(SharedPreferences sharedPreferences) {
        boolean logInInitialized = false;

        boolean rememberMe = sharedPreferences.getBoolean(AppConstants.REMEMBER_ME, false);

        if (rememberMe) {
            String email = sharedPreferences.getString(AppConstants.EMAIL, null);
            String password = sharedPreferences.getString(AppConstants.PASSWORD, null);

            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new LogInOnCompleteListener(this, email, password, rememberMe));
                logInInitialized = true;
            }
        }

        return logInInitialized;
    }

    private void launchProperActivity(SharedPreferences sharedPreferences) {
        boolean isFirstLaunch = sharedPreferences.getBoolean(AppConstants.IS_FIRST_LAUNCH, true);

        if (isFirstLaunch) {
            markFirstLaunchStatus(sharedPreferences);
            startActivity(FirstTimeWelcomeActivity.class);
        } else {
            startActivity(LogInActivity.class);
        }
    }

    private void markFirstLaunchStatus(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putBoolean(AppConstants.IS_FIRST_LAUNCH, false);
        sharedPreferencesEditor.commit();
    }

    private void startActivity(Class<?> activityClass) {
        Context applicationContext = getApplicationContext();

        Intent intent = new Intent(applicationContext, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        applicationContext.startActivity(intent);
    }
}
