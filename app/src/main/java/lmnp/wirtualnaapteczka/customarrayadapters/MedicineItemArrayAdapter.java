package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.enums.MedicineTypeEnum;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MedicineItemArrayAdapter extends ArrayAdapter<Medicine> {

    private List<Medicine> medicines;
    private Context context;

    public MedicineItemArrayAdapter(Context context, int resource, List<Medicine> medicines) {
        super(context, resource, medicines);

        this.medicines = medicines;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Medicine currentMedicine = medicines.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, null);

        TextView name = (TextView) view.findViewById(R.id.medicine_item_name);
        TextView type = (TextView) view.findViewById(R.id.medicine_item_type);
        TextView amount = (TextView) view.findViewById(R.id.medicine_item_amount);
        TextView dueDate = (TextView) view.findViewById(R.id.medicine_item_duedate);
        ImageView thumbnail = (ImageView) view.findViewById(R.id.medicine_item_thumbnail);

        name.setText(currentMedicine.getName());
        type.setText(prepareMedicineTypeText(currentMedicine.getType()));
        dueDate.setText(prepareDueDateText(currentMedicine.getDueDate()));
        amount.setText(AdaptersCommonUtils.prepareAmountText(currentMedicine.getAmount(), context));

//        thumbnail.setImageURI(AdaptersCommonUtils.prepareThumbnailUri(currentMedicine.getThumbnailUri()));

        return view;
    }

    private String prepareMedicineTypeText(MedicineTypeEnum medicineType) {
        String result = context.getResources().getString(R.string.type) + ": " + MedicineTypeUtils.prepareLocalizedMedicineType(medicineType, context);

        return result;
    }

    private String prepareDueDateText(Date dueDate) {
        String result = "";

        if (dueDate != null) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);

            result = context.getResources().getString(R.string.due_date) + ": " + dateFormat.format(dueDate);
        }

        return result;
    }
}
