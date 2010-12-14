package de.winterberg.android.money;

import android.app.Application;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Grants access to application-global objects.
 *
 * @author Benjamin Winterberg
 */
public class MoneyApplication extends Application implements AmountDaoAware {

    private AmountDao amountDao;

    private SimpleDateFormat dateFormat;

    private DecimalFormat decimalFormat;


    @Override
    public void onCreate() {
        amountDao = new AmountDao(this);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy - HH:mm");
        decimalFormat = new DecimalFormat("####0.00");
    }

    public AmountDao getAmountDao() {
        return amountDao;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }

    public DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }
}