package lmnp.wirtualnaapteczka.customarrayadapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.entities.Medicine;

import java.util.List;

public class MedicineItemArrayAdapter extends ArrayAdapter<Medicine> {

    private List<Medicine> medicines;
    private Context context;

    public MedicineItemArrayAdapter(Context context, int resource, List<Medicine> medicines) {
        super(context, resource);

        this.medicines = medicines;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.list_item, null);

        return view;
    }
}
