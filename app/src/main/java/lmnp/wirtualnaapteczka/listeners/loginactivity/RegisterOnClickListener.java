package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.content.Intent;
import android.view.View;
import lmnp.wirtualnaapteczka.activity.RegistrationActivity;

public class RegisterOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), RegistrationActivity.class);
        v.getContext().startActivity(intent);
    }
}
