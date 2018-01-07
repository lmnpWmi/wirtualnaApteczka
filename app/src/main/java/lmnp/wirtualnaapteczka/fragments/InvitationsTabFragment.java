package lmnp.wirtualnaapteczka.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import lmnp.wirtualnaapteczka.R;

/**
 * Created by Jowita on 2018-01-06.
 */

public class InvitationsTabFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.tab_invitations, container, false);
        return myView;
    }
}
