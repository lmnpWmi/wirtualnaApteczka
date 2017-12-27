package lmnp.wirtualnaapteczka.listeners.previewphotoactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class GoBackOnClickListener implements View.OnClickListener {
    private Class<? extends AppCompatActivity> invokingClass;

    public GoBackOnClickListener(Class<? extends AppCompatActivity> invokingClass) {
        this.invokingClass = invokingClass;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(v.getContext(), invokingClass);
        v.getContext().startActivity(intent);
    }
}
