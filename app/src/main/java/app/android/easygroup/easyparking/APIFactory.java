package app.android.easygroup.easyparking;

public class APIFactory {

    private static final String HOST = "http://18.216.227.22";

    public static String api(API api) {
        return HOST + api.getValue();
    }

    public enum API {
        LOGIN("/auth/local/register"),
        VERIFY("/api/users/%s/verify/sms");

        private String value;

        API(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
