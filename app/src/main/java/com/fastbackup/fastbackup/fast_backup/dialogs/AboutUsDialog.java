package com.fastbackup.fastbackup.fast_backup.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragment;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragmentView;

public class AboutUsDialog extends Dialog {

    SettingsFragmentView settingsFragmentView;
    ImageView iv_close_bf_dialog;

    public AboutUsDialog(@NonNull Context context) {
        super(context);
    }

    public void initVariablesOnCreate(SettingsFragment fragment){
        this.settingsFragmentView = fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about_us_dialog);
        initUIViews();
        initActions();

    }

    public void initUIViews(){
        iv_close_bf_dialog = (ImageView) findViewById(R.id.iv_close_bf_dialog);
    }

    public void initActions(){
        iv_close_bf_dialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
