package lmnp.wirtualnaapteczka.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import lmnp.wirtualnaapteczka.R;

public class LauncherActivity extends AppCompatActivity {
    private static final String APP_SETTINGS = "app_settings";
    private static final String IS_FIRST_LAUNCH = "is_first_launch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);

        boolean isFirstLaunch = sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true);

//        if (isFirstLaunch) {
        if (true) {
            markFirstLaunchStatus(sharedPreferences);
            startActivity(FirstTimeWelcomeActivity.class);
        } else {
            startActivity(LogInActivity.class);
        }
    }

    private void markFirstLaunchStatus(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        sharedPreferencesEditor.putBoolean(IS_FIRST_LAUNCH, false);
        sharedPreferencesEditor.commit();
    }

    private void startActivity(Class<?> activityClass) {
        Context applicationContext = getApplicationContext();

        Intent intent = new Intent(applicationContext, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        applicationContext.startActivity(intent);
    }
}
