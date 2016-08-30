package com.projectname.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.projectname.R;


public class HomeFragment extends Fragment {

    private ListView lvList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        initializeComponent(rootView);
        return rootView;
    }

    private void initializeComponent(View view) {
        lvList = (ListView) view.findViewById(R.id.fragment_home_lvList);


    }


    /**
     * Called when coming back from next screen
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {

        }
    }


}
