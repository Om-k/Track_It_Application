package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

public class HomePage extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        t1 = findViewById(R.id.Val);
        t1.setText(t1.getText().toString() + " " + value);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewpager1);

        tabLayout.setupWithViewPager(viewPager);

        VPadapter vpAdapter = new VPadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new fragment1(), "Projects");
        vpAdapter.addFragment(new fragment2(), "Tasks");


        viewPager.setAdapter(vpAdapter);

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
                TextView tabTextView = (TextView) tab.getCustomView();
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
                TextView tabTextView = (TextView) tab.getCustomView();
                tabTextView.setTextColor(getResources().getColor(R.color.white));
                tabTextView.setTypeface(null, Typeface.BOLD);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // No action needed
            }
        });
    }
}
