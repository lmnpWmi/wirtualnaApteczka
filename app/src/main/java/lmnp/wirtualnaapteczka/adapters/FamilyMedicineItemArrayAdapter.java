package lmnp.wirtualnaapteczka.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;

import java.util.Date;
import java.util.List;

public class FamilyMedicineItemArrayAdapter extends ArrayAdapter<Medicine> {
    private List<Medicine> sharedMedicinesFromFamilyMembers;
    private Context context;

    private TextView medicineNameTextView;
    private TextView medicineTypeTextView;
    private TextView medicineAmountTextView;
    private TextView medicineDueDateTextView;
    private TextView medicineOwnerTextView;

    private AdaptersCommonUtils adaptersCommonUtils;

    public FamilyMedicineItemArrayAdapter(Context context, int resource, List<Medicine> sharedMedicinesFromFamilyMembers) {
        super(context, resource, sharedMedicinesFromFamilyMembers);

        this.sharedMedicinesFromFamilyMembers = sharedMedicinesFromFamilyMembers;
        this.context = context;
        this.adaptersCommonUtils = new AdaptersCommonUtils(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_medicines_family_item, null);

        initializeViewComponents(view);

        Medicine currentMedicine = sharedMedicinesFromFamilyMembers.get(position);
        updateComponentsValues(currentMedicine);

        return view;
    }

    private void initializeViewComponents(View view) {
        medicineNameTextView = (TextView) view.findViewById(R.id.family_medicine_name);
        medicineTypeTextView = (TextView) view.findViewById(R.id.family_medicine_type);
        medicineAmountTextView = (TextView) view.findViewById(R.id.family_medicine_amount);
        medicineDueDateTextView = (TextView) view.findViewById(R.id.family_medicine_duedate);
        medicineOwnerTextView = (TextView) view.findViewById(R.id.family_medicine_owner);
    }

    private void updateComponentsValues(Medicine currentMedicine) {
        medicineNameTextView.setText(currentMedicine.getName());
        medicineTypeTextView.setText(adaptersCommonUtils.prepareMedicineTypeText(currentMedicine.getType()));

        Date dueDate = currentMedicine.getDueDate();
        medicineDueDateTextView.setText(adaptersCommonUtils.prepareDueDateText(dueDate));
        adaptersCommonUtils.markFieldIfOverdue(dueDate, medicineDueDateTextView);

        String amountText = adaptersCommonUtils.prepareAmountText(currentMedicine.getAmount()) + " " + MedicineTypeUtils.prepareLocalizedTypeSuffix(currentMedicine.getType(), context);
        medicineAmountTextView.setText(amountText);

        medicineOwnerTextView.setText(adaptersCommonUtils.prepareUsernameText(currentMedicine.getOwnerUsername()));
    }
}
