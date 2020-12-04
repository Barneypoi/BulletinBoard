package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//TODO 判断token是否失效、读取图片和标题副标题

public class DetailActivity extends AppCompatActivity {

    public void getAsyn(String url , final News news) {
        OkHttpClient client = new OkHttpClient();
        SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
        String token = pref.getString("token","");
        Request request = new Request.Builder().url(url).addHeader("Authorization","Bearer "+token).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    Gson gson = new Gson();
                    Body body = gson.fromJson(response.body().string(),Body.class);
                    final String result = body.getData();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TextView tv = (TextView) findViewById(R.id.Content);
                            TextView title = (TextView) findViewById(R.id.DetailTitle);
                            TextView subtitle = (TextView) findViewById(R.id.DetailSubTitle);
                            tv.setText(result);
                            title.setText(news.title);
                            subtitle.setText("作者："+news.author+" 时间："+news.publishTime);
                            if(news.covers!=null){
                                String route = "";
                                ImageView imageView1 = (ImageView) findViewById(R.id.imageViewDetail1);
                                ImageView imageView2 = (ImageView) findViewById(R.id.imageViewDetail2);
                                ImageView imageView3 = (ImageView) findViewById(R.id.imageViewDetail3);
                                ImageView imageView4 = (ImageView) findViewById(R.id.imageViewDetail4);
                                route = "file:///android_asset/" + news.covers[0];
                                Glide.with(DetailActivity.this)
                                        .load(route)
                                        .into(imageView1);
                                imageView1.setVisibility(View.VISIBLE);
                                route = "file:///android_asset/" + news.covers[1];
                                Glide.with(DetailActivity.this)
                                        .load(route)
                                        .into(imageView2);
                                imageView2.setVisibility(View.VISIBLE);
                                route = "file:///android_asset/" + news.covers[2];
                                Glide.with(DetailActivity.this)
                                        .load(route)
                                        .into(imageView3);
                                imageView3.setVisibility(View.VISIBLE);
                                route = "file:///android_asset/" + news.covers[3];
                                Glide.with(DetailActivity.this)
                                        .load(route)
                                        .into(imageView4);
                                imageView4.setVisibility(View.VISIBLE);
                            }
                            else if(news.cover!=null){
                                String route = "file:///android_asset/" + news.cover;
                                ImageView imageView = (ImageView) findViewById(R.id.imageViewDetail1);
                                Glide.with(DetailActivity.this)
                                        .load(route)
                                        .into(imageView);
                                imageView.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                else{
                    Intent intent = getIntent();
                    intent.setClass(DetailActivity.this, LoginActivity.class);
                    DetailActivity.this.startActivity(intent);
                    Toast.makeText(getBaseContext(),"登录信息已失效，请重新登录",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);
        findViewById(R.id.imageViewDetail1).setVisibility(View.GONE);
        findViewById(R.id.imageViewDetail2).setVisibility(View.GONE);
        findViewById(R.id.imageViewDetail3).setVisibility(View.GONE);
        findViewById(R.id.imageViewDetail4).setVisibility(View.GONE);
        Intent intent = getIntent();
        String value = intent.getStringExtra("id");
        News news = (News)getIntent().getSerializableExtra("news");
        getAsyn("https://vcapi.lvdaqian.cn/article/"+value,news);
    }
}
