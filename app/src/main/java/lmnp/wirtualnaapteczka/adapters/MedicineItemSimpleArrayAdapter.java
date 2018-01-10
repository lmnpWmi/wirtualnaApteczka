package lmnp.wirtualnaapteczka.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MainActivity;
import lmnp.wirtualnaapteczka.data.dto.PhotoDescriptionTO;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.common.PreviewPhotoOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.RecentlyUsedMedicineOnClickListener;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;
import lmnp.wirtualnaapteczka.utils.PhotoUtils;

import java.util.List;

/**
 * Adapter for filling layouts of recently used medicines.
 *
 * @author Sebastian Nowak
 * @createdAt 28.12.2017
 */
public class MedicineItemSimpleArrayAdapter extends ArrayAdapter<Medicine> {

    private List<Medicine> medicines;
    private Context context;

    private LinearLayout itemPanel;
    private TextView name;
    private TextView amount;
    private ImageView thumbnail;

    private AdaptersCommonUtils adaptersCommonUtils;

    public MedicineItemSimpleArrayAdapter(Context context, int resource, List<Medicine> medicines) {
        super(context, resource, medicines);

        this.medicines = medicines;
        this.context = context;
        this.adaptersCommonUtils = new AdaptersCommonUtils(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Medicine currentMedicine = medicines.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_simple_item, null);

        itemPanel = (LinearLayout) view.findViewById(R.id.medicine_simple_item_panel);
        name = (TextView) view.findViewById(R.id.medicine_simple_item_name);
        amount = (TextView) view.findViewById(R.id.medicine_simple_item_amount);
        thumbnail = (ImageView) view.findViewById(R.id.medicine_simple_item_thumbnail);

        name.setText(currentMedicine.getName());

        String amountText = adaptersCommonUtils.prepareAmountText(currentMedicine.getAmount()) + " " + MedicineTypeUtils.prepareLocalizedTypeSuffix(currentMedicine.getType(), context);
        amount.setText(amountText);

        PhotoDescriptionTO photoDescriptionTO = currentMedicine.getPhotoDescriptionTO();
        boolean arePhotosPhysicallyPresentOnDevice = PhotoUtils.arePhotosPhysicallyPresentOnDevice(photoDescriptionTO);

        if (!photoDescriptionTO.isEmpty() && arePhotosPhysicallyPresentOnDevice) {
            Bitmap thumbnailBitmap = PhotoUtils.prepareBitmap(photoDescriptionTO.getSmallSizePhotoUri(), thumbnail);

            if (thumbnailBitmap != null) {
                thumbnail.setImageBitmap(thumbnailBitmap);
                thumbnail.setOnClickListener(new PreviewPhotoOnClickListener(photoDescriptionTO.getFullSizePhotoUri(), MainActivity.class));
            }
        }

        itemPanel.setOnClickListener(new RecentlyUsedMedicineOnClickListener(context, currentMedicine, MainActivity.class));

        return view;
    }
}
