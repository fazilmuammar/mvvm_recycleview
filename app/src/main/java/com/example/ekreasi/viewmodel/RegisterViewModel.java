package com.example.ekreasi.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.ekreasi.activity.HomeActivity;
import com.example.ekreasi.activity.LoginActivity;
import com.example.ekreasi.helper.SessionManager;
import com.example.ekreasi.model.ResponseLogin;
import com.example.ekreasi.model.ResponseRegister;
import com.example.ekreasi.network.InitRetrofit;
import com.example.ekreasi.network.RestApi;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterViewModel extends Observable {

    private Context context;


    public RegisterViewModel(Context context) {

        this.context = context;
    }

    public void sendRegister(String username, String email, String phone, String password){
        RestApi api = InitRetrofit.getInstance();
        Call<ResponseRegister> registerCall = api.registeruser(
                username,
                email,
                phone,
                password

        );

        registerCall.enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {
                if (response.isSuccessful()){
                    String result = response.body().getResponse();

                    if (result.equals("success")) {
                        context.startActivity(new Intent(context, LoginActivity.class));
                        ((Activity) context).finish();
                        Toast.makeText(context, "Pendaftaran Berhasil", Toast.LENGTH_SHORT).show();
                    } else{
                        Toast.makeText(context, "Pendaftaran gagal", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {

            }
        });
    }

}
