package lmnp.wirtualnaapteczka;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class MedicineAdapter extends ArrayAdapter<Medicine> {

    public MedicineAdapter(Activity context, ArrayList<Medicine> medicines) {
        super(context, 0, medicines);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Medicine currentMedicine = getItem(position);
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        TextView nameMedicine = (TextView) listItemView.findViewById(R.id.text_medicine_name);
        nameMedicine.setText(currentMedicine.getNameMedicine());
        TextView kindOfMedicine = (TextView) listItemView.findViewById(R.id.text_kind_of_medicine);
        kindOfMedicine.setText(currentMedicine.getKindOfMedicine());
        return listItemView;
    }

}
