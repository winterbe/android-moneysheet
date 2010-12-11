package de.winterberg.android.money;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

/**
 * Tab activity provides access to all category-related activities.
 *
 * @author Benjamin Winterberg
 */
public class CategoryTabActivity extends TabActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);

        String category = getIntent().getStringExtra(MoneyActivity.KEY_CATEGORY);

        setTitle(category);

        Resources res = getResources();

        TabHost tabHost = getTabHost();
        TabHost.TabSpec spec;
        Intent intent;


        // tab 1
        intent = new Intent().setClass(this, AmountActivity.class);
        intent.putExtra(MoneyActivity.KEY_CATEGORY, category);

        spec = tabHost.newTabSpec("amount")
                .setIndicator("Amount", res.getDrawable(R.drawable.ic_tab_amount))
                .setContent(intent);
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