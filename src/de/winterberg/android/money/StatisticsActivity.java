package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Activity for showing various statistics for a category.
 *
 * @author Benjamin Winterberg
 */
public class StatisticsActivity extends Activity implements AmountDaoAware {

    private MoneyApplication application;

    private String category;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats);
        application = (MoneyApplication) getApplication();
        category = getIntent().getStringExtra(MoneyActivity.KEY_CATEGORY);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    private void refreshData() {
        showTotalAmount();
        showFirstDate();
        showLastDate();
    }

    private void showFirstDate() {
        Date firstDate = getAmountDao().findFirstDate(category);
        TextView view = (TextView) findViewById(R.id.stats_from);
        view.setText(application.getDateFormat().format(firstDate));
    }

    private void showLastDate() {
        Date firstDate = getAmountDao().findLastDate(category);
        TextView view = (TextView) findViewById(R.id.stats_to);
        view.setText(application.getDateFormat().format(firstDate));
    }

    private void showTotalAmount() {
        BigDecimal totalAmount = getAmountDao().loadAmount(category);
        TextView view = (TextView) findViewById(R.id.stats_current_amount);
        view.setText(totalAmount.toString() + " €");
    }

    public AmountDao getAmountDao() {
        return application.getAmountDao();
    }
}