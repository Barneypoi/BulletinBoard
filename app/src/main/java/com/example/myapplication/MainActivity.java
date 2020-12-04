package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecycleAdapterMain.OnItemClickListener {
    private RecyclerView recyclerView;//声明RecyclerView
    private RecycleAdapterMain adapterDome;//声明适配器
    private Context context;
    private List<News> list;
    private Handler handler;
    private String token;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.action_cart://监听菜单按钮
                SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("token");
                editor.commit();
                Toast.makeText(getBaseContext(),"已退出登录",Toast.LENGTH_LONG).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                Gson gson = new Gson();
                list = gson.fromJson(msg.obj.toString(), new TypeToken<List<News>>() {}.getType());
//                JsonArray jsonElements = JsonParser.parseString(msg.obj.toString()).getAsJsonArray();
//                for(JsonElement news : jsonElements){
//                    News news1 = gson.fromJson(news,News.class);
//                    list.add(news1);
//                }
                recyclerView = (RecyclerView) findViewById(R.id.main_recycler);
                adapterDome = new RecycleAdapterMain(context,list);
                LinearLayoutManager manager = new LinearLayoutManager(context);
                manager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.addItemDecoration(new SpaceItemDecoration(30));
                recyclerView.setAdapter(adapterDome);
                adapterDome.setItemClickListener(new RecycleAdapterMain.OnItemClickListener(){
                    @Override
                    public void onItemClick(String result, int position) {
                        SharedPreferences pref = getSharedPreferences("data",Context.MODE_PRIVATE);
                        token = pref.getString("token","");
                        if(token==""){
                            Intent intent = new Intent();
                            intent.putExtra("id", result);
                            News news1 = list.get(position);
                            intent.putExtra("news", news1);
                            intent.setClass(MainActivity.this, LoginActivity.class);
                            MainActivity.this.startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent();
                            intent.putExtra("id", result);
                            News news1 = list.get(position);
                            intent.putExtra("news", news1);
                            intent.setClass(MainActivity.this, DetailActivity.class);
                            MainActivity.this.startActivity(intent);
                        }
                    }
                });
            }
        };
        new MyThread().start();
    }

    @Override
    public void onItemClick(String result, int position) {
//        Toast.makeText(getBaseContext(),"登录成功",Toast.LENGTH_LONG).show();
//        if(token==null){
//            Intent intent = new Intent();
//            intent.putExtra("id", result);
//            intent.putExtra("position", position);
//            intent.setClass(MainActivity.this, LoginActivity.class);
//            MainActivity.this.startActivity(intent);
//        }
//        else{
//            Intent intent = new Intent();
//            intent.putExtra("id", result);
//            intent.putExtra("position", position);
//            intent.setClass(MainActivity.this, DetailActivity.class);
//            MainActivity.this.startActivity(intent);
//        }
    }


    class MyThread extends Thread{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            String str = readJsonFile("metadata.json");
            Message message = handler.obtainMessage();
            message.obj = str;
            handler.sendMessage(message);
        }
        private String readJsonFile(String fileName) {
            String str = null;
            try {
                InputStream is = context.getResources().getAssets().open(fileName);
                int lenght = is.available();
                byte[] buffer = new byte[lenght];
                is.read(buffer);
                str  = new String(buffer, "utf8").trim();
                return str;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
