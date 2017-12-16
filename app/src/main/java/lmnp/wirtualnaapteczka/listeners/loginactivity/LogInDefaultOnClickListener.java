package lmnp.wirtualnaapteczka.listeners.loginactivity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import lmnp.wirtualnaapteczka.activity.AddMedicineActivity;
import lmnp.wirtualnaapteczka.activity.MainActivity;

public class LogInDefaultOnClickListener implements View.OnClickListener {
    private String email;
    private String password;

    public LogInDefaultOnClickListener(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        v.getContext().startActivity(intent);
    }
}
