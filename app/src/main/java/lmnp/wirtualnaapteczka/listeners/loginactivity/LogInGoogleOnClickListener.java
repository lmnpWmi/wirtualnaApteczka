package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.activity.MainActivity;

public class LogInGoogleOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        v.getContext().startActivity(intent);
    }
}
