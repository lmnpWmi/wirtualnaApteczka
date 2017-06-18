package lmnp.wirtualnaapteczka.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.dto.OfflineConfiguration;
import lmnp.wirtualnaapteczka.utils.CommonUtils;

/**
 * Activity class for handling applications intro screen.
 */

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button skipBtn;
    private Button createNewAccountBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        setContentView(R.layout.activity_intro);

        initializeComponents();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createNewAccBtn:
                CommonUtils.displayNotAvaiableToastMessage(getApplicationContext());
                break;

            case R.id.skipBtn:
                Intent intent = new Intent(this, MedicineListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initializeComponents() {
        skipBtn = (Button) findViewById(R.id.skipBtn);
        skipBtn.setOnClickListener(this);

        createNewAccountBtn = (Button) findViewById(R.id.createNewAccBtn);
        createNewAccountBtn.setOnClickListener(this);
    }
}
