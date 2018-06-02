package app.android.easygroup.easyparking;

public class APIFactory {

    private static final String HOST = "https://dev.easyparking.com.vn";

    public static String api(API api) {
        return HOST + api.getValue();
    }

    public enum API {
        LOGIN("/auth/local/register"),
        VERIFY("/api/users/%s/verify/sms"),
        GET_PARKING_LOTS_IN_RADIUS("/api/parkinglots"),
        RESERVE_PARKING_LOT("/api/parkinglots/%s/book");

        private String value;

        API(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
