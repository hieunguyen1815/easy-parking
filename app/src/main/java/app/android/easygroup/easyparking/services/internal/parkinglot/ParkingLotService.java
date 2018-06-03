package app.android.easygroup.easyparking.services.internal.parkinglot;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.APIFactory;
import app.android.easygroup.easyparking.HttpClient;
import app.android.easygroup.easyparking.domain.parkinglot.ParkingLot;

public class ParkingLotService {

    private static final int DEFAULT_RADIUS = 500;

    private static ParkingLotService instance;

    private ParkingLotService() {}

    public static ParkingLotService getInstance() {
        if (instance == null) {
            instance = new ParkingLotService();
        }
        return instance;
    }

    public ParkingLot[] getParkingLotsInRadius(double latitude, double longitude) throws InterruptedException, ExecutionException, TimeoutException {
        Gson gson = new Gson();
        String url = String.format(APIFactory.api(APIFactory.API.GET_PARKING_LOTS_IN_RADIUS) + "?lat=%f&lon=%f&r=%d", latitude, longitude, DEFAULT_RADIUS);
        String res = HttpClient.getInstance().get(
                url,
                null,
                null);

        ParkingLot[] parkingLots = gson.fromJson(res, ParkingLot[].class);
        return parkingLots;
    }
}
