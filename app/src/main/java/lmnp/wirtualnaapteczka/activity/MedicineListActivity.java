package lmnp.wirtualnaapteczka.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import lmnp.wirtualnaapteczka.R;

public class MedicineListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
    }

//musi być ArrayAdapter<> itemsAdapter = new ArrayAdapter<>(this, R.layout.list_item, obiekty)
//    ListView listView = (ListView) findViewById(R.id.list);


//    musi być jeszcze obsłużony Spinner z kategoriami leków
}
