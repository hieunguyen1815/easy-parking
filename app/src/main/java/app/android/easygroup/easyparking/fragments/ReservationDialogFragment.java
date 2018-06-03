package app.android.easygroup.easyparking.fragments;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.adapters.PricesAdapter;
import app.android.easygroup.easyparking.domain.parkinglot.ParkingLot;
import app.android.easygroup.easyparking.domain.ticket.Ticket;
import app.android.easygroup.easyparking.services.internal.ticket.ReservationService;

public class ReservationDialogFragment extends DialogFragment {

    private static final String PARKING_LOT_KEY = "parkingLot";

    private ConstraintLayout infoConfirmationLayout;
    private ConstraintLayout numberPlateLayout;

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

        infoConfirmationLayout = view.findViewById(R.id.info_confirmation_layout);
        numberPlateLayout = view.findViewById(R.id.number_plate_layout);

        final TextView nameTextView = view.findViewById(R.id.name_text_view);
        final TextView addressTextView = view.findViewById(R.id.address_text_view);
        final MaterialButton cancel1Button = view.findViewById(R.id.cancel1_button);
        final MaterialButton nextButton = view.findViewById(R.id.next_button);
        final RecyclerView parkingLotRecyclerView = view.findViewById(R.id.parking_lot_recycler_view);

        final TextInputLayout plateTextInput = view.findViewById(R.id.plate_text_input);
        final TextInputEditText plateEditText = view.findViewById(R.id.plate_edit_text);
        final MaterialButton cancel2Button = view.findViewById(R.id.cancel2_button);
        final MaterialButton finishButton = view.findViewById(R.id.finish_button);

        final ParkingLot parkingLot = (ParkingLot) getArguments().getSerializable(PARKING_LOT_KEY);
        nameTextView.setText(parkingLot.name);
        addressTextView.setText(parkingLot.address.getShortAddress());
        cancel1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationDialogFragment.this.dismiss();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoConfirmationLayout.setVisibility(View.GONE);
                numberPlateLayout.setVisibility(View.VISIBLE);
            }
        });

        parkingLotRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        parkingLotRecyclerView.setItemAnimator(new DefaultItemAnimator());
        parkingLotRecyclerView.setAdapter(new PricesAdapter(Arrays.asList(parkingLot.prices)));

        plateEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (isValidNumberPlate(plateEditText.getText())) {
                    plateTextInput.setError(null);
                }
                return false;
            }
        });

        cancel2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReservationDialogFragment.this.dismiss();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidNumberPlate(plateEditText.getText())) {
                    plateTextInput.setError(null);
                    new ReserveParkingLotTask(ReservationDialogFragment.this, parkingLot).execute(plateEditText.getText().toString());
                } else {
                    plateEditText.setError(getString(R.string.error_number_plate));
                }
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    private boolean isValidNumberPlate(Editable editable) {
        return editable != null && editable.length() > 6;
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
                self.dismiss();
            }
        }
    }
}
