package app.android.easygroup.easyparking.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UIUtils {

    public static void hideVirtualKeyBoard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            if (view != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (NullPointerException ignored) { }
    }

}
