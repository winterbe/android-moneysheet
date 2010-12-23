package de.winterberg.android.money;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

/**
 * Preferences activity.
 *
 * @author Benjamin Winterberg
 */
public class PrefsActivity extends PreferenceActivity {
    private static final String OPT_HAPTIC = "haptic";
    private static final boolean OPT_HAPTIC_DEFAULT = true;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

    public static boolean isHapticEnabled(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(OPT_HAPTIC, OPT_HAPTIC_DEFAULT);
    }

}