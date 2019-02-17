package com.example.ekreasi.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.ekreasi.R;
import com.example.ekreasi.databinding.ActivityAddArticleBinding;
import com.example.ekreasi.databinding.ActivityListArticleBinding;
import com.example.ekreasi.helper.CustomRecycleradapter;
import com.example.ekreasi.model.ResponseListArticle;
import com.example.ekreasi.model.ResultItemListArticle;
import com.example.ekreasi.network.InitRetrofit;
import com.example.ekreasi.viewmodel.AddArticleViewModel;
import com.example.ekreasi.viewmodel.ListArticleViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListArticleActivity extends AppCompatActivity {


    RecyclerView recyclerview;
    SwipeRefreshLayout mSwipe;

    private ListArticleViewModel listArticleViewModel;
    private ActivityListArticleBinding activityListArticleBinding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        setSupportActionBar(toolbar);

        activityListArticleBinding = DataBindingUtil.setContentView(this, R.layout.activity_list_article);
        listArticleViewModel = new ListArticleViewModel(this, activityListArticleBinding.recyclerview, activityListArticleBinding.swipe);
        activityListArticleBinding.setListview(listArticleViewModel);

        listArticleViewModel.MyArticle();





        activityListArticleBinding.swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listArticleViewModel.MyArticle();
            }
        });




    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }







}
