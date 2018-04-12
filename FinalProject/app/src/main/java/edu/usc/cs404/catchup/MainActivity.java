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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataModel.getInstance().setContext(MainActivity.this);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
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
                Fragment f = new ListFragment();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container, f, "MY_FRAGMENT");
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //viewPager.setCurrentItem(tab.getPosition());
            }
        });
        tabLayout.getTabAt(2).select();
    }


    static public void update() {
        ListFragment.update();
    }
}
