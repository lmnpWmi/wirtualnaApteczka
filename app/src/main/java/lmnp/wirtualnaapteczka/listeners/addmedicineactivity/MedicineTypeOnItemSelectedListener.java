package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.MedicineQuantitySuffix;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.utils.LocalizationUtils;

public class MedicineTypeOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private Medicine newMedicine;
    private Context context;
    private TextView quantitySuffixLabel;
    private ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter;

    public MedicineTypeOnItemSelectedListener(Medicine newMedicine, ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter, TextView quantitySuffixLabel, Context context) {
        this.newMedicine = newMedicine;
        this.medicineTypesAdapter = medicineTypesAdapter;
        this.quantitySuffixLabel = quantitySuffixLabel;
        this.context = context;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MedicineTypeWithLocalizationTO item = medicineTypesAdapter.getItem(position);

        MedicineQuantitySuffix quantitySuffix = item.getMedicineTypeEnum().getQuantitySuffix();
        String localizedQuantitySuffix = LocalizationUtils.retrieveLocalizationForString(quantitySuffix.name(), context);

        quantitySuffixLabel.setText(localizedQuantitySuffix);
        newMedicine.setType(item.getMedicineTypeEnum());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
