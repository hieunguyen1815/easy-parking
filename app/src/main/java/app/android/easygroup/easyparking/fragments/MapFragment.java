package app.android.easygroup.easyparking.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.NavigationHost;
import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.domain.parkinglot.ParkingLot;
import app.android.easygroup.easyparking.services.internal.parkinglot.ParkingLotService;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private SupportMapFragment mapFragment;
    private GoogleMap mMap;

    private ParkingLot[] parkingLots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            mapFragment.getMapAsync(this);
        }
        getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();

        return view;
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng startPosition = new LatLng(10.768277, 106.6954475);

        CameraPosition cameraOpt = new CameraPosition.Builder()
                .target(startPosition)
                .zoom(16.5f)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraOpt));

        mMap.setOnMarkerClickListener(this);

        new FetchParkingLotsInRadius(this).execute(startPosition);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        for (ParkingLot parkingLot : parkingLots) {
            if (parkingLot.latitude == latLng.latitude && parkingLot.longitude == latLng.longitude) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.parking_lot_card_container, ParkingLotCardFragment.getInstance(parkingLot))
                        .commit();
                break;
            }
        }
        return false;
    }

    public class FetchParkingLotsInRadius extends AsyncTask<LatLng, Void, ParkingLot[]> {

        private MapFragment self;

        public FetchParkingLotsInRadius(MapFragment self) {
            this.self = self;
        }

        @Override
        protected ParkingLot[] doInBackground(LatLng... latLngs) {
            try {
                return ParkingLotService.getInstance().getParkingLotsInRadius(latLngs[0].latitude, latLngs[0].longitude);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ParkingLot[] parkingLots) {
            if (parkingLots != null) {
                mMap.clear();
                self.parkingLots = parkingLots;

                for (ParkingLot parkingLot : parkingLots) {
                    MarkerOptions markerOpt = new MarkerOptions()
                            .position(parkingLot.getLatLng());
                    mMap.addMarker(markerOpt);
                }

                CircleOptions circleOpt = new CircleOptions()
                        .center(new LatLng(10.768277, 106.6954475))
                        .radius(500)
                        .strokeWidth(1)
                        .strokeColor(Color.parseColor("#03A9F4"))
                        .fillColor(Color.parseColor("#3303A9F4"));
                mMap.addCircle(circleOpt);
            }
        }
    }
}
