package com.fastbackup.fastbackup.fast_backup.fragments;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.adapters.AppsListAdapter;
import com.fastbackup.fastbackup.fast_backup.data.models.App;
import com.fastbackup.fastbackup.fast_backup.data.models.SavedApp;
import com.fastbackup.fastbackup.fast_backup.helpers.UserSessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NewAppsFragment extends Fragment implements NewAppsFragmentView{
    RecyclerView rv_apps;
    AppsListAdapter da_apps;
    List<App> appsList;
    TextView save_refresh;

    UserSessionManager session;

    List<App> currentSelectedApps;

    static AppsFragmentView appsFragmentView;

    static String log = "FASTBACKUP";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static void newInstance(AppsFragment fragment) {
        appsFragmentView = fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_apps, container, false);
        initVariables();
        initUIViews(view);

        rv_apps.setLayoutManager(new LinearLayoutManager(getContext()));
        da_apps = new AppsListAdapter(appsList, new AppsListAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(App item) {
                currentSelectedApps.add(item);
                checkSelectedApps();
            }

            @Override
            public void onItemUncheck(App item) {
                currentSelectedApps.remove(item);
                checkSelectedApps();
            }
        });
        rv_apps.setAdapter(da_apps);
        getInstalledApps();

        save_refresh.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(save_refresh.getText().toString().toLowerCase().equals("save")){
                    List<SavedApp> newApps = getListSavedAppsFromSession();
                    session.deleteSessionApp();

                    for (App app : currentSelectedApps){
                        SavedApp sapp = new SavedApp();
                        sapp.setName(app.getName());
                        sapp.setPath(app.getPath());
                        sapp.setFullPath(app.getFullPath());
                        newApps.add(sapp);
                    }

                    String jsonApps = new Gson().toJson(newApps);
                    session.createAppSession(jsonApps);
                    appsFragmentView.onNewAppsSaved();

                    appsList.clear();
                    da_apps.notifyDataSetChanged();
                    getInstalledApps();
                    currentSelectedApps.clear();
                    save_refresh.setText("Refresh");
                }else {
                    appsList = new ArrayList<>();
                    getInstalledApps();
                }
            }
        });

        return view;
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

    public boolean checkSelectedApps(){
        if(!currentSelectedApps.isEmpty()){
            save_refresh.setText("Save");
        }else{
            save_refresh.setText("Refresh");
        }
        return true;
    }

    public void initVariables(){
        session             = new UserSessionManager(getActivity().getBaseContext());
        appsList            = new ArrayList<>();
        currentSelectedApps = new ArrayList<>();
    }

    public void initUIViews(View v){
        rv_apps      = (RecyclerView) v.findViewById(R.id.rv_apps_fm_apps);
        save_refresh = (TextView) v.findViewById(R.id.test);
    }

    private void getInstalledApps() {
        List<PackageInfo> packs = getActivity().getPackageManager().getInstalledPackages(0);
        List<SavedApp> savedApps = getListSavedAppsFromSession();
        String pcknameFull = "";
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if ((isSystemPackage(p) == false)) {
                String pckName = p.packageName;
                pcknameFull = pckName;
                if(!appAlreadySaved(savedApps, pckName)){
                    String appName = p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                    String version = p.versionName;
                    Drawable icon  = p.applicationInfo.loadIcon(getActivity().getPackageManager());
                    if(pckName.length() >= 16){
                        pckName = pckName.substring(0, 15) + "...";
                    }
                    appsList.add(new App(appName, pckName, pcknameFull, icon));
                }
            }
        }
        orderInstalledAppsList();
        da_apps.notifyDataSetChanged();
    }

    public boolean appAlreadySaved(List<SavedApp> savedApps, String pathName){
        boolean tof = false;
        for (SavedApp sApp : savedApps){
            if(pathName.equals(sApp.getFullPath())){
                tof = true;
            }
        }
        return tof;
    }

    public void orderInstalledAppsList(){
        Collections.sort(appsList, new Comparator<App>(){
            public int compare(App s1, App s2) {
                return s1.getName().compareToIgnoreCase(s2.getName());
            }
        });
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) ? true : false;
    }

    @Override
    public void onChangeSavedApp() {
        currentSelectedApps.clear();
        appsList.clear();
        da_apps.notifyDataSetChanged();
        getInstalledApps();
        da_apps.notifyDataSetChanged();
        save_refresh.setText("Refresh");
    }
}