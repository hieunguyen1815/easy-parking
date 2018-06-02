package app.android.easygroup.easyparking.services.internal.user;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.APIFactory;
import app.android.easygroup.easyparking.HttpClient;
import app.android.easygroup.easyparking.domain.user.User;
import app.android.easygroup.easyparking.utils.PhoneUtils;

public class UserService {

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
        return user;
    }
}
