package de.winterberg.android.money;

import android.app.Activity;
import android.os.Bundle;

/**
 * Activity for editing details on a category.
 *
 * @author Benjamin Winterberg
 */
public class Category extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
    }

}