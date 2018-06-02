package app.android.easygroup.easyparking.domain.parkinglot;

import java.io.Serializable;

public class Address implements Serializable {

    public String id;

    public String country;

    public String city;

    public String district;

    public String ward;

    public String street;

    public String number;

    public Address() { }

    public String getFullAddress() {
        return number +
                " " +
                street +
                ", " +
                ward +
                ", " +
                district +
                ", " +
                city +
                ", " +
                country;
    }

    public String getNormalAddress() {
        return number +
                " " +
                street +
                ", " +
                ward +
                ", " +
                district +
                ", " +
                city;
    }

    public String getShortAddress() {
        return number +
                " " +
                street +
                ", " +
                ward +
                ", " +
                district;
    }
}
