package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.listeners.firsttimewelcomeactivity.StartApplicationOnClickListener;

public class FirstTimeWelcomeActivity extends AppCompatActivity {
    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_time_welcome);

        startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new StartApplicationOnClickListener());
    }
}
