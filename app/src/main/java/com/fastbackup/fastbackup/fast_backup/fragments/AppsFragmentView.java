package com.fastbackup.fastbackup.fast_backup.fragments;

import com.fastbackup.fastbackup.fast_backup.data.models.SavedApp;

public interface AppsFragmentView {

    void onAppDownloadClicked(SavedApp app);

    void onNewAppsSaved();

    void onDeleteAppClicked(SavedApp app);

}
