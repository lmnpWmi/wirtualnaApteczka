package lmnp.wirtualnaapteczka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Medicine> medicines = new ArrayList<>();
    private MedicineAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        String nazwa = this.getIntent().getStringExtra("nazwa");
        if (nazwa != null) {
            adapter.add(new Medicine(nazwa, "nieznany typ"));
        }
        if (!adapter.isEmpty()) {
            LinearLayout parent = (LinearLayout) findViewById(R.id.layout_medicine_list);
            View noMedicine = findViewById(R.id.text_no_medicine);
            parent.removeView(noMedicine);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adapter = new MedicineAdapter(this, medicines);
        ListView listView = (ListView) findViewById(R.id.list_medicines);
        listView.setAdapter(adapter);
        View addButton = findViewById(R.id.button_add_medicine);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent medicineIntent = new Intent(MainActivity.this, MedicineActivity.class);
                startActivity(medicineIntent);
            }
        });
    }
}
