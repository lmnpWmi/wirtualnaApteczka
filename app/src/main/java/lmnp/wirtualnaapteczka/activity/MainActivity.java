package lmnp.wirtualnaapteczka.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import lmnp.wirtualnaapteczka.R;

public class MainActivity extends AppCompatActivity {
    private LinearLayout addMedicinePanel;
    private LinearLayout medicineListPanel;
    private LinearLayout friendsPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMedicinePanel = (LinearLayout) findViewById(R.id.addMedicinePanel);
        medicineListPanel = (LinearLayout) findViewById(R.id.medicineListPanel);
        friendsPanel = (LinearLayout) findViewById(R.id.friendsPanel);

        initializeListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initializeListeners() {
        addMedicinePanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddActivity.class);
                startActivity(intent);
            }
        });

        medicineListPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO My List
            }
        });

        friendsPanel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Friends List
            }
        });
    }
}
