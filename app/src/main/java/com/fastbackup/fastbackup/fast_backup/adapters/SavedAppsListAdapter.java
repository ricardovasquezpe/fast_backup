package com.fastbackup.fastbackup.fast_backup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.data.models.App;
import com.fastbackup.fastbackup.fast_backup.data.models.SavedApp;

import java.util.List;

public class SavedAppsListAdapter extends RecyclerView.Adapter<SavedAppsListAdapter.ViewHolder>{
    private final List<SavedApp> data;
    private Context context;

    public SavedAppsListAdapter(List<SavedApp> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.app_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final SavedApp item = data.get(position);
        holder.name.setText(item.getName());
        holder.icon.setImageResource(R.drawable.saved_app);
        holder.pck_name.setText(item.getPath());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView pck_name;
        public ImageView icon;

        public ViewHolder(View v) {
            super(v);
            name     = (TextView) v.findViewById(R.id.name_ap_row);
            pck_name = (TextView) v.findViewById(R.id.package_ap_row);
            icon     = (ImageView) v.findViewById(R.id.icon_ap_row);
        }
    }
}