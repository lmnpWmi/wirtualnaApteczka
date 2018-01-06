package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.content.Intent;
import android.view.View;

import lmnp.wirtualnaapteczka.FamilyActivity;

public class FriendListOnClickListener implements View.OnClickListener {
//    @Override
//    public void onClick(View v) {
//        Toast.makeText(v.getContext(), "Przeglądanie leków znajomych jest niedostępne w wersji offline.", Toast.LENGTH_SHORT)
//                .show();
//    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), FamilyActivity.class);

        v.getContext().startActivity(intent);
    }
}
