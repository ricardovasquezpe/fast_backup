package com.fastbackup.fastbackup.fast_backup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.data.models.SavedApp;
import com.fastbackup.fastbackup.fast_backup.fragments.AppsFragment;
import com.fastbackup.fastbackup.fast_backup.fragments.AppsFragmentView;
import java.util.List;

public class SavedAppsListAdapter extends RecyclerView.Adapter<SavedAppsListAdapter.ViewHolder>{
    List<SavedApp> data;
    Context context;
    AppsFragmentView appsFragmentView;

    public SavedAppsListAdapter(List<SavedApp> data, AppsFragment fragment) {
        this.data = data;
        this.appsFragmentView = fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.saved_app_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SavedApp item = data.get(position);
        holder.name.setText(item.getName());
        holder.pck_name.setText(item.getPath());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView pck_name;
        public ImageView delete;
        public ImageView download;

        public ViewHolder(View v) {
            super(v);
            name     = (TextView) v.findViewById(R.id.name_saved_ap_row);
            pck_name = (TextView) v.findViewById(R.id.package_saved_ap_row);
            delete   = (ImageView) v.findViewById(R.id.delete_saved_ap_row);
            download = (ImageView) v.findViewById(R.id.icon_saved_ap_row);

            download.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SavedApp item = data.get(getAdapterPosition());
                    appsFragmentView.onAppDownloadClicked(item);
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    SavedApp item = data.get(getAdapterPosition());
                    appsFragmentView.onDeleteAppClicked(item);
                }
            });
        }
    }
}