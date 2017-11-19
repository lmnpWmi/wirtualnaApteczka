package lmnp.wirtualnaapteczka.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.listeners.AddNewMedicineListener;
import lmnp.wirtualnaapteczka.listeners.FriendListListener;
import lmnp.wirtualnaapteczka.listeners.MedicineListListener;

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
        addMedicinePanel.setOnClickListener(new AddNewMedicineListener());

        medicineListPanel.setOnClickListener(new MedicineListListener());

        friendsPanel.setOnClickListener(new FriendListListener());
    }
}
