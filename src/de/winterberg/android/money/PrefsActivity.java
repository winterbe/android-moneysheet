package de.winterberg.android.money;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Preferences activity.
 *
 * @author Benjamin Winterberg
 */
public class PrefsActivity extends PreferenceActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

}