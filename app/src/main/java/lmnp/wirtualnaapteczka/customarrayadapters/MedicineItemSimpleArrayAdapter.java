package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
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
import lmnp.wirtualnaapteczka.listeners.mainactivity.RecentlyUsedMedicineOnClickListener;
import lmnp.wirtualnaapteczka.utils.AdaptersCommonUtils;
import lmnp.wirtualnaapteczka.utils.MedicineTypeUtils;

import java.util.List;

public class MedicineItemSimpleArrayAdapter extends ArrayAdapter<Medicine> {

    private List<Medicine> medicines;
    private MainActivity mainActivity;

    private LinearLayout itemPanel;
    private TextView name;
    private TextView amount;
    private ImageView thumbnail;

    public MedicineItemSimpleArrayAdapter(MainActivity mainActivity, int resource, List<Medicine> medicines) {
        super(mainActivity, resource, medicines);

        this.medicines = medicines;
        this.mainActivity = mainActivity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Medicine currentMedicine = medicines.get(position);

        LayoutInflater inflater = (LayoutInflater) mainActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_simple_item, null);

        itemPanel = (LinearLayout) view.findViewById(R.id.medicine_simple_item_panel);
        name = (TextView) view.findViewById(R.id.medicine_simple_item_name);
        amount = (TextView) view.findViewById(R.id.medicine_simple_item_amount);
        thumbnail = (ImageView) view.findViewById(R.id.medicine_simple_item_thumbnail);

        name.setText(currentMedicine.getName());

        String amountText = AdaptersCommonUtils.prepareAmountText(currentMedicine.getAmount(), mainActivity) + " " + MedicineTypeUtils.prepareLocalizedTypeSuffix(currentMedicine.getType(), mainActivity);
        amount.setText(amountText);

//        thumbnail.setImageURI(AdaptersCommonUtils.prepareThumbnailUri(currentMedicine.getThumbnailUri()));

        itemPanel.setOnClickListener(new RecentlyUsedMedicineOnClickListener(mainActivity, currentMedicine, amount));

        return view;
    }
}