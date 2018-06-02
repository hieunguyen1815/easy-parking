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

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.domain.parkinglot.ParkingLot;
import app.android.easygroup.easyparking.domain.ticket.Ticket;
import app.android.easygroup.easyparking.services.internal.ticket.ReservationService;

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
        new ReserveParkingLotTask(this, parkingLot).execute("23H24221");
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public class ReserveParkingLotTask extends AsyncTask<String, Void, Ticket> {

        private ReservationDialogFragment self;

        private ParkingLot parkingLot;

        public ReserveParkingLotTask(ReservationDialogFragment self, ParkingLot parkingLot) {
            this.self = self;
            this.parkingLot = parkingLot;
        }

        @Override
        protected Ticket doInBackground(String... plates) {
            try {
                return ReservationService.getInstance().reserveParkingLot(parkingLot, plates[0]);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Ticket ticket) {
            if (ticket != null) {
                int x = 0;
                self.dismiss();
            }
        }
    }
}
