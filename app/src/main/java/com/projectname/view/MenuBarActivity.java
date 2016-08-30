package com.projectname.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.projectname.R;
import com.projectname.TemplateAplication;
import com.projectname.adapter.MenuAdapter;
import com.projectname.fragment.HomeFragment;
import com.projectname.model.SlidingMenuModel;

import java.util.ArrayList;

public class MenuBarActivity extends BaseActivity {

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ListView lvMenuList;
    private SlidingMenuModel model;
    private TextView tvName;

    private ImageView ivProfileRound;
    private TextView mTitle;


    private ImageView ivNext;
    private FragmentManager fragmentManager;
    private MenuAdapter mMenuAdapter;
    private int menuLastPotion = 1;


    private TemplateAplication mTemplateAplication;
    private ImageLoader imageLoader;
    private DisplayImageOptions displayImageOptions;

    private int position = 1;
    private boolean isListClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menubar);

        initComponents();



    }

    @Override
    public void initComponents() {



        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.activity_menubar_navigationview);
        lvMenuList = (ListView) navigationView.findViewById(R.id.activity_menubar_lvMenuList);

        mTemplateAplication = (TemplateAplication) getApplication().getApplicationContext();
        imageLoader = mTemplateAplication.getImageLoader();
        displayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).showImageOnLoading(R.drawable.profile).showImageForEmptyUri(R.drawable.profile)
                .showImageOnFail(R.drawable.profile).cacheInMemory(true).bitmapConfig(Bitmap.Config.RGB_565).build();


        setupToolbar();
        addComponents();
        setUpMenu();


    }

    @Override
    public void addComponents() {


        //Initializing DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
                if (isListClick) {
                    isListClick = false;
                    listClickOpenfragment(position);
                }


            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blan
                super.onDrawerOpened(drawerView);
            }


        };


        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        drawerLayout.openDrawer(GravityCompat.START);


    }




    @Override
    public void onPause() {
        super.onPause();


    }

    @Override
    public void onResume() {
        super.onResume();


    }


    private void setupToolbar() {
        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.activity_menubar_toolbar);
        setSupportActionBar(toolbar);
        mTitle = (TextView) toolbar.findViewById(R.id.actionbar_tvTitle);
        ivNext = (ImageView) toolbar.findViewById(R.id.activity_base_actionbar_ivNext);
        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.menu_btn));
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }


    }


    private void openFragment(final Fragment mFragment) {


        final FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.activity_menubar_containers, mFragment, mFragment.getClass().getSimpleName());
        transaction.commit();
        drawerLayout.closeDrawer(GravityCompat.START);


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


        int id = item.getItemId();

        if (id == android.R.id.home) {


            fragmentManager = getFragmentManager();

            if (fragmentManager.getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
                return true;

            } else {

                return true;

            }


        }
        return super.onOptionsItemSelected(item);
    }


    public Toolbar getToolbar() {
        return toolbar;
    }


    private void setUpMenu() {
        ArrayList<SlidingMenuModel> slidingMenu = new ArrayList<SlidingMenuModel>();
        model = new SlidingMenuModel();
        model.setTitle("Menu1");
        //model.setIcon(R.drawable.menu_contacts_selector);
        slidingMenu.add(model);

        model = new SlidingMenuModel();
        model.setTitle("Menu2");
        //model.setIcon(R.drawable.menu_poi_selector);
        slidingMenu.add(model);

        model = new SlidingMenuModel();
        model.setTitle("Menu3");
        //model.setIcon(R.drawable.menu_folders_selector);
        slidingMenu.add(model);

        model = new SlidingMenuModel();
        model.setTitle("Menu4");
        //model.setIcon(R.drawable.menu_groups_selector);
        slidingMenu.add(model);

        model = new SlidingMenuModel();
        model.setTitle("Menu5");
        //model.setIcon(R.drawable.menu_logbook_selector);
        slidingMenu.add(model);


        model = new SlidingMenuModel();
        model.setTitle("Menu6");
        //model.setIcon(R.drawable.menu_usertips_selector);
        slidingMenu.add(model);

        model = new SlidingMenuModel();
        model.setTitle("Menu7");
        //model.setIcon(R.drawable.menu_tutorial_selector);
        slidingMenu.add(model);


        View header = (View) getLayoutInflater().inflate(R.layout.menu_header, null);
        lvMenuList.addHeaderView(header);

        ivProfileRound = (ImageView) header.findViewById(R.id.menu_header_ivProfileRound);
        tvName = (TextView) header.findViewById(R.id.menu_header_tvName);


        final String username = TemplateAplication.getmInstance().getSharedPreferences().getString(getString((R.string.prefe_userName)), "");
        final String profilePic = TemplateAplication.getmInstance().getSharedPreferences().getString(getString((R.string.prefe_userPic)), "");

        imageLoader.displayImage(profilePic, ivProfileRound, displayImageOptions);

        tvName.setText(username);
        tvName.setSelected(true);


        mMenuAdapter = new MenuAdapter(MenuBarActivity.this, slidingMenu);
        lvMenuList.setAdapter(mMenuAdapter);


        lvMenuList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int mPosition, long id) {


                mMenuAdapter.notifyDataSetChanged();

                if (isNavDrawerOpen()) {
                    isListClick = true;
                    position = mPosition;
                    closeNavDrawer();
                }

                view.setSelected(true);
                mMenuAdapter.notifyDataSetChanged();

            }
        });
    }

    private void listClickOpenfragment(int position) {


        switch (position) {

            case 1:

                if (menuLastPotion == 1) {
                    closeNavDrawer();
                } else {
                    menuLastPotion = 1;
                    HomeFragment mContactsFragment = new HomeFragment();
                    openFragment(mContactsFragment);
                }

                break;
            case 2:
                if (menuLastPotion == 2) {
                    closeNavDrawer();
                } else {
                    menuLastPotion = 2;
                    HomeFragment mPoiFragment = new HomeFragment();
                    openFragment(mPoiFragment);
                }

                break;

            case 3:
                if (menuLastPotion == 3) {
                    closeNavDrawer();
                } else {
                    menuLastPotion = 3;
                    HomeFragment mFoldersFragment = new HomeFragment();
                    openFragment(mFoldersFragment);


                }

                break;
            case 4:
                if (menuLastPotion == 4) {
                    closeNavDrawer();
                } else {
                    menuLastPotion = 4;
                    HomeFragment mGroupsFragment = new HomeFragment();
                    openFragment(mGroupsFragment);
                }
                break;
            case 5:
                if (menuLastPotion == 5) {
                    closeNavDrawer();
                } else {
                    menuLastPotion = 5;
                    HomeFragment mLogBookFragment = new HomeFragment();
                    openFragment(mLogBookFragment);
                }
                break;
            case 6:

                if (menuLastPotion == 6) {
                    closeNavDrawer();
                } else {
                    menuLastPotion = 6;
                    HomeFragment mUserTipsFragment = new HomeFragment();
                    openFragment(mUserTipsFragment);
                }
                break;
            case 7:

                if (menuLastPotion == 7) {
                    closeNavDrawer();
                } else {
                    menuLastPotion = 7;
                    HomeFragment mSettingsFragment = new HomeFragment();
                    openFragment(mSettingsFragment);
                }
                break;


        }

    }

    protected boolean isNavDrawerOpen() {

        return drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    protected void closeNavDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }


    public ImageView getIvNext() {
        return ivNext;
    }

    public void setIvNext(ImageView ivNext) {
        this.ivNext = ivNext;
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


}
