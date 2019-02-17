package com.example.ekreasi.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.andreabaccega.widget.FormEditText;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ekreasi.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditActivity extends AppCompatActivity {

    @BindView(R.id.my_awesome_toolbar)
    Toolbar myAwesomeToolbar;
    @BindView(R.id.gambar)
    ImageView gambar;
    @BindView(R.id.input1)
    TextView input1;
    @BindView(R.id.edit_name)
    FormEditText editName;
    @BindView(R.id.edit_title)
    EditText editTitle;
    @BindView(R.id.tanggal)
    EditText tanggal;
    @BindView(R.id.spin_category)
    Spinner spinCategory;
    @BindView(R.id.spin_category_id)
    Spinner spinCategoryId;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.input3)
    TextView input3;
    @BindView(R.id.edit_content)
    EditText editContent;
    @BindView(R.id.fab)
    FloatingActionButton fab;



    private String email,author,category,tanggals,phones,content,content_id;
    private ImageView imgs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        ButterKnife.bind(this);

        email=this.getExtras("email");
        author=this.getExtras("author");
        category=this.getExtras("category");
        tanggals=this.getExtras("tanggal");
        phones=this.getExtras("phone");
        content=this.getExtras("content");
        imgs=((ImageView)this.findViewById(R.id.gambar));

        this.setTexting();
        parseIntentFromLink();
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
      //          Glide.with((Activity)EditActivity.this) .load(getExtras("gambar")).apply(new RequestOptions().skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)).into(imgs);

            }
        });

    }



    public void setImages(String path, ImageView views){
    }
    private String getExtras(String values){
        return getIntent().getStringExtra(values);
    }

    private void setTexting(){
        editName.setText(email.equals("")?"-":email);
        editTitle.setText(author.equals("")?"-":author);
        tanggal.setText(tanggals.equals("")?"-":tanggals);
        phone.setText(phones.equals("")?"-":phones);
        editContent.setText(content.equals("")?"-":content);


    }

    private void parseIntentFromLink() {
        Intent intent = getIntent();
        Uri uri = intent.getData();


        // Test deep link data
        if (uri != null) {

            email = uri.getQueryParameter("email");
            author = uri.getQueryParameter("author");
            category = uri.getQueryParameter("category");
            tanggals = uri.getQueryParameter("tanggal");
            phones = uri.getQueryParameter("phone");
            content = uri.getQueryParameter("content");



           content_id = uri.getQueryParameter("content_id");

        } else {
            email = getIntent().getStringExtra("email");
            author = getIntent().getStringExtra("author");
            category = getIntent().getStringExtra("category");
            tanggals = getIntent().getStringExtra("tanggal");
            phones = getIntent().getStringExtra("phone");
            content = getIntent().getStringExtra("content");


            content_id = getIntent().getStringExtra("content_id");

        }


      //  Edit();


    }

}
