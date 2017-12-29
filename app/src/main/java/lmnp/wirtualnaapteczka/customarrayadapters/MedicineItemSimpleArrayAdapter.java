package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.MainActivity;
import lmnp.wirtualnaapteczka.data.entities.Medicine;
import lmnp.wirtualnaapteczka.listeners.common.PreviewPhotoOnClickListener;
import lmnp.wirtualnaapteczka.listeners.mainactivity.RecentlyUsedMedicineOnClickListener;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;
import lmnp.wirtualnaapteczka.utils.ThumbnailUtils;

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

    public MedicineItemSimpleArrayAdapter(Context context, int resource, List<Medicine> medicines) {
        super(context, resource, medicines);

        this.medicines = medicines;
        this.context = context;
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

        String amountText = AdaptersCommonUtils.prepareAmountText(currentMedicine.getAmount(), context) + " " + MedicineTypeUtils.prepareLocalizedTypeSuffix(currentMedicine.getType(), context);
        amount.setText(amountText);

        String thumbnailUri = currentMedicine.getThumbnailUri();

        if (!TextUtils.isEmpty(thumbnailUri)) {
            Bitmap thumbnailBitmap = ThumbnailUtils.prepareBitmap(thumbnailUri, thumbnail);
            thumbnail.setImageBitmap(thumbnailBitmap);

            thumbnail.setOnClickListener(new PreviewPhotoOnClickListener(thumbnailUri, MainActivity.class));
        }

        itemPanel.setOnClickListener(new RecentlyUsedMedicineOnClickListener(context, currentMedicine));

        return view;
    }
}
