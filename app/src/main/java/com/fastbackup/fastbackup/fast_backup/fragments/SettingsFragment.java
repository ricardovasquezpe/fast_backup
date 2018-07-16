package com.fastbackup.fastbackup.fast_backup.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.data.models.BackupFiles;
import com.fastbackup.fastbackup.fast_backup.dialogs.FilesBackupDialog;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SettingsFragment extends Fragment implements SettingsFragmentView{

    List<BackupFiles> backupFilesList;
    LinearLayout ll_files_generated_fm_settings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initVariables(view);
        initActions();
        return view;
    }

    public void initVariables(View view){
        backupFilesList                = new ArrayList<>();
        ll_files_generated_fm_settings = view.findViewById(R.id.ll_files_generated_fm_settings);
    }

    public void initActions(){
        ll_files_generated_fm_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getListOfFilesBackup();
            }
        });
    }

    public void getListOfFilesBackup(){
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "FastBackupFiles");
        File[] listBackup = directory.listFiles();
        for (File backup : listBackup) {
            BackupFiles bFile = new BackupFiles();
            bFile.setName(backup.getName());
            bFile.setPath(backup.getAbsolutePath());
            Date last_modified = new Date(backup.lastModified());
            bFile.setCreated_at(last_modified);
            backupFilesList.add(bFile);
        }

        FilesBackupDialog filesBackupDialog = new FilesBackupDialog(getActivity());
        filesBackupDialog.initVariablesOnCreate(backupFilesList, this);
        filesBackupDialog.show();
    }

    public void shareFile(String filePath){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "FastBackup File");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filePath));
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onBackupFileShare(BackupFiles item) {
        shareFile(item.getPath());
    }
}
