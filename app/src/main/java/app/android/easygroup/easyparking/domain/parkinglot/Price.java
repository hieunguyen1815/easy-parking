package app.android.easygroup.easyparking.domain.parkinglot;

import java.io.Serializable;

public class Price implements Serializable {

    public String id;

    public String parkingLot;

    public String type;

    public String unit;

    public Long amount;

    public Price() { }
}
