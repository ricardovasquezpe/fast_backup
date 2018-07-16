package com.fastbackup.fastbackup.fast_backup.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.adapters.FileBackupListAdapter;
import com.fastbackup.fastbackup.fast_backup.data.models.BackupFiles;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragment;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragmentView;
import java.util.ArrayList;
import java.util.List;

public class FilesBackupDialog extends Dialog {

    RecyclerView rv_files_backup;
    FileBackupListAdapter da_files_backup;
    List<BackupFiles> backupFilesList = new ArrayList<>();
    SettingsFragmentView settingsFragmentView;
    SettingsFragment settingsFragment;
    ImageView iv_close_bf_dialog;

    public FilesBackupDialog(@NonNull Context context) {
        super(context);
    }

    public void initVariablesOnCreate(List<BackupFiles> data, SettingsFragment fragment){
        this.backupFilesList      = data;
        this.settingsFragmentView = fragment;
        this.settingsFragment     = fragment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.backup_file_dialog);
        initUIViews();
        initActions();

        try{
            rv_files_backup.setLayoutManager(new LinearLayoutManager(getContext()));
            da_files_backup = new FileBackupListAdapter(backupFilesList, settingsFragment);
            rv_files_backup.setAdapter(da_files_backup);
        }catch(Exception e){}

    }

    public void initUIViews(){
        rv_files_backup    = (RecyclerView) findViewById(R.id.rv_files_backup_bf_dialog);
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
