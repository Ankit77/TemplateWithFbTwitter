package com.projectname.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.projectname.R;
import com.projectname.fragment.HomeFragment;


/****************************************************************************
 * HomeActivity
 *
 * @CreatedDate:
 * @ModifiedBy: not yet
 * @ModifiedDate: not yet
 * @purpose:This Class is user for HomeActivity.
 ***************************************************************************/

public class HomeActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        HomeFragment mFragmentHome = new HomeFragment();
        fragmentTransaction.add(R.id.activity_home_maincontent, mFragmentHome);
        fragmentTransaction.commit();

    }

    @Override
    public void initComponents() {

    }

    @Override
    public void addComponents() {

    }


}
