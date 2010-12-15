package de.winterberg.android.money;

import android.app.Application;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Grants access to application-global objects.
 *
 * @author Benjamin Winterberg
 */
public class MoneyApplication extends Application implements AmountDaoAware {

    private AmountDao amountDao;

    private DateFormat dateFormat;

    private DecimalFormat decimalFormat;


    @Override
    public void onCreate() {
        amountDao = new AmountDao(this);

        String datePattern = getResources().getString(R.string.date_pattern);
        String numberPattern = getResources().getString(R.string.number_pattern);

        dateFormat = new SimpleDateFormat(datePattern);
        decimalFormat = new DecimalFormat(numberPattern);
    }

    public AmountDao getAmountDao() {
        return amountDao;
    }

    public java.text.DateFormat getDateFormat() {
        return dateFormat;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }
}