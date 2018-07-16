package com.fastbackup.fastbackup.fast_backup.helpers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.fastbackup.fastbackup.fast_backup.activities.Login.LoginActivity;
import java.util.HashMap;

public class UserSessionManager {
    SharedPreferences pref;
    Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String PREFER_NAME   = "fastbackupprefer";
    public static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_EMAIL     = "email";
    public static final String KEY_PASSWORD  = "password";
    public static final String KEY_TOKEN     = "token";
    public static final String KEY_APPS      = "apps";
    public static final String KEY_WELCOME   = "welcome";

    public UserSessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createUserLoginSession(String uEmail, String uPassword, String uToken){
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_EMAIL, uEmail);
        editor.putString(KEY_PASSWORD,  uPassword);
        editor.putString(KEY_TOKEN,  uToken);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.isUserLoggedIn()){
            Intent i = new Intent(_context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);

            return true;
        }
        return false;
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    public String getSessionToken(){
        return pref.getString(KEY_TOKEN, null);
    }

    public void createAppSession(String uApps){
        editor.putString(KEY_APPS, uApps);
        editor.commit();
    }

    public void createWelcomeSession(){
        editor.putBoolean(KEY_WELCOME, true);
        editor.commit();
    }

    public boolean getWelcomeSession(){
        return pref.getBoolean(KEY_WELCOME, false);
    }

    public void deleteWelcomeSession(){
        pref.edit().remove(KEY_WELCOME).commit();
    }

    public String getSessionApps(){
        return pref.getString(KEY_APPS, null);
    }

    public void deleteSessionApp(){
        pref.edit().remove(KEY_APPS).commit();
    }
}
