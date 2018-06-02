package app.android.easygroup.easyparking.application;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

public class EasyParkingApplication extends Application {

    private static Context appContext;
    private static EasyParkingApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        this.setAppContext(getApplicationContext());
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    public static EasyParkingApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public void setAppContext(Context appContext) {
        this.appContext = appContext;
    }
}
