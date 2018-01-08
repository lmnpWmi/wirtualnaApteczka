package lmnp.wirtualnaapteczka.listeners.registrationactivity;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import lmnp.wirtualnaapteczka.R;
import lmnp.wirtualnaapteczka.data.dto.UserRegistrationTO;
import lmnp.wirtualnaapteczka.session.SessionManager;
import lmnp.wirtualnaapteczka.utils.AppConstants;

public class RegisterNewUserOnClickListener implements View.OnClickListener {
    private EditText usernameEditText;
    private EditText emailText;
    private EditText passwordText;
    private EditText repeatPasswordText;

    public RegisterNewUserOnClickListener() {
    }

    @Override
    public void onClick(View v) {
        View registrationLayoutView = v.getRootView();

        initializeViewComponents(registrationLayoutView);
        UserRegistrationTO userRegistrationTO = prepareUserRegistrationTO();

        boolean isValidUserRegistrationData = validateUserRegistrationData(v.getContext(), userRegistrationTO);

        if (isValidUserRegistrationData) {
            FirebaseAuth firebaseAuth = SessionManager.getFirebaseAuth();
            firebaseAuth.createUserWithEmailAndPassword(userRegistrationTO.getEmail(), userRegistrationTO.getPassword())
                    .addOnCompleteListener(new RegistrationOnCompleteListener(v.getContext(), userRegistrationTO));
        }
    }

    private void initializeViewComponents(View v) {
        usernameEditText = (EditText) v.findViewById(R.id.register_username_text);
        emailText = (EditText) v.findViewById(R.id.register_email_text);
        passwordText = (EditText) v.findViewById(R.id.register_password_text);
        repeatPasswordText = (EditText) v.findViewById(R.id.register_password_repeat_text);
    }

    private UserRegistrationTO prepareUserRegistrationTO() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();
        String repeatedPassword = repeatPasswordText.getText().toString().trim();

        return new UserRegistrationTO(username, email, password, repeatedPassword);
    }

    private boolean validateUserRegistrationData(Context context, UserRegistrationTO userRegistrationTO) {
        StringBuilder errorMessageBuilder = new StringBuilder();
        String errorMessage = null;

        if (TextUtils.isEmpty(userRegistrationTO.getUsername())) {
            errorMessage = context.getResources().getString(R.string.username_empty_err_msg);
            appendErrorMessage(errorMessageBuilder, errorMessage);
        }

        if (TextUtils.isEmpty(userRegistrationTO.getEmail())) {
            errorMessage = context.getResources().getString(R.string.email_empty_err_msg);
            appendErrorMessage(errorMessageBuilder, errorMessage);
        }

        if (TextUtils.isEmpty(userRegistrationTO.getPassword())) {
            errorMessage = context.getResources().getString(R.string.password_empty_err_msg);
            appendErrorMessage(errorMessageBuilder, errorMessage);
        } else if (userRegistrationTO.getPassword().length() < AppConstants.MINIMUM_PASSWORD_LENGTH) {
            errorMessage = context.getResources().getString(R.string.password_length_err_msg);
            appendErrorMessage(errorMessageBuilder, errorMessage);
        } else if (TextUtils.isEmpty(userRegistrationTO.getRepeatedPassword())) {
            errorMessage = context.getResources().getString(R.string.repeat_password_empty_err_msg);
            appendErrorMessage(errorMessageBuilder, errorMessage);

        } else if (!userRegistrationTO.getPassword().equals(userRegistrationTO.getRepeatedPassword())) {
            errorMessage = context.getResources().getString(R.string.incorrect_passwords_err_msg);
            appendErrorMessage(errorMessageBuilder, errorMessage);
        }

        if (errorMessageBuilder.length() > 0) {
            errorMessageBuilder.deleteCharAt(errorMessageBuilder.lastIndexOf("\n"));
        }
        String errorMessageTotal = errorMessageBuilder.toString();

        boolean isValidUserRegistrationData = TextUtils.isEmpty(errorMessageTotal);

        if (!isValidUserRegistrationData) {
            Toast.makeText(context, errorMessageTotal, Toast.LENGTH_LONG).show();
        }

        return isValidUserRegistrationData;
    }

    private void appendErrorMessage(StringBuilder errorMessageBuilder, String errorMessage) {
        errorMessageBuilder.append(errorMessage);
        errorMessageBuilder.append('\n');
    }
}
