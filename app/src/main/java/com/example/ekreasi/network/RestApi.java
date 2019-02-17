package com.example.ekreasi.network;

import com.example.ekreasi.model.ResponseAddArticle;
import com.example.ekreasi.model.ResponseDelete;
import com.example.ekreasi.model.ResponseListArticle;
import com.example.ekreasi.model.ResponseLogin;
import com.example.ekreasi.model.ResponseRegister;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseRegister> registeruser(
            @Field("username") String username,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseLogin> loginuser(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("create_content.php")
    Call<ResponseAddArticle> addarticle(
            @Field("user_id") int user_id,
            @Field("author") String author,
            @Field("title") String title,
            @Field("category_id") String category_id,
            @Field("content") String content,
            @Field("image") String image,
            @Field("tanggal") String tanggal,
            @Field("phone") String phone
    );

    @GET("list_article.php")
    Call<ResponseListArticle> getRequestList();


    @FormUrlEncoded
    @POST("delete.php")
    Call<ResponseDelete> deleteArticle(
            @Field("content_id") String content_id

    );

}
