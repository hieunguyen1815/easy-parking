package app.android.easygroup.easyparking.services.internal.ticket;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.APIFactory;
import app.android.easygroup.easyparking.HttpClient;
import app.android.easygroup.easyparking.domain.parkinglot.ParkingLot;
import app.android.easygroup.easyparking.domain.ticket.Ticket;
import app.android.easygroup.easyparking.services.internal.user.UserService;

public class ReservationService {

    private static ReservationService instance;

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (instance == null) {
            instance = new ReservationService();
        }
        return instance;
    }

    public Ticket reserveParkingLot(ParkingLot parkingLot, String numberPlate) throws InterruptedException, ExecutionException, TimeoutException {
        Gson gson = new Gson();
        String res = HttpClient.getInstance().post(
                APIFactory.api(APIFactory.API.RESERVE_PARKING_LOT),
                new String[] {parkingLot.id},
                ImmutableMap.of(
                        "vehicleType", "car",
                        "unit", "hour",
                        "paymentMethod", "CASH",
                        "estimatedArrivalTime", "15",
                        "numberPlate", numberPlate
                ),
                ImmutableMap.of("Authorization", UserService.getInstance().getAccessToken()));

        Ticket ticket = gson.fromJson(res, Ticket.class);
        return ticket;
    }
}
