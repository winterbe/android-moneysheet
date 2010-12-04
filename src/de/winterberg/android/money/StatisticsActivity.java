package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Activity for showing various statistics for a category.
 *
 * @author Benjamin Winterberg
 */
public class StatisticsActivity extends Activity implements AmountDaoAware {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Statistics tab");
        setContentView(textview);
    }

    public AmountDao getAmountDao() {
        MoneyApplication application = (MoneyApplication) getApplication();
        return application.getAmountDao();
    }
}