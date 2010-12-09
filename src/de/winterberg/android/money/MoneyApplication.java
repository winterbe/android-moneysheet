package de.winterberg.android.money;

import android.app.Application;

import java.text.SimpleDateFormat;

/**
 * Grants access to application-global objects.
 *
 * @author Benjamin Winterberg
 */
public class MoneyApplication extends Application implements AmountDaoAware {

    private AmountDao amountDao;

    private SimpleDateFormat dateFormat;


    @Override
    public void onCreate() {
        amountDao = new AmountDao(this);
        dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    }

    public AmountDao getAmountDao() {
        return amountDao;
    }

    public SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
}