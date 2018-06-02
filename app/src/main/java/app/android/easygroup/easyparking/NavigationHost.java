package app.android.easygroup.easyparking;

import android.content.Intent;
import android.support.v4.app.Fragment;

public interface NavigationHost {

    void navigateTo(Fragment fragment, boolean addToBackStack);

    <T extends NavigationHost> void navigateTo(Class<T> activity, Intent intent, boolean allowBack);

}
