package de.winterberg.android.money;

/**
 * Grants access to AmountDao for SQLite operations.
 * 
 * @author Benjamin Winterberg
 */
public interface AmountDaoAware {
    AmountDao getAmountDao();
}