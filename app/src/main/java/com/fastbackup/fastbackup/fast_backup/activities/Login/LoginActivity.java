package com.fastbackup.fastbackup.fast_backup.activities.Login;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.fastbackup.fastbackup.fast_backup.R;
import com.fastbackup.fastbackup.fast_backup.helpers.Dialogs;
import com.fastbackup.fastbackup.fast_backup.helpers.Helper;

public class LoginActivity extends Activity {

    TextView tv_login;
    TextView tv_singup;
    LinearLayout ll_singup;
    LinearLayout ll_login;
    Button btn_login;
    EditText et_email;
    EditText et_password;
    ProgressBar pb_login;

    Helper helper;
    Dialogs dialogs;

    Dialog dg_wrong_credentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUIViews();
        initVariables();
        initActions();

        dialogs.showDialogWrongCredentials(dg_wrong_credentials);
        TextView try_again = (TextView)dg_wrong_credentials.findViewById(R.id.tv_try_again_dl_wrong_cred);
        try_again.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dg_wrong_credentials.dismiss();
            }
        });
    }

    public void initUIViews(){
        tv_login    = findViewById(R.id.tv_login_act_login);
        tv_singup   = findViewById(R.id.tv_signup_act_login);
        ll_singup   = findViewById(R.id.ll_singup_act_login);
        ll_login    = findViewById(R.id.ll_login_act_login);
        btn_login   = findViewById(R.id.btn_login_act_login);
        et_email    = findViewById(R.id.et_email_act_login);
        et_password = findViewById(R.id.et_password_act_login);
        pb_login    = findViewById(R.id.pb_login_act_login);
    }

    public void initVariables(){
        helper  = new Helper();
        dialogs = new Dialogs();
        dg_wrong_credentials = new Dialog(LoginActivity.this);
    }

    public void initActions(){
        addShowHideSections(tv_login, tv_singup);
        et_email.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                emailPasswordComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        et_password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                emailPasswordComplete();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    public void addShowHideSections(TextView tv_login, TextView tv_singup){
        tv_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tv_login_inter   = (TextView) findViewById(R.id.tv_login_act_login);
                TextView tv_singup_inter  = (TextView) findViewById(R.id.tv_signup_act_login);
                tv_login_inter.setTextColor(getResources().getColor(R.color.colorPersianGreen));
                tv_singup_inter.setTextColor(getResources().getColor(R.color.colorWhite));

                LinearLayout ll_singup = findViewById(R.id.ll_singup_act_login);
                LinearLayout ll_login  = findViewById(R.id.ll_login_act_login);
                ll_singup.setVisibility(View.GONE);
                ll_login.setVisibility(View.VISIBLE);
            }
        });

        tv_singup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TextView tv_login_inter   = (TextView) findViewById(R.id.tv_login_act_login);
                TextView tv_singup_inter  = (TextView) findViewById(R.id.tv_signup_act_login);
                tv_singup_inter.setTextColor(getResources().getColor(R.color.colorPersianGreen));
                tv_login_inter.setTextColor(getResources().getColor(R.color.colorWhite));

                LinearLayout ll_singup = findViewById(R.id.ll_singup_act_login);
                LinearLayout ll_login  = findViewById(R.id.ll_login_act_login);
                ll_singup.setVisibility(View.VISIBLE);
                ll_login.setVisibility(View.GONE);
            }
        });
    }

    public void emailPasswordComplete(){
        if(!helper.isEmpty(et_email) && !helper.isEmpty(et_password)){
            btn_login.setEnabled(true);
            btn_login.setBackground(getResources().getDrawable(R.drawable.inline_button));
            btn_login.setTextColor(getResources().getColor(R.color.colorWhite));
        }else{
            btn_login.setBackground(getResources().getDrawable(R.drawable.outline_button));
            btn_login.setTextColor(getResources().getColor(R.color.colorWhite));
            btn_login.setEnabled(false);
        }
    }

    public void doLogin(){
        et_email.setEnabled(false);
        et_password.setEnabled(false);
        pb_login.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
    }
}