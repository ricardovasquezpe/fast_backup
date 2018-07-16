package com.fastbackup.fastbackup.fast_backup.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragment;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragmentView;

import org.w3c.dom.Text;

public class SupportDialog extends Dialog {

    SettingsFragmentView settingsFragmentView;
    ImageView iv_close_bf_dialog;
    TextView tv_2_about_us_dialog;

    public SupportDialog(@NonNull Context context) {
        super(context);
    }

    public void initVariablesOnCreate(SettingsFragment fragment){
        this.settingsFragmentView = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.support_dialog);
        initUIViews();
        initActions();

    }

    public void initUIViews(){
        iv_close_bf_dialog   = (ImageView) findViewById(R.id.iv_close_bf_dialog);
        tv_2_about_us_dialog = (TextView) findViewById(R.id.tv_2_about_us_dialog);
    }

    public void initActions(){
        iv_close_bf_dialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_2_about_us_dialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                settingsFragmentView.onFaskBackupPlayStore();
            }
        });
    }
}
