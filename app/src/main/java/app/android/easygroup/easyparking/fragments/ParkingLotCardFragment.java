package app.android.easygroup.easyparking.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.android.easygroup.easyparking.NavigationHost;
import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.domain.parkinglot.ParkingLot;

public class ParkingLotCardFragment extends Fragment {

    private static final String PARKING_LOT_KEY = "parkingLot";

    public static ParkingLotCardFragment getInstance(ParkingLot parkingLot) {
        ParkingLotCardFragment fragment = new ParkingLotCardFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(PARKING_LOT_KEY, parkingLot);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parking_lot_card, container, false);

        final ParkingLot parkingLot = (ParkingLot) getArguments().getSerializable(PARKING_LOT_KEY);

        final TextView nameTextView = view.findViewById(R.id.name_text_view);
        final TextView addressTextView = view.findViewById(R.id.address_text_view);
        final TextView hourTextView = view.findViewById(R.id.hour_value_text_view);
        final TextView feeTextView = view.findViewById(R.id.fee_value_text_view);
        final TextView distanceTextView = view.findViewById(R.id.distance_value_text_view);

        final MaterialButton infoButton = view.findViewById(R.id.info_button);
        final MaterialButton bookingButton = view.findViewById(R.id.booking_button);

        if (parkingLot != null) {
            nameTextView.setText(parkingLot.name);
            addressTextView.setText(parkingLot.address.getShortAddress());
            hourTextView.setText(parkingLot.getOperationTime());
            feeTextView.setText(parkingLot.getDisplayPrice());

            infoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((NavigationHost) getActivity()).navigateTo(ParkingLotDetailsFragment.getInstance(parkingLot), true);
                }
            });

            bookingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showDialog(parkingLot);
                }
            });
        }

        return view;
    }

    public void showDialog(ParkingLot parkingLot) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ReservationDialogFragment fragment = ReservationDialogFragment.getInstance(parkingLot);
        fragment.show(fragmentManager, "reservation");
        /*// The device is smaller, so show the fragment fullscreen
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // For a little polish, specify a transition animation
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // To make it fullscreen, use the 'content' root view as the container
        // for the fragment, which is always the root view for the activity
        transaction.add(android.R.id.content, ).addToBackStack(null).commit();*/
    }
}
