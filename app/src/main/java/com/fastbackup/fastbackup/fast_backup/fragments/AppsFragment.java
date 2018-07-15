package com.fastbackup.fastbackup.fast_backup.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.activities.Main.MainActivity;
import com.fastbackup.fastbackup.fast_backup.activities.Main.MainActivityView;
import com.fastbackup.fastbackup.fast_backup.adapters.SavedAppsListAdapter;
import com.fastbackup.fastbackup.fast_backup.data.models.SavedApp;
import com.fastbackup.fastbackup.fast_backup.helpers.UserSessionManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.Timestamp;

public class AppsFragment extends Fragment implements AppsFragmentView{
    RecyclerView rv_saved_apps;
    SavedAppsListAdapter da_saved_apps;
    List<SavedApp> savedAppsList;
    ImageView iv_upload_fm_apps;

    UserSessionManager session;

    static NewAppsFragmentView newAppsFragmentView;

    private static String LOG                              = "AppsFragment";
    public static final Integer WRITE_EXST                 = 0x1;
    public static final Integer FILE_PICKER                = 0x2;
    public static final String APPS_FILES_UPLOAD_SEPARATOR_LINE = "///";
    public static final String APPS_FILES_UPLOAD_SEPARATOR = "//_//";

    static MainActivityView mainActivityView;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apps, container, false);
        initVariables(view);
        initUIViews(view);
        initActions();
        rv_saved_apps.setLayoutManager(new LinearLayoutManager(getContext()));
        da_saved_apps = new SavedAppsListAdapter(savedAppsList, this);
        rv_saved_apps.setAdapter(da_saved_apps);

        getSavedApps();

        return view;
    }

    public void initUIViews(View v){
        iv_upload_fm_apps = v.findViewById(R.id.iv_upload_fm_apps);
        rv_saved_apps     = v.findViewById(R.id.rv_saved_apps_fm_apps);
    }

    public static void newInstance(NewAppsFragment fragment, MainActivity activity) {
        newAppsFragmentView = fragment;
        mainActivityView    = activity;
    }

    public void initVariables(View view){
        session       = new UserSessionManager(getActivity().getBaseContext());
        savedAppsList = new ArrayList<>();
    }

    public void initActions(){
        iv_upload_fm_apps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /*if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    createFolder();
                } else {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXST);
                }*/
                searchBackupFile();
            }
        });
    }

    public void createFolder(){
        File folder = new File(Environment.getExternalStorageDirectory() + File.separator + "FastBackupFiles");
        if (!folder.exists()) {
            if(folder.mkdirs()){
                createBackupFile();
            }else{
                mainActivityView.onToast("Error Creating Folder");
            }
        }else{
            createBackupFile();
        }
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

    public void createBackupFile(){
        if(!savedAppsList.isEmpty()){
            try{
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String filePath = Environment.getExternalStorageDirectory() + File.separator + "FastBackupFiles" + File.separator + "backup_1" + timestamp.getTime() + ".txt";
                File file = new File(filePath);

                String appsString = "";
                for (SavedApp sApp : savedAppsList){
                    appsString += sApp.getName() + APPS_FILES_UPLOAD_SEPARATOR_LINE + sApp.getFullPath() + System.getProperty("line.separator");
                }

                if(file.createNewFile() == true){
                    mainActivityView.onToast("Your Files was created on FastBackupFiles folder, Share it!");
                    if(file.exists()){
                        FileWriter writer = new FileWriter(file);
                        writer.append(appsString);
                        writer.flush();
                        writer.close();
                        shareFile(filePath);
                    }
                }
            }catch(Exception e){
                mainActivityView.onToast("Error Creating the File");
            }
        }else{
            mainActivityView.onToast("No Apps to download the  Backup File");
        }
    }

    public void shareFile(String filePath){
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("*/*");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "FastBackup File");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filePath));
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void searchBackupFile(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        getActivity().startActivityForResult(intent, FILE_PICKER);
    }

    public void onFileSelected(Intent data){
        Uri selectedFile  = data.getData();
        String dataReader = readTextFile(selectedFile);
        if(!dataReader.isEmpty()){
            importDataFromFileSelected(dataReader);
        } else {
            mainActivityView.onToast("The file selected is empty");
        }
    }

    public void importDataFromFileSelected(String data){
        try{
            String[] listUploadedApps = data.split(APPS_FILES_UPLOAD_SEPARATOR);
            List <SavedApp> listSavedApps = new ArrayList<>();
            for (String uploadedApp : listUploadedApps){
                String [] uploadedAppObject = uploadedApp.split(APPS_FILES_UPLOAD_SEPARATOR_LINE);
                SavedApp sApp = new SavedApp();
                sApp.setName(uploadedAppObject[0]);
                sApp.setFullPath(uploadedAppObject[1]);
                sApp.setPath(uploadedAppObject[1]);
                listSavedApps.add(sApp);
            }
            savedAppsList.clear();
            da_saved_apps.notifyDataSetChanged();
            savedAppsList.addAll(listSavedApps);
            da_saved_apps.notifyDataSetChanged();

            session.deleteSessionApp();
            String jsonApps = new Gson().toJson(savedAppsList);
            session.createAppSession(jsonApps);
            newAppsFragmentView.onChangeSavedApp();
            mainActivityView.onToast("File Uploaded");
        }catch(Exception e){
            mainActivityView.onToast("Something wrong with the selected file, make sure you are selecting the .fbackup file please");
        }
    }

    private String readTextFile(Uri uri){
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(getActivity().getContentResolver().openInputStream(uri)));
            String line = "";

            while ((line = reader.readLine()) != null) {
                builder.append(line + APPS_FILES_UPLOAD_SEPARATOR);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return builder.toString();
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

                newAppsFragmentView.onChangeSavedApp();
            }
        }
    }

    @Override
    public void onStoragePermissionDone() {
        createFolder();
    }
}
