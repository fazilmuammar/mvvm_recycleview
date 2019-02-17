package com.example.ekreasi.activity;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.ekreasi.R;


import com.example.ekreasi.databinding.ActivityRegisterBinding;
import com.example.ekreasi.view.RegisterView;
import com.example.ekreasi.viewmodel.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {


    private VideoView videoBG;
    MediaPlayer mMediaPlayer;
    int mCurrentVideoPosition;

    private RegisterViewModel registerViewModel;
    private ActivityRegisterBinding activityRegisterBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        activityRegisterBinding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        registerViewModel= new RegisterViewModel(this);
        activityRegisterBinding.setRegisterview(registerViewModel);

      activityRegisterBinding.setPresenter(new RegisterView() {
          @Override
          public void RegisterData() {
              final String username = activityRegisterBinding.usernameku.getText().toString();
              final String email = activityRegisterBinding.emailku.getText().toString();
              final String phone = activityRegisterBinding.phone.getText().toString();
              final String pasword = activityRegisterBinding.passwordku.getText().toString();

              registerViewModel.sendRegister(username,email,phone,pasword);
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
                // We want our video to play over and over so we set looping to true.
                mMediaPlayer.setLooping(true);
                // We then seek to the current posistion if it has been set and play the video.
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
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer.getCurrentPosition();
        videoBG.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restart the video when resuming the Activity
        videoBG.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


}
