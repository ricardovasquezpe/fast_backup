package com.fastbackup.fastbackup.fast_backup.activities.Main;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.fragments.NewAppsFragment;
import com.fastbackup.fastbackup.fast_backup.fragments.AppsFragment;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity implements MainActivityView{

    LinearLayout ll_settings_menu;
    LinearLayout ll_apps_menu;
    LinearLayout ll_new_apps_menu;

    RelativeLayout rl_cont_fm_apps;
    RelativeLayout rl_cont_fm_settings;
    RelativeLayout rl_cont_fm_new_apps;

    TextView tv_apps_menu;
    TextView tv_settings_menu;
    TextView tv_new_apps_menu;

    ImageView iv_apps_menu;
    ImageView iv_settings_menu;
    ImageView iv_new_apps_menu;

    AppsFragment appsFragment;
    NewAppsFragment newAppsFragment;
    SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        initUIViews();
        initVariables();
        initActions();

        verifyListDoneFragment();
    }

    public void initUIViews(){
        ll_apps_menu        = (LinearLayout) findViewById(R.id.ll_apps_menu_act_main);
        ll_new_apps_menu    = (LinearLayout) findViewById(R.id.ll_new_apps_menu_act_main);
        ll_settings_menu    = (LinearLayout) findViewById(R.id.ll_settings_menu_act_main);
        rl_cont_fm_apps     = (RelativeLayout) findViewById(R.id.rl_cont_fm_apps_act_main);
        rl_cont_fm_new_apps = (RelativeLayout) findViewById(R.id.rl_cont_fm_new_apps_act_main);
        rl_cont_fm_settings = (RelativeLayout) findViewById(R.id.rl_cont_fm_settings_act_main);
        tv_apps_menu        = (TextView) findViewById(R.id.tv_apps_menu_act_main);
        tv_settings_menu    = (TextView) findViewById(R.id.tv_settings_menu_act_main);
        tv_new_apps_menu    = (TextView) findViewById(R.id.tv_new_apps_menu_act_main);
        iv_apps_menu        = (ImageView) findViewById(R.id.iv_apps_menu_act_main);
        iv_settings_menu    = (ImageView) findViewById(R.id.iv_settings_menu_act_main);
        iv_new_apps_menu    = (ImageView) findViewById(R.id.iv_new_apps_menu_act_main);
    }

    public void initVariables(){
        appsFragment    = (AppsFragment) getSupportFragmentManager().findFragmentById(R.id.fm_apps_act_main);
        newAppsFragment = (NewAppsFragment) getSupportFragmentManager().findFragmentById(R.id.fm_new_apps_act_main);
        newAppsFragment.newInstance(appsFragment);
        appsFragment.newInstance(newAppsFragment, this);
        settingsFragment = (SettingsFragment) getSupportFragmentManager().findFragmentById(R.id.fm_settings_act_main);
        settingsFragment.newInstance(this);
    }

    public void initActions(){
        ll_apps_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickMenuOption(v);
            }
        });
        ll_settings_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickMenuOption(v);
            }
        });
        ll_new_apps_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickMenuOption(v);
            }
        });
    }

    public void onClickMenuOption(View v){
        if(v.getId() == R.id.ll_apps_menu_act_main){
            rl_cont_fm_apps.setVisibility(View.VISIBLE);
            rl_cont_fm_settings.setVisibility(View.GONE);
            rl_cont_fm_new_apps.setVisibility(View.GONE);

            iv_apps_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPersianGreen), android.graphics.PorterDuff.Mode.MULTIPLY);
            iv_settings_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorSilver), android.graphics.PorterDuff.Mode.MULTIPLY);
            iv_new_apps_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorSilver), android.graphics.PorterDuff.Mode.MULTIPLY);

            tv_apps_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPersianGreen));
            tv_settings_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorSilver));
            tv_new_apps_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorSilver));
        }else if(v.getId() == R.id.ll_settings_menu_act_main){
            rl_cont_fm_apps.setVisibility(View.GONE);
            rl_cont_fm_settings.setVisibility(View.VISIBLE);
            rl_cont_fm_new_apps.setVisibility(View.GONE);

            iv_apps_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorSilver), android.graphics.PorterDuff.Mode.MULTIPLY);
            iv_settings_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPersianGreen), android.graphics.PorterDuff.Mode.MULTIPLY);
            iv_new_apps_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorSilver), android.graphics.PorterDuff.Mode.MULTIPLY);

            tv_apps_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorSilver));
            tv_settings_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPersianGreen));
            tv_new_apps_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorSilver));
        }else{
            rl_cont_fm_apps.setVisibility(View.GONE);
            rl_cont_fm_settings.setVisibility(View.GONE);
            rl_cont_fm_new_apps.setVisibility(View.VISIBLE);

            iv_settings_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorSilver), android.graphics.PorterDuff.Mode.MULTIPLY);
            iv_apps_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorSilver), android.graphics.PorterDuff.Mode.MULTIPLY);
            iv_new_apps_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPersianGreen), android.graphics.PorterDuff.Mode.MULTIPLY);

            tv_settings_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorSilver));
            tv_apps_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorSilver));
            tv_new_apps_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPersianGreen));
        }
    }

    public void verifyListDoneFragment(){
        if(appsFragment.savedAppsList.size() == 0){
            rl_cont_fm_apps.setVisibility(View.GONE);
            rl_cont_fm_settings.setVisibility(View.GONE);
            rl_cont_fm_new_apps.setVisibility(View.VISIBLE);

            iv_settings_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorSilver), android.graphics.PorterDuff.Mode.MULTIPLY);
            iv_apps_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorSilver), android.graphics.PorterDuff.Mode.MULTIPLY);
            iv_new_apps_menu.setColorFilter(ContextCompat.getColor(getApplicationContext(), R.color.colorPersianGreen), android.graphics.PorterDuff.Mode.MULTIPLY);

            tv_settings_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorSilver));
            tv_apps_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorSilver));
            tv_new_apps_menu.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.colorPersianGreen));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == appsFragment.FILE_PICKER){
                appsFragment.onFileSelected(data);
            }else{
                Toast.makeText(this, "Try again please", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == appsFragment.WRITE_EXST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                appsFragment.onStoragePermissionDone();
            } else {
                Toast.makeText(this, "Permission to store your backup needed", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == settingsFragment.WRITE_EXST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                settingsFragment.onStoragePermissionDone();
            } else {
                Toast.makeText(this, "Permission to check your backup files", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}