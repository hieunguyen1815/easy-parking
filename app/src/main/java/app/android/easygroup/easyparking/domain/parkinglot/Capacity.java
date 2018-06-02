package app.android.easygroup.easyparking.domain.parkinglot;

import java.io.Serializable;

public class Capacity implements Serializable {

    public String id;

    public String parkingLot;

    public String type;

    public Integer maximumCapacity;

    public Integer numOfBookedRooms;

    public Boolean isBookable;

    public Capacity() { }
}
