package com.fastbackup.fastbackup.fast_backup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.data.models.BackupFiles;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragment;
import com.fastbackup.fastbackup.fast_backup.fragments.SettingsFragmentView;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class FileBackupListAdapter extends RecyclerView.Adapter<FileBackupListAdapter.ViewHolder>{
    List<BackupFiles> data;
    private Context context;
    SettingsFragmentView settingsFragmentView;
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


    public FileBackupListAdapter(List<BackupFiles> data, SettingsFragment fragment) {
        this.data                 = data;
        this.settingsFragmentView = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.backup_files_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BackupFiles item = data.get(position);
        holder.setIsRecyclable(false);
        holder.name.setText(item.getName());
        String dateCreated = dateFormat.format(item.getCreated_at());
        holder.date.setText(dateCreated);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView date;
        public ImageView share;

        public ViewHolder(View v) {
            super(v);
            name  = (TextView) v.findViewById(R.id.name_backup_files_row);
            date  = (TextView) v.findViewById(R.id.date_backup_files_row);
            share = (ImageView) v.findViewById(R.id.share_backup_files_row);

            share.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    BackupFiles item = data.get(getAdapterPosition());
                    settingsFragmentView.onBackupFileShare(item);
                }
            });
        }
    }
}