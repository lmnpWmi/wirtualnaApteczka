package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(), R.string.app_name, Toast.LENGTH_LONG);
    }
}
