package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.*;

public class LoginActivity extends AppCompatActivity {
    private static final String URL = "https://vcapi.lvdaqian.cn/login";

    public void httppost(String url,String username, String password){
        OkHttpClient client = new OkHttpClient();
        FormBody body = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //...
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String result = response.body().string();
                    Gson gson = new Gson();
                    Token token = gson.fromJson(result,Token.class);
                    SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("token",token.getToken());
                    editor.commit();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getBaseContext(),"登录成功",Toast.LENGTH_LONG).show();
                            Intent intent = getIntent();
//                            String value = intent.getStringExtra("id");
//                            int pos = intent.getIntExtra("position", -1);
//                            Intent intent_new = new Intent();
//                            intent_new.putExtra("id", value);
//                            intent_new.putExtra("position", pos);
//                            intent_new.setClass(LoginActivity.this, DetailActivity.class);
                            intent.setClass(LoginActivity.this, DetailActivity.class);
//                            LoginActivity.this.startActivity(intent_new);
                            LoginActivity.this.startActivity(intent);
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.remove("token");
        editor.commit();
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View view){
                EditText tv1 = (EditText) findViewById(R.id.textus);
                EditText tv2 = (EditText) findViewById(R.id.textpw);
                httppost(URL, tv1.getText().toString(), tv2.getText().toString());
            }
        });
    }
}
