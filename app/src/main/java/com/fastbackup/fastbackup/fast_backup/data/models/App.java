package com.fastbackup.fastbackup.fast_backup.data.models;

import android.graphics.drawable.Drawable;

public class App {

    public String name;
    public String path;
    public Drawable icon;
    public boolean isChecked;

    public App(String name, String path, Drawable icon){
        this.name = name;
        this.path = path;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
