package com.vikshy.v_beta.firebase;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.vikshy.v_beta.BuildConfig;

/**
 * Created by laxman.muttineni on 26/01/18.
 */

public class RemoteConfig {

    private static FirebaseRemoteConfig firebaseRemoteConfig;

    public static FirebaseRemoteConfig getConfigInstance(Activity activity) {
        if (firebaseRemoteConfig == null) {
            synchronized (RemoteConfig.class) {
                if (firebaseRemoteConfig == null) {
                    initConfig(activity);
                }
            }
        }
        return firebaseRemoteConfig;
    }

    private static void initConfig(final Activity activity) {
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();

        //todo : debug
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setDeveloperModeEnabled(BuildConfig.DEBUG)
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);

        firebaseRemoteConfig.setDefaults(ConfigUtils.FIRE_BASE_DEFAULTS);
        // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
        // will use fetch data from the Remote Config service, rather than cached parameter values,
        // if cached parameter values are more than cacheExpiration seconds old.
        // See Best Practices in the README for more information.
        firebaseRemoteConfig.fetch(200)
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Fetch Succeeded", Toast.LENGTH_SHORT).show();
                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            firebaseRemoteConfig.activateFetched();
                        } else {
                            Toast.makeText(activity, "Fetch Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
