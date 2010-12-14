package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
        showSumToday();
        showSumYesterday();
        showSumWeek();
        showSumMonth();
        showSumYear();
        showAverage();
    }

    private void showAverage() {
        BigDecimal sum = getAmountDao().findAverage(category);
        TextView view = (TextView) findViewById(R.id.stats_average);
        view.setText(getDecimalFormat().format(sum) + " €");
    }

    private void showSumYear() {
        BigDecimal sum = getAmountDao().findSumYear(category);
        TextView view = (TextView) findViewById(R.id.stats_sum_year);
        view.setText(getDecimalFormat().format(sum) + " €");
    }

    private void showSumMonth() {
        BigDecimal sum = getAmountDao().findSumMonth(category);
        TextView view = (TextView) findViewById(R.id.stats_sum_month);
        view.setText(getDecimalFormat().format(sum) + " €");
    }

    private void showSumWeek() {
        BigDecimal sum = getAmountDao().findSumWeek(category);
        TextView view = (TextView) findViewById(R.id.stats_sum_week);
        view.setText(getDecimalFormat().format(sum) + " €");
    }

    private void showSumToday() {
        BigDecimal sum = getAmountDao().findSumToday(category);
        TextView view = (TextView) findViewById(R.id.stats_sum_today);
        view.setText(getDecimalFormat().format(sum) + " €");
    }

    private void showSumYesterday() {
        BigDecimal sum = getAmountDao().findSumYesterday(category);
        TextView view = (TextView) findViewById(R.id.stats_sum_yesterday);
        view.setText(getDecimalFormat().format(sum) + " €");
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
        view.setText(getDecimalFormat().format(totalAmount) + " €");
    }

    public AmountDao getAmountDao() {
        return application.getAmountDao();
    }

    private DecimalFormat getDecimalFormat() {
        return application.getDecimalFormat();
    }
}