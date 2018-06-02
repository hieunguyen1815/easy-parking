package app.android.easygroup.easyparking.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import app.android.easygroup.easyparking.NavigationHost;
import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.fragments.MapFragment;

public class MainActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.main_container, new MapFragment())
                    .commit();
        }
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public <T extends NavigationHost> void navigateTo(Class<T> activity, Intent intent, boolean allowBack) {

    }
}
