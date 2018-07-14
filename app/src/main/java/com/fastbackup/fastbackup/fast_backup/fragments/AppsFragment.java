package com.fastbackup.fastbackup.fast_backup.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.adapters.SavedAppsListAdapter;
import com.fastbackup.fastbackup.fast_backup.data.models.SavedApp;
import com.fastbackup.fastbackup.fast_backup.helpers.UserSessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

public class AppsFragment extends Fragment implements AppsFragmentView{
    RecyclerView rv_saved_apps;
    SavedAppsListAdapter da_saved_apps;
    List<SavedApp> savedAppsList;

    UserSessionManager session;

    static NewAppsFragmentView newAppsFragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps, container, false);
        initVariables(view);
        rv_saved_apps.setLayoutManager(new LinearLayoutManager(getContext()));
        da_saved_apps = new SavedAppsListAdapter(savedAppsList, this);
        rv_saved_apps.setAdapter(da_saved_apps);

        getSavedApps();

        return view;
    }

    public static void newInstance(NewAppsFragment fragment) {
        newAppsFragmentView = fragment;
    }

    public void initVariables(View view){
        session       = new UserSessionManager(getActivity().getBaseContext());
        savedAppsList = new ArrayList<>();
        rv_saved_apps = view.findViewById(R.id.rv_saved_apps_fm_apps);
    }

    public void getSavedApps(){
        List<SavedApp> savedApps = getListSavedAppsFromSession();
        if(!savedApps.isEmpty()){
            for (SavedApp sApp : savedApps){
                SavedApp sAppObj = new SavedApp();
                sAppObj.setName(sApp.getName());
                sAppObj.setPath(sApp.getPath());
                sAppObj.setFullPath(sApp.getFullPath());
                savedAppsList.add(sAppObj);
            }
        }
    }

    public List<SavedApp> getListSavedAppsFromSession(){
        String uApps = session.getSessionApps();
        List<SavedApp> apps = new ArrayList<>();
        if(uApps != null && !uApps.isEmpty()){
            TypeToken<List<SavedApp>> token = new TypeToken<List<SavedApp>>() {};
            apps = new Gson().fromJson(uApps, token.getType());
        }

        return apps;
    }

    @Override
    public void onAppDownloadClicked(SavedApp app) {
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + app.getFullPath())));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + app.getFullPath())));
        }
    }

    @Override
    public void onNewAppsSaved() {
        savedAppsList.clear();
        da_saved_apps.notifyDataSetChanged();
        getSavedApps();
        da_saved_apps.notifyDataSetChanged();
    }

    @Override
    public void onDeleteAppClicked(SavedApp app) {
        List<SavedApp> savedApps = getListSavedAppsFromSession();
        if(!savedApps.isEmpty()) {
            int i = 0;
            int indexRemove = -1;
            for (SavedApp sApp : savedApps){
                if(sApp.getFullPath().equals(app.getFullPath())){
                    indexRemove = i;
                    break;
                }
                i++;
            }

            if(indexRemove != -1){
                savedApps.remove(indexRemove);
                session.deleteSessionApp();
                String jsonApps = new Gson().toJson(savedApps);
                session.createAppSession(jsonApps);

                savedAppsList.clear();
                da_saved_apps.notifyDataSetChanged();
                savedAppsList.addAll(savedApps);
                da_saved_apps.notifyDataSetChanged();

                newAppsFragmentView.onDeleteSavedApp();
            }
        }
    }
}
