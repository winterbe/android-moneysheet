package de.winterberg.android.money;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * @author Benjamin Winterberg
 */
public class CategoryTabActivity extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);

        String category = getIntent().getStringExtra(MoneyActivity.KEY_CATEGORY);

        setTitle(category);

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;


        // tab 1
        intent = new Intent().setClass(this, AmountActivity.class);
        intent.putExtra(MoneyActivity.KEY_CATEGORY, category);

        spec = tabHost.newTabSpec("amount").setIndicator("Amount").setContent(intent);
        tabHost.addTab(spec);


        // tab 2
        intent = new Intent().setClass(this, HistoryActivity.class);
        intent.putExtra(MoneyActivity.KEY_CATEGORY, category);

        spec = tabHost.newTabSpec("history").setIndicator("History").setContent(intent);
        tabHost.addTab(spec);


        // tab 3
        intent = new Intent().setClass(this, StatisticsActivity.class);
        intent.putExtra(MoneyActivity.KEY_CATEGORY, category);

        spec = tabHost.newTabSpec("statistics").setIndicator("Statistics").setContent(intent);
        tabHost.addTab(spec);


        tabHost.setCurrentTab(0);
    }

}