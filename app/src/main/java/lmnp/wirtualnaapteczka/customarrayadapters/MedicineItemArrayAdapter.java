package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.entities.Medicine;

import java.text.DateFormat;
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
        name.setTypeface(Typeface.DEFAULT_BOLD);

        type.setText(currentMedicine.getType().name());

        amount.setText(String.valueOf(currentMedicine.getAmount()));

//        if (!TextUtils.isEmpty(currentMedicine.getThumbnailUri())) {
//            Uri thumbnailUri = new Uri.Builder().appendPath(currentMedicine.getThumbnailUri()).build();
//            thumbnail.setImageURI(thumbnailUri);
//        }

        if (currentMedicine.getDueDate() != null) {
            DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getContext().getApplicationContext());
            String formattedDueDate = dateFormat.format(currentMedicine.getDueDate());
            dueDate.setText(formattedDueDate);
        }

        return view;
    }
}
