package com.example.ekreasi.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.example.ekreasi.R;
import com.example.ekreasi.activity.EditActivity;
import com.example.ekreasi.activity.ListArticleActivity;
import com.example.ekreasi.model.ResponseDelete;
import com.example.ekreasi.model.ResultItemListArticle;
import com.example.ekreasi.network.InitRetrofit;
import com.example.ekreasi.network.RestApi;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomRecycleradapter extends RecyclerView.Adapter<CustomRecycleradapter.MyHolder> {

    List<ResultItemListArticle> data;
    Context c;

    private LayoutInflater layoutInflater;
    private String img_path;

    Bitmap bitmap;


    public CustomRecycleradapter(List<ResultItemListArticle> result, Context Context) {
        this.data = result;
        this.c = Context;


    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflater = LayoutInflater.from(c).inflate(R.layout.cardview_article, parent, false);
        return new MyHolder(inflater);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {



        holder.CONTENT_ID =data.get(position).getContentId();


        holder.email.setText(data.get(position).getTitle());
        holder.category.setText(data.get(position).getCategory());
        holder.tanggals.setText(data.get(position).getTanggal());
        holder.phones.setText(data.get(position).getPhone());
        holder.author.setText(data.get(position).getAuthor());
        holder.content.setText(data.get(position).getContent());
        img_path=("http://fazilmuammar007.com/blogapp/images/" + data.get(position).getImage());


        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.noprofile)
                .priority(Priority.HIGH);

        Glide.with(c)
                .load(img_path)
                .apply(options)
                .into(holder.gambar);



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {


       private TextView email,category, tanggals, phones,author,content ;

        ImageView gambar,hapus,edit;

        public String CONTENT_ID;

        public MyHolder(final View itemView) {
            super(itemView);


            author       = (TextView) itemView.findViewById(R.id.text_titles);
            email        = (TextView) itemView.findViewById(R.id.text_title);
            category     = (TextView) itemView.findViewById(R.id.text_category);
            tanggals      = (TextView) itemView.findViewById(R.id.text_created);
            phones        = (TextView) itemView.findViewById(R.id.text_views);
            content      = (TextView) itemView.findViewById(R.id.text_content);

            gambar = (ImageView) itemView.findViewById(R.id.img_image);

            hapus  =(ImageView)  itemView.findViewById(R.id.hapus);
            edit   = (ImageView) itemView.findViewById(R.id.edit);


            /*
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent pindah=new Intent(c,  EditActivity.class);
                    pindah.putExtra("email",     email.getText().toString());
                    pindah.putExtra("author",    author.getText().toString());
                    pindah.putExtra("category",  category.getText().toString());
                    pindah.putExtra("tanggal",   tanggals.getText().toString());
                    pindah.putExtra("phone",     phones.getText().toString());
                    pindah.putExtra("content",   content.getText().toString());
                    pindah.putExtra("gambar",    img_path);
                    pindah.putExtra("content_id", CONTENT_ID);
                    c.startActivity(pindah);
                }
            });

            hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Hapus();

                }
            });

           */
        }

        public void Hapus() {
            final String content_id = CONTENT_ID;
            android.support.v7.app.AlertDialog.Builder builder = new AlertDialog.Builder(c);
            builder.setTitle("Hapus ");
            builder.setMessage("Apakah anda yakin untuk hapus content ini ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    RestApi api = InitRetrofit.getInstance();
                    Call<ResponseDelete> regisCall = api.deleteArticle(content_id);
                    regisCall.enqueue(new Callback<ResponseDelete>() {
                        @Override
                        public void onResponse(Call<ResponseDelete> call, Response<ResponseDelete> response) {
                            if (response.isSuccessful()){
                                String result = response.body().getResponse();
                                if (result.equals("success")){
                                    Toast.makeText(c, "Content Berhasil Di Hapus", Toast.LENGTH_SHORT).show();


                                }else{
                                    Toast.makeText(c, "Gagal Menghapus", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDelete> call, Throwable t) {

                        }
                    });
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            builder.show();




        }
    }


        }


