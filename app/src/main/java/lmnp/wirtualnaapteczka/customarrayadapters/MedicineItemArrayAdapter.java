package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.data.enums.MedicineTypeEnum;
import lmnp.wirtualnaapteczka.listeners.common.PreviewPhotoOnClickListener;
import lmnp.wirtualnaapteczka.listeners.medicinelistactivity.MedicineItemLongClickListener;
import lmnp.wirtualnaapteczka.listeners.medicinelistactivity.MedicineItemOnClickListener;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.DateUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;
import lmnp.wirtualnaapteczka.utils.ThumbnailUtils;

import java.util.Date;
import java.util.List;

/**
 * Adapter for medicines in medicine list.
 *
 * @author Sebastian Nowak
 * @createdAt 28.12.2017
 */
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
    private Button sharedMedicineBtn;


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
        sharedMedicineBtn = (Button) view.findViewById(R.id.share_medicine_btn);
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

        String thumbnailUri = currentMedicine.getThumbnailUri();
        if (!TextUtils.isEmpty(thumbnailUri)) {
            Bitmap thumbnailBitmap = ThumbnailUtils.prepareBitmap(thumbnailUri, thumbnail);
            thumbnail.setImageBitmap(thumbnailBitmap);

            thumbnail.setOnClickListener(new PreviewPhotoOnClickListener(thumbnailUri, MedicineListActivity.class));
        }

        if (currentMedicine.isShareWithFriends()) {
            sharedMedicineBtn.getBackground().setColorFilter(null);
            sharedMedicineBtn.setText(R.string.shared_medicine);
        } else {
            sharedMedicineBtn.getBackground().setColorFilter(Color.parseColor("#FF8F00"), PorterDuff.Mode.SRC_IN);
            sharedMedicineBtn.setText(R.string.private_medicine);
        }
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
