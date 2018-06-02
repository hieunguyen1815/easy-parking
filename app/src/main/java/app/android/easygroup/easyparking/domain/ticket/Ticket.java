package app.android.easygroup.easyparking.domain.ticket;

import java.util.Date;

import app.android.easygroup.easyparking.domain.parkinglot.ParkingLot;

public class Ticket {

    public String id;

    public String user;

    public Long initialPrice;

    public Long finalPrice;

    public Date validBefore;

    public String numberPlate;

    public Boolean isPaid;

    public String status;

    public String paymentMethod;

    public ParkingLot parkingLot;

    public Ticket() {
        parkingLot = new ParkingLot();
    }
}
