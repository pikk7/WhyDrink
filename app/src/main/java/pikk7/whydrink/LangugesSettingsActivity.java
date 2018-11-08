package pikk7.whydrink;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

@SuppressWarnings("deprecation")
public class LangugesSettingsActivity extends PreferenceActivity implements  SharedPreferences.OnSharedPreferenceChangeListener{
    public static String LOKALKEY="";
    public static String data="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        LOKALKEY=getString(R.string.languages_settings);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            Preference connectionPref = findPreference(key);

            // Set summary to be the user-description for the selected value
            connectionPref.setSummary(sharedPreferences.getString(key, ""));
            String code=connectionPref.toString();





            SharedPreferences sharedPref = MainActivity.getInstance().getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.languages_settings),code );
            editor.commit();
            this.recreate();

    }

}
