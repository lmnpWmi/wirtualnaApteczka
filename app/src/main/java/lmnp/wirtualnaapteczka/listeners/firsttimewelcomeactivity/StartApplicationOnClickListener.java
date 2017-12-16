package lmnp.wirtualnaapteczka.listeners.firsttimewelcomeactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.LogInActivity;

public class StartApplicationOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), LogInActivity.class);
        v.getContext().startActivity(intent);
    }
}
