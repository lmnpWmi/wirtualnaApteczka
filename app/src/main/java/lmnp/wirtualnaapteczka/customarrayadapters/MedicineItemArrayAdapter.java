package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.dto.MedicineItem;
import lmnp.wirtualnaapteczka.dto.OfflineConfiguration;
import lmnp.wirtualnaapteczka.utils.CommonUtils;

/**
 * ArrayAdapter for MedicineItems that will be added to the MedicineListActivity ListView.
 */

public class MedicineItemArrayAdapter extends ArrayAdapter<MedicineItem> {

    private static final String AMOUNT_TEXT = "Ilość: ";
    private static final String DUEDATE_TEXT = "Data ważności: ";

    private Context context;
    private Spinner categorySpinner;
    private MedicineItemArrayAdapter adapter;
    private List<MedicineItem> medicineItems;

    public MedicineItemArrayAdapter(Context context, int resource, List<MedicineItem> medicineItems, Spinner categorySpinner) {
        super(context, resource, medicineItems);

        this.context = context;
        this.medicineItems = medicineItems;
        this.adapter = this;
        this.categorySpinner = categorySpinner;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        final MedicineItem medicine = medicineItems.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.medicine_layout, null);

        TextView name = (TextView) view.findViewById(R.id.name);
        TextView type = (TextView) view.findViewById(R.id.type);
        TextView amount = (TextView) view.findViewById(R.id.amount);
        TextView dueDate = (TextView) view.findViewById(R.id.dueDate);

        name.setText(medicine.getName());
        name.setTypeface(null, Typeface.BOLD);
        type.setText(medicine.getType().getName());
        amount.setText(AMOUNT_TEXT + medicine.getAmount());

        SimpleDateFormat formatter = new SimpleDateFormat("d.M.yyyy");
        String formattedDueDate = formatter.format(medicine.getDueDate());
        dueDate.setText(DUEDATE_TEXT + formattedDueDate);

        ImageButton deleteBtn = (ImageButton) view.findViewById(R.id.deleteMedicineBtn);
        deleteBtn.setOnClickListener(new DeleteButtonOnClickListener(name.getText().toString()));

        ImageButton editBtn = (ImageButton) view.findViewById(R.id.editMedicineBtn);
        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.displayNotAvaiableToastMessage(context);
            }
        });

        ImageButton viewBtn = (ImageButton) view.findViewById(R.id.viewMedicineBtn);
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonUtils.displayNotAvaiableToastMessage(context);
            }
        });

        return view;
    }

    private class DeleteButtonOnClickListener implements View.OnClickListener {

        private String name;

        public DeleteButtonOnClickListener(String name) {
            this.name = name;
        }

        @Override
        public void onClick(View v) {
            MedicineItem itemToDeletion = null;
            for (MedicineItem item : medicineItems) {
                if (item.getName().equals(name)) {
                    itemToDeletion = item;
                }
            }

            String categoryOfDeletedItem = itemToDeletion.getCategory();

            medicineItems.remove(itemToDeletion);

            boolean doesAnyItemInThisCategoryExists = false;
            for (MedicineItem medicine : medicineItems) {
                if (medicine.getCategory().equals(categoryOfDeletedItem)) {
                    doesAnyItemInThisCategoryExists = true;
                }
            }

            OfflineConfiguration offlineConfiguration = CommonUtils.retrieveOfflineConfiguration(context);
            offlineConfiguration.getMedicineChest().setMedicines(medicineItems);

            if (!doesAnyItemInThisCategoryExists) {
                offlineConfiguration.getMedicineCategories().remove(categoryOfDeletedItem);
            }

            CommonUtils.updateOfflineConfiguration(offlineConfiguration, context);

            adapter.notifyDataSetChanged();

            List<String> updatedCategories = new ArrayList<>();
            updatedCategories.add(v.getResources().getString(R.string.medicinelist_categoryAll));
            updatedCategories.addAll(offlineConfiguration.getMedicineCategories());
            ArrayAdapter<String> categoriesArrayAdapter = new ArrayAdapter<>(context, R.layout.dropdown_category_layout, updatedCategories);
            categorySpinner.setAdapter(categoriesArrayAdapter);
            categoriesArrayAdapter.notifyDataSetChanged();
        }
    }
}
