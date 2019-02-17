package com.example.ekreasi.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.view.View;
import android.widget.Toast;

import com.example.ekreasi.activity.HomeActivity;
import com.example.ekreasi.activity.ListArticleActivity;
import com.example.ekreasi.activity.OTPView;
import com.example.ekreasi.helper.MyContants;
import com.example.ekreasi.helper.SessionManager;
import com.example.ekreasi.model.ResponseAddArticle;
import com.example.ekreasi.model.ResponseLogin;
import com.example.ekreasi.network.InitRetrofit;
import com.example.ekreasi.network.RestApi;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

public class AddArticleViewModel extends Observable {
    private Context context;
    private SessionManager manager;

    public AddArticleViewModel(Context context) {
        this.context = context;
    }



    public void sendSaveRequest(final String username, final String title,  final String category, final String content, final String image, final String tanggal, final String phone)  {
        manager = new SessionManager(context);
        int user_id = Integer.parseInt(manager.getUser_id());
        RestApi api = InitRetrofit.getInstance();
        Call<ResponseAddArticle> saveCall = api.addarticle(
                user_id,
                username,
                title,
                category,
                content,
                image,
                tanggal,
                phone
        );
        saveCall.enqueue(new Callback<ResponseAddArticle>() {
            @Override
            public void onResponse(Call<ResponseAddArticle> call, Response<ResponseAddArticle> response) {

                if (response.isSuccessful()) {
                    ((Activity)context).finish();

                    Toast.makeText(context, "Article Berhasil Di Simpan", Toast.LENGTH_SHORT).show();


                } else if (response.equals("failed")) {
                    Toast.makeText(context, "Article gagal Di Simpan", Toast.LENGTH_SHORT).show();

                }
            }


            @Override
            public void onFailure(Call<ResponseAddArticle> call, Throwable t) {
                Toast.makeText(context, "Masalah Koneksi", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
