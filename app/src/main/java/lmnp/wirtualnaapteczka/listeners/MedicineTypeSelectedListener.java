package lmnp.wirtualnaapteczka.listeners;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.data.MedicineQuantitySuffix;
import lmnp.wirtualnaapteczka.data.dto.MedicineTypeWithLocalizationTO;
import lmnp.wirtualnaapteczka.utils.LocalizationUtils;

public class MedicineTypeSelectedListener implements AdapterView.OnItemSelectedListener {

    private ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter;

    private Context context;
    private TextView quantitySuffixLabel;

    public MedicineTypeSelectedListener(ArrayAdapter<MedicineTypeWithLocalizationTO> medicineTypesAdapter, TextView quantitySuffixLabel, Context context) {
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
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
