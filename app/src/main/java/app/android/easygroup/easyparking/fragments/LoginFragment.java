package app.android.easygroup.easyparking.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.NavigationHost;
import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.activities.AuthActivity;
import app.android.easygroup.easyparking.activities.MainActivity;
import app.android.easygroup.easyparking.domain.user.User;
import app.android.easygroup.easyparking.services.internal.user.UserService;

public class LoginFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        final TextInputLayout phoneNumberTextInput = view.findViewById(R.id.phone_number_text_input);
        final TextInputEditText phoneNumberEditText = view.findViewById(R.id.phone_number_edit_text);
        MaterialButton loginButton = view.findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isPhoneNumberValid(phoneNumberEditText.getText())) {
                    phoneNumberTextInput.setError(getString(R.string.error_phone_number));
                } else {
                    phoneNumberTextInput.setError(null); // Clear the error
                    new LoginTask().execute(phoneNumberEditText.getText().toString());
                }
            }
        });

        phoneNumberEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isPhoneNumberValid(phoneNumberEditText.getText())) {
                    phoneNumberTextInput.setError(null);
                }
                return false;
            }
        });

        return view;
    }

    private boolean isPhoneNumberValid(@Nullable Editable text) {
        return text != null && text.length() > 9;
    }

    private class LoginTask extends AsyncTask<String, Void, User> {
        @Override
        protected User doInBackground(String... phoneNumbers) {
            try {
                UserService.getInstance().login(phoneNumbers[0]);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                ((NavigationHost) getActivity()).navigateTo(VerifyFragment.getInstance(user.id), true);
            }
        }
    }
}
