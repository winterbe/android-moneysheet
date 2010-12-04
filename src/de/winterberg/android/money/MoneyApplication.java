package de.winterberg.android.money;

import android.app.Application;

/**
 * Grants access to application-global objects.
 *
 * @author Benjamin Winterberg
 */
public class MoneyApplication extends Application implements AmountDaoAware {

    private AmountDao amountDao;


    @Override
    public void onCreate() {
        amountDao = new AmountDao(this);
    }

    public AmountDao getAmountDao() {
        return amountDao;
    }
}