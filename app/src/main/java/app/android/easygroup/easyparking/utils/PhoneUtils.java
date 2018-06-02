package app.android.easygroup.easyparking.utils;

public class PhoneUtils {

    private static final String DEFAULT_COUNTRY_CODE = "84";

    public static String getNumber(String phoneNumber) {
        String result = phoneNumber.replace("+", "");
        if (phoneNumber.startsWith("84")) {
            return result.substring(2);
        } else {
            return result.startsWith("0") ? result.substring(1) : result;
        }
    }
}
