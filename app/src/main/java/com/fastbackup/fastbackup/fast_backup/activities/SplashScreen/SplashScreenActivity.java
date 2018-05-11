package com.fastbackup.fastbackup.fast_backup.activities.SplashScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.activities.Login.LoginActivity;
import com.fastbackup.fastbackup.fast_backup.activities.Main.MainActivity;
import com.fastbackup.fastbackup.fast_backup.helpers.Helper;
import com.fastbackup.fastbackup.fast_backup.helpers.UserSessionManager;

public class SplashScreenActivity extends Activity {

    private static final long SPLASH_SCREEN_DELAY = 3000;
    UserSessionManager session;
    Helper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper = new Helper();
        helper.changeStatusColorBar(getWindow(), this, R.color.colorWhite);
        setContentView(R.layout.activity_splash_screen);
        session = new UserSessionManager(this.getBaseContext());
        //session.logoutUser();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(!session.isUserLoggedIn()){
                    Intent loginIntent = new Intent().setClass(
                            SplashScreenActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }else {
                    Intent mainIntent = new Intent().setClass(
                            SplashScreenActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }
}
