package edu.usc.cs404.catchup;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    int selectedTab = 1;

    static private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataModel.getInstance().setContext(MainActivity.this);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.map));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.list));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.settings));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setSelectedTabIndicatorColor(R.color.colorWhite);


        //final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        //final PagerAdapter adapter = new PageAdapter
        //        (getSupportFragmentManager(), tabLayout.getTabCount());
        //viewPager.setAdapter(adapter);
        //viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
                selectedTab = tab.getPosition();
                FragmentManager fm = getSupportFragmentManager();
                Fragment f;
                FragmentTransaction ft;
                switch (selectedTab) {
                    case 0:
                        f = new MapFragment();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container, f, "MY_FRAGMENT");
                        ft.commit();
                        break;
                    case 1:
                        f = new ListFragment();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container, f, "MY_FRAGMENT");
                        ft.commit();
                        break;
                    case 2:
                        f = new ListFragment();
                        ft = fm.beginTransaction();
                        ft.replace(R.id.fragment_container, f, "MY_FRAGMENT");
                        ft.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
            }
        });
        tabLayout.getTabAt(1).select();
    }


    static public void update() {
        switch (tabLayout.getSelectedTabPosition()) {
            case 0:
                MapFragment.update();
                break;
            case 1:
                ListFragment.update();
                break;
        }
    }
}
