package com.fastbackup.fastbackup.fast_backup.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.activities.Main.MainActivity;
import com.fastbackup.fastbackup.fast_backup.activities.Main.MainActivityView;
import com.fastbackup.fastbackup.fast_backup.activities.SplashScreen.SplashScreenActivity;
import com.fastbackup.fastbackup.fast_backup.activities.SplashScreen.WelcomeActivity;
import com.fastbackup.fastbackup.fast_backup.data.models.BackupFiles;
import com.fastbackup.fastbackup.fast_backup.dialogs.AboutUsDialog;
import com.fastbackup.fastbackup.fast_backup.dialogs.FilesBackupDialog;
import com.fastbackup.fastbackup.fast_backup.dialogs.SupportDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SettingsFragment extends Fragment implements SettingsFragmentView{

    List<BackupFiles> backupFilesList;
    LinearLayout ll_files_generated_fm_settings;
    LinearLayout ll_about_us_fm_settings;
    LinearLayout ll_support_fm_settings;
    LinearLayout ll_tutorial_fm_settings;
    static MainActivityView mainActivityView;

    public static final Integer WRITE_EXST                      = 0x10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        initVariables(view);
        initActions();
        return view;
    }

    public static void newInstance(MainActivity activity) {
        mainActivityView = activity;
    }

    public void initVariables(View view){
        backupFilesList                = new ArrayList<>();
        ll_files_generated_fm_settings = view.findViewById(R.id.ll_files_generated_fm_settings);
        ll_about_us_fm_settings        = view.findViewById(R.id.ll_about_us_fm_settings);
        ll_support_fm_settings         = view.findViewById(R.id.ll_support_fm_settings);
        ll_tutorial_fm_settings        = view.findViewById(R.id.ll_tutorial_fm_settings);
    }

    public void initActions(){
        ll_files_generated_fm_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                backupFilesList.clear();
                checkPermissionsToListBackupFiles();
            }
        });

        ll_about_us_fm_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToAboutUsDialog();
            }
        });

        ll_support_fm_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToSupportDialog();
            }
        });

        ll_tutorial_fm_settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainActivityView.onLoadTutorial();
            }
        });
    }

    public void goToAboutUsDialog(){
        AboutUsDialog aboutUsDialog = new AboutUsDialog(getActivity());
        aboutUsDialog.initVariablesOnCreate(this);
        aboutUsDialog.show();
    }

    public void goToSupportDialog(){
        SupportDialog aboutUsDialog = new SupportDialog(getActivity());
        aboutUsDialog.initVariablesOnCreate(this);
        aboutUsDialog.show();
    }

    public void checkPermissionsToListBackupFiles(){
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getListOfFilesBackup();
        }else{
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXST);
        }
    }

    public void getListOfFilesBackup(){
        File directory = new File(Environment.getExternalStorageDirectory() + File.separator + "FastBackupFiles");
        File[] listBackup = directory.listFiles();
        if(listBackup.length != 0){
            for (File backup : listBackup) {
                BackupFiles bFile = new BackupFiles();
                bFile.setName(backup.getName());
                bFile.setPath(backup.getAbsolutePath());
                Date last_modified = new Date(backup.lastModified());
                bFile.setCreated_at(last_modified);
                backupFilesList.add(bFile);
            }
            orderBackuoFilesCreated();

            FilesBackupDialog filesBackupDialog = new FilesBackupDialog(getActivity());
            filesBackupDialog.initVariablesOnCreate(backupFilesList, this);
            filesBackupDialog.show();
        } else {
            mainActivityView.onToast("No backup files generated");
        }
    }

    public void orderBackuoFilesCreated(){
        Collections.sort(backupFilesList, new Comparator<BackupFiles>(){
            public int compare(BackupFiles s1, BackupFiles s2) {
                return s2.getCreated_at().compareTo(s1.getCreated_at());
            }
        });
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

    @Override
    public void onFaskBackupPlayStore() {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.fastbackup.fastbackup.fast_backup")));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.fastbackup.fastbackup.fast_backup")));
        }
    }

    public void onStoragePermissionDone(){
        getListOfFilesBackup();
    }
}
