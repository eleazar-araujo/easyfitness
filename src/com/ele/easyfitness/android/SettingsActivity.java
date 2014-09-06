package com.ele.easyfitness.android;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity implements
		OnPreferenceChangeListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_unit_system_key)));
		bindPreferenceSummaryToValue(findPreference(getString(R.string.pref_user_name_key)));
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		String stringValue = newValue.toString();
		if (preference instanceof ListPreference) {
			ListPreference listPreference = (ListPreference) preference;
			int prefIndex = listPreference.findIndexOfValue(stringValue);
			if (prefIndex >= 0) {
				preference.setSummary(listPreference.getEntries()[prefIndex]);
			}
		} else {
			preference.setSummary(stringValue);
		}
		return false;
	}

	private void bindPreferenceSummaryToValue(Preference preference) {
		preference.setOnPreferenceChangeListener(this);
		onPreferenceChange(preference, PreferenceManager
				.getDefaultSharedPreferences(preference.getContext())
				.getString(preference.getKey(), ""));
	}

}
