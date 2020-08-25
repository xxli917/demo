package com.lixiaoxue.demolxx.md;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lixiaoxue.demolxx.R;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;
import androidx.preference.SwitchPreferenceCompat;

public class MySettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
      //  setPreferencesFromResource(R.xml.preferences, rootKey);
        Context context = getPreferenceManager().getContext();
        PreferenceScreen screen = getPreferenceManager().createPreferenceScreen(context);

        SwitchPreferenceCompat notificationPreference = new SwitchPreferenceCompat(context);
        notificationPreference.setKey("notifications");
        notificationPreference.setTitle("Enable message notifications");

        Preference feedbackPreference = new Preference(context);
        feedbackPreference.setKey("feedback");
        feedbackPreference.setTitle("Send feedback");
        feedbackPreference.setSummary("Report technical issues or suggest new features");

        screen.addPreference(notificationPreference);
        screen.addPreference(feedbackPreference);
        feedbackPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent intent = new Intent(getActivity(),MotionActivity.class);
                startActivity(intent);
                return true;
            }
        });
       // screen.setIntent(new Intent(getActivity(),MotionActivity.class));

        setPreferenceScreen(screen);

    }
}
