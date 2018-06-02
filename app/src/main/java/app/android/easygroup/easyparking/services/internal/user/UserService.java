package app.android.easygroup.easyparking.services.internal.user;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.APIFactory;
import app.android.easygroup.easyparking.HttpClient;
import app.android.easygroup.easyparking.application.EasyParkingApplication;
import app.android.easygroup.easyparking.domain.user.User;
import app.android.easygroup.easyparking.utils.PhoneUtils;

public class UserService {

    private static final String ACCESS_TOKEN_KEY = "accessToken";

    private static UserService instance;

    private UserService() {}

    public static UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public User login(String phoneNumber) throws InterruptedException, ExecutionException, TimeoutException {
        Gson gson = new Gson();
        String res = HttpClient.getInstance().post(
                APIFactory.api(APIFactory.API.LOGIN),
                null,
                ImmutableMap.of("countryCode", "84", "phoneNumber", PhoneUtils.getNumber(phoneNumber)),
                null);

        User user = gson.fromJson(res, User.class);
        return user;
    }

    public User verify(String userId, String otp) throws InterruptedException, ExecutionException, TimeoutException {
        Gson gson = new Gson();
        String res = HttpClient.getInstance().post(
                APIFactory.api(APIFactory.API.VERIFY),
                new String[] {userId},
                ImmutableMap.of("smsCode", otp),
                null);

        User user = gson.fromJson(res, User.class);
        if (!TextUtils.isEmpty(user.accessToken)) {
            saveAccessToken(user.accessToken);
        }
        return user;
    }

    public User logout() {
        deleteAccessToken();
        return null;
    }

    public boolean isLogingin() {
        return !TextUtils.isEmpty(getAccessToken());
    }

    public String getAccessToken() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EasyParkingApplication.getAppContext());
        return preferences.getString(ACCESS_TOKEN_KEY, null);
    }

    private void saveAccessToken(String accessToken) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EasyParkingApplication.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ACCESS_TOKEN_KEY, accessToken);
        editor.apply();
    }

    private void deleteAccessToken() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(EasyParkingApplication.getInstance());
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(ACCESS_TOKEN_KEY);
        editor.apply();
    }
}
