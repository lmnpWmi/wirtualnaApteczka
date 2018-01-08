package lmnp.wirtualnaapteczka.listeners.mainactivity;

import android.view.View;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.R;

public class FamilyListOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Toast.makeText(v.getContext(), R.string.available_soon, Toast.LENGTH_SHORT)
                .show();
    }

//    @Override
//    public void onClick(View v) {
//        Intent intent = new Intent(v.getContext(), FamilyActivity.class);
//        v.getContext().startActivity(intent);
//    }
}
