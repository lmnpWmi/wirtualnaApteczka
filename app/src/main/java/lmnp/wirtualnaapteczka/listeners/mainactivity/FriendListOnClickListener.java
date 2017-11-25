package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.view.View;
import android.widget.Toast;

public class FriendListOnClickListener implements View.OnClickListener{
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), "Przeglądanie leków znajomych jest niedostępne w wersji offline.", Toast.LENGTH_SHORT)
                .show();
    }
}
