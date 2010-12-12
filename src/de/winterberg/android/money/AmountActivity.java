package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Activity for editing details on a category.
 *
 * @author Benjamin Winterberg
 */
public class AmountActivity extends Activity implements AmountDaoAware {
    private static final String TAG = "AmountActivity";

    protected static final String ACTION_PLUS = "+";
    private static final String ACTION_MINUS = "-";
    private static final String ACTION_CLEAR = "C";

    private TextView inputView;

    private String category;
    private BigDecimal amount;
    private StringBuilder input;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amount);
        category = getIntent().getStringExtra(MoneyActivity.KEY_CATEGORY);
        setTitle(category);
        loadLatestAmount();
        input = new StringBuilder();
        inputView = (TextView) findViewById(R.id.current_input_value);
        refreshView();
    }

    private void loadLatestAmount() {
        amount = getAmountDao().loadAmount(category);
    }

    public void onNumButtonClick(View view) {
        Button button = (Button) view;
        button.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        CharSequence num = button.getText();
        Log.d(TAG, "onNumButtonClick: " + num);
        changeInputValue(num);
    }

    private void changeInputValue(CharSequence num) {
        if (!canAppend(num))
            return;
        input.append(num);
        inputView.setText(input.toString());
    }

    private boolean canAppend(CharSequence num) {
        String testValue = input.toString() + num;
        return isInputValid(testValue);
    }

    public void onActionButtonClick(View view) {
        Button button = (Button) view;
        button.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        String action = button.getText().toString();
        Log.d(TAG, "onActionButtonClick: " + action);

        if (input.length() == 0)
            return;

        if (!isInputValid()) {
            Toast.makeText(getApplicationContext(), "Input is not valid: " + input.toString(), Toast.LENGTH_LONG).show();
            clearInput();
            return;
        }

        doAction(action);
    }

    private void doAction(String action) {
        if (ACTION_PLUS.equals(action)) {
            plus();
        } else if (ACTION_MINUS.equals(action)) {
            minus();
        } else if (ACTION_CLEAR.equals(action)) {
            clearDigit();
        }
    }

    private void doSave(String action) {
        try {
            String value = input.toString();
            if (ACTION_MINUS.equals(action))
                value = "-" + value;

            getAmountDao().save(category, value, action, amount);
            Toast.makeText(this, "Amount saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "doSave() error", e);
            Toast.makeText(this, "Saving amount failed", Toast.LENGTH_LONG).show();
        }
    }

    private void clearDigit() {
        int length = input.length();
        if (length > 0) {
            input.setLength(length - 1);
            inputView.setText(input.toString());
        }
    }

    private void plus() {
        BigDecimal inputNumber = new BigDecimal(input.toString());
        amount = amount.add(inputNumber);
        doSave(ACTION_PLUS);
        clearInput();
        refreshView();
    }

    private void minus() {
        BigDecimal inputNumber = new BigDecimal(input.toString());
        amount = amount.subtract(inputNumber);
        doSave(ACTION_MINUS);
        clearInput();
        refreshView();
    }

    private void refreshView() {
        TextView amountValue = (TextView) findViewById(R.id.amount_value);
        amountValue.setText(getDecimalFormat().format(amount));
    }

    private void clearInput() {
        input.delete(0, input.length());
        inputView.setText("");
    }

    private boolean isInputValid() {
        return isInputValid(input.toString());
    }

    private boolean isInputValid(String input) {
        try {
            new BigDecimal(input);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public AmountDao getAmountDao() {
        MoneyApplication application = (MoneyApplication) getApplication();
        return application.getAmountDao();
    }

    private DecimalFormat getDecimalFormat() {
        return getMoneyApplication().getDecimalFormat();
    }

    private MoneyApplication getMoneyApplication() {
        return (MoneyApplication) getApplication();
    }
}