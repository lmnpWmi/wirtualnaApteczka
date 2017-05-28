package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.dto.MedicineItem;

/**
 * ArrayAdapter for MedicineItems that will be added to the MedicineListActivity ListView.
 */

public class MedicineItemArrayAdapter extends ArrayAdapter<MedicineItem> {

    private static final String AMOUNT_TEXT = "Ilość: ";
    private static final String DUEDATE_TEXT = "Data ważności: ";

    private Context context;
    private List<MedicineItem> medicineItems;

    public MedicineItemArrayAdapter(Context context, int resource, List<MedicineItem> medicineItems) {
        super(context, resource, medicineItems);

        this.context = context;
        this.medicineItems = medicineItems;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MedicineItem medicine = medicineItems.get(position);

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

        return view;
    }
}
