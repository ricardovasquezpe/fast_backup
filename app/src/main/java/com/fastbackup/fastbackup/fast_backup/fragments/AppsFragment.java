package com.fastbackup.fastbackup.fast_backup.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.adapters.AppsListAdapter;
import com.fastbackup.fastbackup.fast_backup.adapters.SavedAppsListAdapter;
import com.fastbackup.fastbackup.fast_backup.data.models.SavedApp;
import com.fastbackup.fastbackup.fast_backup.helpers.UserSessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class AppsFragment extends Fragment {
    RecyclerView rv_saved_apps;
    SavedAppsListAdapter da_saved_apps;
    List<SavedApp> savedAppsList;

    UserSessionManager session;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps, container, false);
        initVariables();
        rv_saved_apps.setLayoutManager(new LinearLayoutManager(getContext()));
        da_saved_apps = new SavedAppsListAdapter(savedAppsList);
        rv_saved_apps.setAdapter(da_saved_apps);

        return view;
    }

    public void initVariables(){
        session       = new UserSessionManager(getActivity().getBaseContext());
        savedAppsList = new ArrayList<>();
    }

    public void getSavedApps(){
        List<SavedApp> savedApps = getListSavedAppsFromSession();
        if(!savedApps.isEmpty()){
            for (SavedApp sApp : savedApps){
                
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
}
