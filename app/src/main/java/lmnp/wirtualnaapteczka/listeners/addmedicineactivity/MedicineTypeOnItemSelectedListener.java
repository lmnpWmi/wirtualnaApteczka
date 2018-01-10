package lmnp.wirtualnaapteczka.listeners.addmedicineactivity;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.MedicineQuantitySuffixEnum;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.utils.LocalizationUtils;

public class MedicineTypeOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
    private Medicine medicine;
    private Context context;
    private TextView quantitySuffixLabel;
    private ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter;

    public MedicineTypeOnItemSelectedListener(Medicine medicine, ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter, TextView quantitySuffixLabel, Context context) {
        this.medicine = medicine;
        this.medicineTypesAdapter = medicineTypesAdapter;
        this.quantitySuffixLabel = quantitySuffixLabel;
        this.context = context;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        MedicineTypeWithLocalizationTO item = medicineTypesAdapter.getItem(position);

        MedicineQuantitySuffixEnum quantitySuffix = item.getMedicineTypeEnum().getQuantitySuffix();
        String localizedQuantitySuffix = LocalizationUtils.retrieveLocalizationForString(quantitySuffix.name(), context);

        quantitySuffixLabel.setText(localizedQuantitySuffix);
        medicine.setType(item.getMedicineTypeEnum());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
