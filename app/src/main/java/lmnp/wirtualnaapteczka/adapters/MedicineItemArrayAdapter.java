package lmnp.wirtualnaapteczka.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MedicineListActivity;
import lmnp.wirtualnaapteczka.data.dto.PhotoDescriptionTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.common.PreviewPhotoOnClickListener;
import lmnp.wirtualnaapteczka.listeners.medicinelistactivity.MedicineItemLongClickListener;
import lmnp.wirtualnaapteczka.listeners.medicinelistactivity.MedicineItemOnClickListener;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;
import lmnp.wirtualnaapteczka.utils.PhotoUtils;

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

    private TextView nameTextView;
    private TextView typeTextView;
    private TextView amountTextView;
    private TextView createdAtTextView;
    private TextView dueDateTextView;
    private ImageView thumbnailImageView;
    private Button sharedMedicineBtn;

    private AdaptersCommonUtils adaptersCommonUtils;

    public MedicineItemArrayAdapter(Context context, int resource, List<Medicine> medicines) {
        super(context, resource, medicines);

        this.medicines = medicines;
        this.context = context;
        this.adaptersCommonUtils = new AdaptersCommonUtils(context);
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

        nameTextView = (TextView) view.findViewById(R.id.medicine_item_name);
        typeTextView = (TextView) view.findViewById(R.id.medicine_item_type);
        amountTextView = (TextView) view.findViewById(R.id.medicine_item_amount);
        createdAtTextView = (TextView) view.findViewById(R.id.medicine_item_createdat);
        dueDateTextView = (TextView) view.findViewById(R.id.medicine_item_duedate);
        thumbnailImageView = (ImageView) view.findViewById(R.id.medicine_simple_item_thumbnail);
        sharedMedicineBtn = (Button) view.findViewById(R.id.share_medicine_btn);
    }

    private void updateComponentsValues(Medicine currentMedicine) {
        listItemPanel.setOnClickListener(new MedicineItemOnClickListener(currentMedicine, MedicineListActivity.class));
        listItemPanel.setOnLongClickListener(new MedicineItemLongClickListener(currentMedicine, context));

        nameTextView.setText(currentMedicine.getName());
        typeTextView.setText(adaptersCommonUtils.prepareMedicineTypeText(currentMedicine.getType()));
        createdAtTextView.setText(adaptersCommonUtils.prepareCreatedAtText(currentMedicine.getCreatedAt()));

        Date dueDate = currentMedicine.getDueDate();
        dueDateTextView.setText(adaptersCommonUtils.prepareDueDateText(dueDate));
        adaptersCommonUtils.markFieldIfOverdue(dueDate, dueDateTextView);

        String amountText = adaptersCommonUtils.prepareAmountText(currentMedicine.getAmount()) + " " + MedicineTypeUtils.prepareLocalizedTypeSuffix(currentMedicine.getType(), context);
        amountTextView.setText(amountText);

        PhotoDescriptionTO photoDescriptionTO = currentMedicine.getPhotoDescriptionTO();
        boolean arePhotosPhysicallyPresentOnDevice = PhotoUtils.arePhotosPhysicallyPresentOnDevice(photoDescriptionTO);

        if (!photoDescriptionTO.isEmpty() && arePhotosPhysicallyPresentOnDevice) {
            Bitmap thumbnailBitmap = PhotoUtils.prepareBitmap(photoDescriptionTO.getSmallSizePhotoUri(), thumbnailImageView);

            if (thumbnailBitmap != null) {
                thumbnailImageView.setImageBitmap(thumbnailBitmap);
                thumbnailImageView.setOnClickListener(new PreviewPhotoOnClickListener(photoDescriptionTO.getFullSizePhotoUri(), MedicineListActivity.class));
            }
        }

        if (currentMedicine.isShareWithFriends()) {
            sharedMedicineBtn.getBackground().setColorFilter(null);
            sharedMedicineBtn.setText(R.string.shared_medicine);
        } else {
            sharedMedicineBtn.getBackground().setColorFilter(Color.parseColor("#FF8F00"), PorterDuff.Mode.SRC_IN);
            sharedMedicineBtn.setText(R.string.private_medicine);
        }
    }
}
