package app.android.easygroup.easyparking.fragments;

import android.support.v4.app.Fragment;

import java.util.Map;

public abstract class BaseFragment<T extends BaseFragment> extends Fragment {

     public abstract T newInstance(Map<String, Object> bundle);
}
