package com.fastbackup.fastbackup.fast_backup.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.data.models.App;
import java.util.List;

public class AppsListAdapter extends RecyclerView.Adapter<AppsListAdapter.ViewHolder>{
    private final List<App> data;
    private Context context;
    private OnItemCheckListener onItemCheckListener;

    public interface OnItemCheckListener {
        void onItemCheck(App item);
        void onItemUncheck(App item);
    }

    public AppsListAdapter(List<App> data, OnItemCheckListener onItemCheckListener) {
        this.data = data;
        this.onItemCheckListener = onItemCheckListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        this.context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.app_row, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final App item = data.get(position);
        holder.setIsRecyclable(false);
        holder.name.setText(item.getName());
        holder.icon.setImageDrawable(item.getIcon());
        holder.pck_name.setText(item.getPath());
        holder.select.setOnCheckedChangeListener(null);
        holder.select.setChecked(item.getChecked());
        holder.select.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);
            }
        });

        ((ViewHolder) holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ViewHolder) holder).select.setChecked(
                        !((ViewHolder) holder).select.isChecked());
                if (((ViewHolder) holder).select.isChecked()) {
                    onItemCheckListener.onItemCheck(item);
                } else {
                    onItemCheckListener.onItemUncheck(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView pck_name;
        public ImageView icon;
        public CheckBox select;

        public ViewHolder(View v) {
            super(v);
            name     = (TextView) v.findViewById(R.id.name_ap_row);
            pck_name = (TextView) v.findViewById(R.id.package_ap_row);
            icon     = (ImageView) v.findViewById(R.id.icon_ap_row);
            select   = (CheckBox) v.findViewById(R.id.select_ap_row);
            select.setClickable(false);
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            itemView.setOnClickListener(onClickListener);
        }
    }
}