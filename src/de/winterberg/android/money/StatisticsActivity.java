package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity for showing various statistics for a category.
 *
 * @author Benjamin Winterberg
 */
public class StatisticsActivity extends Activity implements AmountDaoAware {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
    }

    public AmountDao getAmountDao() {
        MoneyApplication application = (MoneyApplication) getApplication();
        return application.getAmountDao();
    }
}