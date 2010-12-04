package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * @author Benjamin Winterberg
 */
public class StatisticsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the Statistics tab");
        setContentView(textview);
    }
}