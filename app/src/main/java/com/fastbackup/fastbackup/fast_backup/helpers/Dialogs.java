package com.fastbackup.fastbackup.fast_backup.helpers;

import android.app.Dialog;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import com.fastbackup.fastbackup.fast_backup.R;

public class Dialogs {

    public void showDialogWrongCredentials(Dialog dialog) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_wrong_credentials);

        dialog.show();
    }
}