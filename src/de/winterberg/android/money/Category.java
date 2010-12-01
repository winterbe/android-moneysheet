package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Activity for editing details on a category.
 *
 * @author Benjamin Winterberg
 */
public class Category extends Activity {
    private static final String TAG = "Category";

    private static final String ACTION_PLUS = "+";
    private static final String ACTION_MINUS = "-";

    private String category;

    private BigDecimal amount;

    private StringBuilder input = new StringBuilder();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);

        category = getIntent().getStringExtra(Money.KEY_CATEGORY);
        amount = new BigDecimal(0.0);

        TextView amountView = (TextView) findViewById(R.id.amount_value);
        amountView.setText(amount.toString());
    }

    public void onNumButtonClick(View view) {
        Button button = (Button) view;
        CharSequence num = button.getText();
        Log.d(TAG, "onNumButtonClick: " + num);
        input.append(num);
    }

    public void onActionButtonClick(View view) {
        Button button = (Button) view;
        CharSequence action = button.getText();
        Log.d(TAG, "onActionButtonClick: " + action);

        if (!isInputValid()) {
            Toast.makeText(getApplicationContext(), "Input is not valid: " + input.toString(), Toast.LENGTH_LONG).show();
            clearInput();
            return;
        }

        if (ACTION_PLUS.equals(action)) {
            plus();
        } else if (ACTION_MINUS.equals(action)) {
            minus();
        }
    }

    private void plus() {
        BigDecimal inputNumber = new BigDecimal(input.toString());
        amount = amount.add(inputNumber);
        clearInput();
        refreshView();
    }

    private void minus() {
        BigDecimal inputNumber = new BigDecimal(input.toString());
        amount = amount.subtract(inputNumber);
        clearInput();
        refreshView();
    }

    private void refreshView() {
        TextView amountValue = (TextView) findViewById(R.id.amount_value);
        amountValue.setText(amount.toString());
    }

    private void clearInput() {
        input.delete(0, input.length());
    }

    private boolean isInputValid() {
        try {
            new BigDecimal(input.toString());
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

}