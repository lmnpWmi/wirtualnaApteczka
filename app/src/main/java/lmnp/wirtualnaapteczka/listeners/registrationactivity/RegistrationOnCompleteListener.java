package lmnp.wirtualnaapteczka.listeners.registrationactivity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.activity.LogInActivity;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;

public class RegistrationOnCompleteListener implements OnCompleteListener<AuthResult> {
    private Context context;
    private UserRegistrationTO userRegistrationTO;

    public RegistrationOnCompleteListener(Context context, UserRegistrationTO userRegistrationTO) {
        this.context = context;
        this.userRegistrationTO = userRegistrationTO;
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        int messageId = R.string.user_not_registered;

        if (task.isSuccessful()) {
            FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();
            FirebaseUser currentUser = firebaseAuth.getCurrentUser();

            if (currentUser != null) {
                currentUser.sendEmailVerification();
                DbService dbService = SessionManager.getDbService();
                dbService.createUserDataInFirebase(userRegistrationTO);

                messageId = R.string.verification_email_sent;
            }

            Intent intent = new Intent(context, LogInActivity.class);
            context.startActivity(intent);
        }

        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
    }
}
