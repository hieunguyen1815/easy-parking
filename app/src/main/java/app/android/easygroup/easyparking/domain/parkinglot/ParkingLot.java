package app.android.easygroup.easyparking.domain.parkinglot;

import com.google.android.gms.maps.model.LatLng;

public class ParkingLot {

    public String id;

    public String name;

    public Double latitude;

    public Double longitude;

    public String openingTime;

    public String closingTime;

    public Address address;

    public String[] photos;

    public Price[] prices;

    public Capacity[] capacities;

    public ParkingLot() {
        this.photos = new String[] {};
        this.prices = new Price[] {};
        this.capacities = new Capacity[] {};
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longitude);
    }
}
