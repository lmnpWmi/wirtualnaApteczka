package lmnp.wirtualnaapteczka.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.listeners.loginactivity.*;
import lmnp.wirtualnaapteczka.services.DbService;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * Activity responsible for handling events on the layout for logging in.
 *
 * @author Sebastian Nowak
 * @createdAt 07.01.2018
 */
public class LogInActivity extends AppCompatActivity {
    private Button logInGoogleBtn;
    private Button logInDefaultBtn;
    private TextView registerBtn;
    private TextView forgotPasswordBtn;
    private TextView regulations;

    private EditText emailText;
    private EditText passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        verifyNecessaryPermissions();

        initializeViewComponents();
        initializeComponentsListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AppConstants.GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(this, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstants.PERMISSION_REQUEST:
                boolean permissionGranted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;

                if (!permissionGranted) {
                    Toast.makeText(this, R.string.insufficient_permissions, Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        SessionManager.closeApplication(this);
    }

    private void verifyNecessaryPermissions() {
        int permissionCheckCamera = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CAMERA);

        if (permissionCheckCamera != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.CAMERA},
                    AppConstants.PERMISSION_REQUEST);
        }

        int permissionCheckInternet = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.INTERNET);

        if (permissionCheckInternet != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.INTERNET, Manifest.permission.INTERNET},
                    AppConstants.PERMISSION_REQUEST);
        }
    }

    private void initializeViewComponents() {
        logInGoogleBtn = (Button) findViewById(R.id.log_in_google_btn);
        logInDefaultBtn = (Button) findViewById(R.id.log_in_default_btn);
        registerBtn = (TextView) findViewById(R.id.register_btn);
        forgotPasswordBtn = (TextView) findViewById(R.id.forgot_password_btn);
        regulations = (TextView) findViewById(R.id.regulations_btn);

        emailText = (EditText) findViewById(R.id.email_login_text);
        emailText.setSelected(false);

        updateRememberedEmail();

        passwordText = (EditText) findViewById(R.id.password_login_text);
        passwordText.setTypeface(Typeface.DEFAULT);
        passwordText.setTransformationMethod(new PasswordTransformationMethod());
        passwordText.setSelected(false);
    }

    private void initializeComponentsListeners() {
        logInGoogleBtn.setOnClickListener(new LogInGoogleOnClickListener(this));

        logInDefaultBtn.setOnClickListener(new LogInDefaultOnClickListener(this));

        regulations.setOnClickListener(new RegulationsOnClickListener());
        forgotPasswordBtn.setOnClickListener(new ForgotPasswordOnClickListener());
        registerBtn.setOnClickListener(new RegisterOnClickListener());
    }

    private void updateRememberedEmail() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(AppConstants.APP_SETTINGS, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(AppConstants.EMAIL, null);

        if (!TextUtils.isEmpty(email)) {
            emailText.setText(email);
        }
    }

    private void firebaseAuthWithGoogle(final GoogleSignInAccount acct) {
        FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String displayName = acct.getDisplayName();
                            String email = acct.getEmail();
                            UserRegistrationTO userRegistrationTO = new UserRegistrationTO(displayName, email);

                            DbService dbService = SessionManager.getDbService();
                            dbService.createUserAccountCreatorListener(userRegistrationTO);

                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            LogInActivity.this.startActivity(intent);
                        } else {
                            Toast.makeText(LogInActivity.this, R.string.authentication_failed, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
