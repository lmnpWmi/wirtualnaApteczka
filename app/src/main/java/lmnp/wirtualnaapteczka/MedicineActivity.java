package lmnp.wirtualnaapteczka;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MedicineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);


        View addButton = findViewById(R.id.button_save_medicine);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String givenName = ((EditText) findViewById(R.id.edit_text_name)).getText().toString();
                Intent medicineIntent = new Intent(MedicineActivity.this, MainActivity.class);
                medicineIntent.putExtra("medicineName", givenName);
                startActivity(medicineIntent);
            }

        });



    }
}
