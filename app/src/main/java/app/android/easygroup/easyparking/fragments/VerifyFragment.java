package app.android.easygroup.easyparking.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.NavigationHost;
import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.activities.MainActivity;
import app.android.easygroup.easyparking.domain.user.User;
import app.android.easygroup.easyparking.services.internal.user.UserService;
import app.android.easygroup.easyparking.utils.UIUtils;

public class VerifyFragment extends Fragment {

    private static final String USER_ID_KEY = "userId";

    private TextInputEditText number1, number2, number3, number4;

    private String userId;

    public static VerifyFragment getInstance(String userId) {
        VerifyFragment fragment = new VerifyFragment();

        Bundle args = new Bundle();
        args.putString(USER_ID_KEY, userId);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verify, container, false);
        number1 = view.findViewById(R.id.otp_number1_edit_text);
        number2 = view.findViewById(R.id.otp_number2_edit_text);
        number3 = view.findViewById(R.id.otp_number3_edit_text);
        number4 = view.findViewById(R.id.otp_number4_edit_text);

        number1.addTextChangedListener(new OTPCodeWatcher(null, number2));
        number2.addTextChangedListener(new OTPCodeWatcher(number1, number3));
        number3.addTextChangedListener(new OTPCodeWatcher(number2, number4));
        number4.addTextChangedListener(new OTPCodeWatcher(number3,null));

        try {
            userId = getArguments().getString(USER_ID_KEY);
        } catch (NullPointerException ignored) { }


        return view;
    }

    private class OTPCodeWatcher implements TextWatcher {

        private TextInputEditText previous;
        private TextInputEditText next;

        public OTPCodeWatcher(TextInputEditText previous, TextInputEditText next) {
            this.previous = previous;
            this.next = next;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // do nothing
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            // do nothing
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (next != null && editable.length() == 1) {
                next.requestFocus();
            } else if (previous != null && editable.length() == 0) {
                previous.requestFocus();
            } else if (next == null) {
                if (getActivity() != null) {
                    UIUtils.hideVirtualKeyBoard(getActivity());
                    new VerityTask(userId).execute(code(number1.getText()) + code(number2.getText()) + code(number3.getText()) + code(number4.getText()));
                }
            }
        }

        private String code(Editable editable) {
            return editable == null ? "" : editable.toString();
        }
    }

    private class VerityTask extends AsyncTask<String, Void, User> {

        private String userId;

        public VerityTask(String userId) {
            this.userId = userId;
        }

        @Override
        protected User doInBackground(String... codes) {
            try {
                return UserService.getInstance().verify(userId, codes[0]);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null) {
                ((NavigationHost) getActivity()).navigateTo(MainActivity.class, null, false);
            }
        }
    }
}
