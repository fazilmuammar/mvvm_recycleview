package com.example.ekreasi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.widget.VideoView;

import com.andreabaccega.formedittextvalidator.EmailValidator;
import com.andreabaccega.formedittextvalidator.OrValidator;
import com.andreabaccega.widget.FormEditText;
import com.example.ekreasi.R;
import com.example.ekreasi.databinding.ActivityLoginActivittyBinding;

import com.example.ekreasi.view.LoginView;
import com.example.ekreasi.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {



    private LoginViewModel loginViewModel;
    private ActivityLoginActivittyBinding loginActivittyBinding;



    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

    int sess_intro;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        loginActivittyBinding = DataBindingUtil.setContentView(this,R.layout.activity_login_activitty);
        loginViewModel = new LoginViewModel(this);
        loginActivittyBinding.setLoginview(loginViewModel);

        FormEditText fdt = (FormEditText) findViewById(R.id.et_email);
        fdt.addValidator(
                new OrValidator(
                        "This Email is Invalid",
                        new EmailValidator(null) // same here for null
                )
        );


            sess_intro = 0;
            sharedPreferences = getSharedPreferences("ekreasii", Context.MODE_PRIVATE);
            sess_intro = sharedPreferences.getInt("ekreasii", 0);
            if (sess_intro == 0) {
                // set session
                sharedPreferences = getSharedPreferences("ekreasii", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putInt("ekreasii", 1);
                editor.apply();
                // call intro
                startActivity(new Intent(LoginActivity.this, IntroActivity.class));
                finish();

            }


        loginActivittyBinding.setPresenter(new LoginView() {

            @Override
            public void LoginData() {
                final String name = loginActivittyBinding.etEmail.getText().toString();
                final String pass = loginActivittyBinding.password.getText().toString();
                FormEditText fdt = (FormEditText) findViewById(R.id.et_email);
                loginViewModel.sendLoginRequest(name,pass);
            }



            @Override
            public void IntentRegister() {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });

        videoBG = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"
                + getPackageName()
                + "/"
                + R.raw.b);
        videoBG.setVideoURI(uri);
        videoBG.start();

        videoBG.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mMediaPlayer = mediaPlayer;

                mMediaPlayer.setLooping(true);
                if (mCurrentVideoPosition != 0) {
                    mMediaPlayer.seekTo(mCurrentVideoPosition);
                    mMediaPlayer.start();
                }
            }
        });

    }

        @Override
        protected void onPause() {
            super.onPause();
            videoBG.pause();
        }

        @Override
        protected void onResume() {
            super.onResume();
            videoBG.start();
        }

}
