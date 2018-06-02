package app.android.easygroup.easyparking.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.android.easygroup.easyparking.NavigationHost;
import app.android.easygroup.easyparking.R;

public class WelcomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        new LoadingTask().execute();

        return view;
    }

    private class LoadingTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try { Thread.sleep(1800); } catch (InterruptedException ignored) { }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ((NavigationHost) getActivity()).navigateTo(new LoginFragment(), false);
        }
    }
}