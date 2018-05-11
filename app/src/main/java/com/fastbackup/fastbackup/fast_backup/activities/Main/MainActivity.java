package com.fastbackup.fastbackup.fast_backup.activities.Main;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fastbackup.fastbackup.fast_backup.R;

public class MainActivity extends FragmentActivity {

    Fragment apps_fragment;
    Fragment settings_fragment;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUIViews();
        initVariables();
        initActions();
    }

    public void initUIViews(){
        FragmentManager fm  = getFragmentManager();
        apps_fragment       = fm.findFragmentById(R.id.fm_apps_act_main);
        settings_fragment   = fm.findFragmentById(R.id.fm_settings_act_main);
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
}