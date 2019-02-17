package com.example.ekreasi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

import com.example.ekreasi.R;
import com.example.ekreasi.intro.Intro1;
import com.example.ekreasi.intro.Intro2;
import com.example.ekreasi.intro.Intro3;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    Intro1 intro1 = new Intro1();
    Intro2 intro2 = new Intro2();
    Intro3 intro3 = new Intro3();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);


        addSlide(AppIntroFragment.newInstance("WELCOME", "eKreasi Application", R.drawable.aa,getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("WRITE", "Write Your Idea in Ekreasi",
                R.drawable.b, getResources().getColor(R.color.colorPrimary)));
        addSlide(AppIntroFragment.newInstance("SHARE IT", "Share your idea with other people", R.drawable.c, getResources().getColor(R.color.colorPrimary)));

        setBarColor(getResources().getColor(R.color.colorPrimary));
        setSeparatorColor(getResources().getColor(R.color.colorPrimary));


        showSkipButton(true);
        setProgressButtonEnabled(true);


        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(IntroActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }
}

