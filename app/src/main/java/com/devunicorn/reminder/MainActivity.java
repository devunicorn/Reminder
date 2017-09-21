package com.devunicorn.reminder;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.devunicorn.reminder.adapter.TabsFragmentAdapter;
import com.devunicorn.reminder.data.RemindData;
import com.devunicorn.reminder.dialog.AddingTaskDialogFragment;
import com.devunicorn.reminder.fragment.TodoFragment;


public class MainActivity extends AppCompatActivity
        implements AddingTaskDialogFragment.AddingTaskListener {

    private static final int LAYOUT = R.layout.activity_main;

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private FragmentManager fragmentManager;
    private TabsFragmentAdapter adapter;
    private TodoFragment todoFragment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        fragmentManager = getFragmentManager();

        initToolbar();
        initNavigationView();
        initTabs();
        initFab();
    }


    private void initToolbar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        toolBar.setTitle(R.string.app_name);
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });

        toolBar.inflateMenu(R.menu.menu);
    }

    private void initTabs() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        adapter = new TabsFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        todoFragment = (TodoFragment) adapter.getItem(0);
    }

    private void initNavigationView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.view_navigation_open, R.string.view_navigation_close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.actionNotificationItem:
                        showNotificationTab();
                }
                return true;
            }
        });
    }

    private void showNotificationTab() {
        viewPager.setCurrentItem(Constants.TAB_ONE);
    }

    private void initFab() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment addingTaskDialogFragment = new AddingTaskDialogFragment();
                addingTaskDialogFragment.show(fragmentManager, "AddingTAskDialogFragment");
            }
        });
    }

    @Override
    public void onTaskAdded(RemindData newTask) {

        todoFragment.addTask(newTask); // добавление только на вкладку HISTORY

        //Toast.makeText(this, "Task added", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTaskAddingCancel() {
        Toast.makeText(this, "Task adding cancel", Toast.LENGTH_LONG).show();
    }
}
