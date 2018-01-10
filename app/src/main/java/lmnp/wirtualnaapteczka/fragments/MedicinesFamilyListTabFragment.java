package lmnp.wirtualnaapteczka.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import lmnp.wirtualnaapteczka.R;

/**
 * Created by Jowita on 2018-01-06.
 */

public class MedicinesFamilyListTabFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.family_tab_medicines_list, container, false);
        return myView;
    }


}
