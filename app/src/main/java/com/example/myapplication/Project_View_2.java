package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class Project_View_2 extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private VPadapter vpAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_view2);

        tabLayout = findViewById(R.id.tbL);
        viewPager = findViewById(R.id.viewpager1);

        vpAdapter = new VPadapter(getSupportFragmentManager());
        vpAdapter.addFragment(new ProjViewFrag(), "Tasks");
        vpAdapter.addFragment(new ProjDescFrag(), "Description");
        vpAdapter.addFragment(new LeaderBoardFrag(), "Leaderboard");

        viewPager.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPager);

        // Set custom view for each tab
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View tabView = LayoutInflater.from(this).inflate(R.layout.custom_tab_item, null);
                TextView tabTextView = tabView.findViewById(R.id.tab_text);
                tabTextView.setText(vpAdapter.getPageTitle(i));
                tab.setCustomView(tabView);
            }
        }

        View customView = tabLayout.getTabAt(0).getCustomView();
        if (customView != null) {
            customView.setBackgroundResource(R.drawable.selected_tab_background);
        }

        // Highlight the selected tab with a border
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    customView.setBackgroundResource(R.drawable.selected_tab_background);
                }

                // Apply the selected tab style
                TextView tabTextView = customView.findViewById(R.id.tab_text);
                tabTextView.setTextColor(getResources().getColor(R.color.white));
                tabTextView.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                if (customView != null) {
                    customView.setBackground(null);
                }

                // Apply the unselected tab style
                TextView tabTextView = customView.findViewById(R.id.tab_text);
                tabTextView.setTextColor(getResources().getColor(R.color.white));
                tabTextView.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No action needed
            }
        });
    }

    // Custom ViewPager Adapter
    class VPadapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public VPadapter(FragmentManager manager) {
            super(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
