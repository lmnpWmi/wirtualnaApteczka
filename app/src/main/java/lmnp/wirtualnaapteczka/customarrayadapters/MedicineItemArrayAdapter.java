package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.MedicineTypeEnum;
import lmnp.wirtualnaapteczka.listeners.medicinelistactivity.MedicineItemLongClickListener;
import lmnp.wirtualnaapteczka.listeners.medicinelistactivity.MedicineItemOnClickListener;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.DateUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;

import java.util.Date;
import java.util.List;

public class MedicineItemArrayAdapter extends ArrayAdapter<Medicine> {

    private List<Medicine> medicines;
    private Context context;

    private LinearLayout listItemPanel;

    private TextView name;
    private TextView type;
    private TextView amount;
    private TextView createdAt;
    private TextView dueDate;
    private ImageView thumbnail;


    public MedicineItemArrayAdapter(Context context, int resource, List<Medicine> medicines) {
        super(context, resource, medicines);

        this.medicines = medicines;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, null);

        Medicine currentMedicine = medicines.get(position);

        initializeViewComponents(view);
        updateComponentsValues(currentMedicine);

        return view;
    }

    private void initializeViewComponents(View view) {
        listItemPanel = (LinearLayout) view.findViewById(R.id.list_item_panel);

        name = (TextView) view.findViewById(R.id.medicine_item_name);
        type = (TextView) view.findViewById(R.id.medicine_item_type);
        amount = (TextView) view.findViewById(R.id.medicine_item_amount);
        createdAt = (TextView) view.findViewById(R.id.medicine_item_createdat);
        dueDate = (TextView) view.findViewById(R.id.medicine_item_duedate);
        thumbnail = (ImageView) view.findViewById(R.id.medicine_simple_item_thumbnail);
    }

    private void updateComponentsValues(Medicine currentMedicine) {
        listItemPanel.setOnClickListener(new MedicineItemOnClickListener(currentMedicine));
        listItemPanel.setOnLongClickListener(new MedicineItemLongClickListener(currentMedicine, context));

        name.setText(currentMedicine.getName());
        type.setText(prepareMedicineTypeText(currentMedicine.getType()));
        createdAt.setText(prepareCreatedAtText(currentMedicine.getCreatedAt()));
        dueDate.setText(prepareDueDateText(currentMedicine.getDueDate()));

        String amountText = AdaptersCommonUtils.prepareAmountText(currentMedicine.getAmount(), context) + " " + MedicineTypeUtils.prepareLocalizedTypeSuffix(currentMedicine.getType(), context);
        amount.setText(amountText);

//        thumbnail.setImageURI(AdaptersCommonUtils.prepareThumbnailUri(currentMedicine.getThumbnailUri()));
    }

    private String prepareCreatedAtText(Date createdAt) {
        String result = context.getResources().getString(R.string.created_at) + ": " + DateUtils.formatDate(createdAt, context);

        return result;
    }

    private String prepareMedicineTypeText(MedicineTypeEnum medicineType) {
        String result = context.getResources().getString(R.string.type) + ": " + MedicineTypeUtils.prepareLocalizedMedicineType(medicineType, context);

        return result;
    }

    private String prepareDueDateText(Date dueDate) {
        String result = context.getResources().getString(R.string.due_date) + ": ";

        if (dueDate != null) {
            result += DateUtils.formatDate(dueDate, context);
            markFieldIfOverdue(dueDate);
        } else {
            result += context.getResources().getString(R.string.not_specified);
        }

        return result;
    }

    private void markFieldIfOverdue(Date dueDate) {
        Date now = new Date();

        if (dueDate.before(now)) {
            this.dueDate.setTextColor(Color.RED);
        }
    }
}
