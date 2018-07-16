package com.fastbackup.fastbackup.fast_backup.activities.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.activities.Main.MainActivity;
import com.fastbackup.fastbackup.fast_backup.helpers.Helper;
import com.fastbackup.fastbackup.fast_backup.helpers.UserSessionManager;

public class SplashScreenActivity extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 1500;
    UserSessionManager session;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper();
        helper.changeStatusColorBar(getWindow(), this, R.color.colorWhite);
        setContentView(R.layout.activity_splash_screen);
        session = new UserSessionManager(this.getBaseContext());
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(session.getWelcomeSession()){
                    Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                } else {
                    Intent welcomeIntent = new Intent().setClass(SplashScreenActivity.this, WelcomeActivity.class);
                    startActivity(welcomeIntent);
                    finish();
                }

                Intent mainIntent = new Intent().setClass(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
