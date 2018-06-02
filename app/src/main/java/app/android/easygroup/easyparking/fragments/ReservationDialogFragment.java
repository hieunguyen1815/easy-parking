package app.android.easygroup.easyparking.fragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.domain.parkinglot.ParkingLot;

public class ReservationDialogFragment extends DialogFragment {

    private static final String PARKING_LOT_KEY = "parkingLot";

    public static ReservationDialogFragment getInstance(ParkingLot parkingLot) {
        ReservationDialogFragment fragment = new ReservationDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(PARKING_LOT_KEY, parkingLot);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_reservation, container, false);

        ParkingLot parkingLot = (ParkingLot) getArguments().getSerializable(PARKING_LOT_KEY);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public class ReserveParkingLotTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... plates) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
