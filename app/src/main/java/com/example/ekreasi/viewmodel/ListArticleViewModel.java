package com.example.ekreasi.viewmodel;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ekreasi.activity.ListArticleActivity;
import com.example.ekreasi.helper.CustomRecycleradapter;
import com.example.ekreasi.model.ResponseListArticle;
import com.example.ekreasi.model.ResultItemListArticle;
import com.example.ekreasi.network.InitRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListArticleViewModel {
    private Context context;
    RecyclerView recyclerview;
    SwipeRefreshLayout mSwipe;

    public static List<ResultItemListArticle> datahistory;




    public ListArticleViewModel(Context context, RecyclerView recyclerview, SwipeRefreshLayout swipe) {
        this.context = context;
        this.recyclerview = recyclerview;
        this.mSwipe = swipe;
    }

    public void MyArticle() {
        mSwipe.setRefreshing(true);
        InitRetrofit.getInstance().getRequestList().enqueue(new Callback<ResponseListArticle>() {
            @Override
            public void onResponse(Call<ResponseListArticle> call, Response<ResponseListArticle> response) {
                mSwipe.setRefreshing(false);

                datahistory = response.body().getResult();
                CustomRecycleradapter adapter = new CustomRecycleradapter(datahistory, context);
                recyclerview.setAdapter(adapter);
                recyclerview.setLayoutManager(new LinearLayoutManager(context));

            }



            @Override
            public void onFailure(Call<ResponseListArticle> call, Throwable t) {
            mSwipe.setRefreshing(false);
            }
        });
    }




}
