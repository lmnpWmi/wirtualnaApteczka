package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.LinearLayout;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.listeners.OpenAddNewMedicineListener;
import lmnp.wirtualnaapteczka.listeners.OpenFriendListListener;
import lmnp.wirtualnaapteczka.listeners.OpenMedicineListListener;

public class MainActivity extends AppCompatActivity {
    private LinearLayout addMedicinePanel;
    private LinearLayout medicineListPanel;
    private LinearLayout friendsPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addMedicinePanel = (LinearLayout) findViewById(R.id.add_medicine_panel);
        medicineListPanel = (LinearLayout) findViewById(R.id.medicine_list_panel);
        friendsPanel = (LinearLayout) findViewById(R.id.friends_panel);

        initializeListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void initializeListeners() {
        addMedicinePanel.setOnClickListener(new OpenAddNewMedicineListener());

        medicineListPanel.setOnClickListener(new OpenMedicineListListener());

        friendsPanel.setOnClickListener(new OpenFriendListListener());
    }
}
