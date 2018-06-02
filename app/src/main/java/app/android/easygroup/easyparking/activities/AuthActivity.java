package app.android.easygroup.easyparking.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import app.android.easygroup.easyparking.NavigationHost;
import app.android.easygroup.easyparking.R;
import app.android.easygroup.easyparking.domain.user.User;
import app.android.easygroup.easyparking.fragments.WelcomeFragment;

public class AuthActivity extends AppCompatActivity implements NavigationHost {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.auth_container, new WelcomeFragment())
                    .commit();
        }
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.auth_container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }

    @Override
    public <T extends NavigationHost> void navigateTo(Class<T> activity, Intent intent, boolean allowBack) {
        if (intent == null) {
            intent = new Intent(AuthActivity.this, activity);
        }
        startActivity(intent);

        if (!allowBack) {
            finish();
        }
    }
}
